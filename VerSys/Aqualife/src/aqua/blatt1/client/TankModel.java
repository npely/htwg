package aqua.blatt1.client;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

import aqua.blatt1.common.Direction;
import aqua.blatt1.common.FishModel;
import aqua.blatt1.common.msgtypes.CollectSnapshot;

public class TankModel extends Observable implements Iterable<FishModel> {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 350;
	protected static final int MAX_FISHIES = 5;
	protected static final Random rand = new Random();
	protected volatile String id;
	protected final Set<FishModel> fishies;
	protected int fishCounter = 0;
	protected final ClientCommunicator.ClientForwarder forwarder;
	protected InetSocketAddress leftNeighbor;
	protected InetSocketAddress rightNeighbor;
	protected volatile boolean hasToken = false;
	protected Timer timer;
	private RecordingMode recMode = RecordingMode.IDLE;
	protected int localState;
	protected boolean isInitiator = false;
	protected volatile boolean hasSnapshotCollectToken = false;
	protected volatile CollectSnapshot snapshotCollector = null;
	protected volatile boolean isSnapshotDone = false;
	private int fadingFishCounter = 0;

	public TankModel(ClientCommunicator.ClientForwarder forwarder) {
		this.fishies = Collections.newSetFromMap(new ConcurrentHashMap<FishModel, Boolean>());
		this.forwarder = forwarder;
		this.timer = new Timer();
	}

	synchronized void onRegistration(String id) {
		this.id = id;
		newFish(WIDTH - FishModel.getXSize(), rand.nextInt(HEIGHT - FishModel.getYSize()));
	}

	public synchronized void newFish(int x, int y) {
		if (fishies.size() < MAX_FISHIES) {
			x = x > WIDTH - FishModel.getXSize() - 1 ? WIDTH - FishModel.getXSize() - 1 : x;
			y = y > HEIGHT - FishModel.getYSize() ? HEIGHT - FishModel.getYSize() : y;

			FishModel fish = new FishModel("fish" + (++fishCounter) + "@" + getId(), x, y,
					rand.nextBoolean() ? Direction.LEFT : Direction.RIGHT);

			fishies.add(fish);
		}
	}

	synchronized void receiveFish(FishModel fish) {
		fish.setToStart();
		fishies.add(fish);

		if(this.recMode != RecordingMode.IDLE) {
			this.localState++;
		}
	}

	public String getId() {
		return id;
	}

	public synchronized int getFishCounter() {
		return fishCounter;
	}

	public synchronized Iterator<FishModel> iterator() {
		return fishies.iterator();
	}

	public synchronized void receiveToken() {
		this.hasToken = true;
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TankModel.this.hasToken = false;
				forwarder.forwardToken(TankModel.this.leftNeighbor);
			}
		}, 2000);
	}

	public synchronized boolean hasToken() {
		return this.hasToken;
	}

	protected void initiateSnapshot() {
		this.isInitiator = true;
		this.localState = this.fishies.size() - fadingFishCounter;
		this.recMode = RecordingMode.BOTH;
		this.hasSnapshotCollectToken = true;
		this.snapshotCollector = new CollectSnapshot();
		this.forwarder.sendSnapshotMarker(this.leftNeighbor);
		this.forwarder.sendSnapshotMarker(this.rightNeighbor);
	}

	protected synchronized void handleReceivedMarker(String dir) {
		RecordingMode direction;
		if (dir.equals("left"))
			direction = RecordingMode.RIGHT;
		else
			direction = RecordingMode.LEFT;

		if (this.recMode == RecordingMode.IDLE) {
			this.localState = this.fishies.size() - fadingFishCounter;
			this.recMode = direction;
			this.forwarder.sendSnapshotMarker(this.leftNeighbor);
			this.forwarder.sendSnapshotMarker(this.rightNeighbor);
		} else {
			if (this.recMode == RecordingMode.BOTH) {
				this.recMode = direction;
			} else {
				this.recMode = RecordingMode.IDLE;
				if (hasSnapshotCollectToken) {
					this.hasSnapshotCollectToken = false;
					this.snapshotCollector.addFishies(this.localState);
					forwarder.sendSnapshotCollectionMarker(this.leftNeighbor, this.snapshotCollector);
					this.snapshotCollector = null;
				}
			}
		}
	}

	private synchronized void updateFishies() {
		for (Iterator<FishModel> it = iterator(); it.hasNext();) {
			FishModel fish = it.next();

			fish.update();

			if (fish.hitsEdge()) {
				if (this.hasToken) {
					forwarder.handOff(fish, this);
					fadingFishCounter++;
				}
				else {
					fish.reverse();
				}
			}

			if (fish.disappears()) {
				it.remove();
				fadingFishCounter--;
			}
		}
	}



	private synchronized void update() {
		updateFishies();
		setChanged();
		notifyObservers();
	}

	protected void run() {
		forwarder.register();

		try {
			while (!Thread.currentThread().isInterrupted()) {
				update();
				TimeUnit.MILLISECONDS.sleep(10);
			}
		} catch (InterruptedException consumed) {
			// allow method to terminate
		}
	}

	public synchronized void finish() {
		forwarder.deregister(id);
	}

	enum RecordingMode {
		IDLE,
		LEFT,
		RIGHT,
		BOTH
	}

}
