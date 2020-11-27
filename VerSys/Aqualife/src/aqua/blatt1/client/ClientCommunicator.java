package aqua.blatt1.client;

import java.net.InetSocketAddress;

import messaging.Endpoint;
import messaging.Message;
import aqua.blatt1.client.TankModel.RecordingMode;
import aqua.blatt1.common.Direction;
import aqua.blatt1.common.FishModel;
import aqua.blatt1.common.Properties;
import aqua.blatt1.common.msgtypes.CollectSnapshot;
import aqua.blatt1.common.msgtypes.DeregisterRequest;
import aqua.blatt1.common.msgtypes.HandoffRequest;
import aqua.blatt1.common.msgtypes.NeighborUpdate;
import aqua.blatt1.common.msgtypes.RegisterRequest;
import aqua.blatt1.common.msgtypes.RegisterResponse;
import aqua.blatt1.common.msgtypes.SnapshotMarker;
import aqua.blatt1.common.msgtypes.Token;

public class ClientCommunicator {
	private final Endpoint endpoint;

	public ClientCommunicator() {
		endpoint = new Endpoint();
	}

	public class ClientForwarder {
		private final InetSocketAddress broker;

		private ClientForwarder() {
			this.broker = new InetSocketAddress(Properties.HOST, Properties.PORT);
		}

		public void register() {
			endpoint.send(broker, new RegisterRequest());
		}

		public void deregister(String id) {
			endpoint.send(broker, new DeregisterRequest(id));
		}

		public void forwardToken(InetSocketAddress addr) {
			endpoint.send(addr, new Token());
		}

		public void sendSnapshotMarker(InetSocketAddress addr) {
			endpoint.send(addr, new SnapshotMarker());
		}

		public void sendSnapshotCollectionMarker(InetSocketAddress addr, CollectSnapshot cs) {
			endpoint.send(addr, cs);
		}

		public void handOff(FishModel fish, TankModel tankModel) {
			if (fish.getDirection() == Direction.LEFT)
				endpoint.send(tankModel.leftNeighbor, new HandoffRequest(fish));
			else
				endpoint.send(tankModel.rightNeighbor, new HandoffRequest(fish));
		}
	}

	public class ClientReceiver extends Thread {
		private final TankModel tankModel;

		private ClientReceiver(TankModel tankModel) {
			this.tankModel = tankModel;
		}

		@Override
		public void run() {
			while (!isInterrupted()) {
				Message msg = endpoint.blockingReceive();

				if (msg.getPayload() instanceof RegisterResponse)
					tankModel.onRegistration(((RegisterResponse) msg.getPayload()).getId());

				if (msg.getPayload() instanceof HandoffRequest)
					tankModel.receiveFish(((HandoffRequest) msg.getPayload()).getFish());

				if (msg.getPayload() instanceof Token)
					tankModel.receiveToken();

				if (msg.getPayload() instanceof NeighborUpdate) {
					NeighborUpdate neighborUpdate = (NeighborUpdate) msg.getPayload();
					if (neighborUpdate.getLeftNeighbor() != null)
						tankModel.leftNeighbor = neighborUpdate.getLeftNeighbor();
					if (neighborUpdate.getRightNeighbor() != null)
						tankModel.rightNeighbor = neighborUpdate.getRightNeighbor();
				}
				if (msg.getPayload() instanceof SnapshotMarker) {
					if (msg.getSender().equals(tankModel.leftNeighbor))
						tankModel.handleReceivedMarker("left");

					else
						tankModel.handleReceivedMarker("right");
				}

				if (msg.getPayload() instanceof CollectSnapshot) {
					tankModel.hasSnapshotCollectToken = true;
					tankModel.snapshotCollector = (CollectSnapshot) msg.getPayload();
					if (tankModel.isInitiator) {
						tankModel.isSnapshotDone = true;
						tankModel.hasSnapshotCollectToken = false;
						tankModel.isInitiator = false;
					} else {
						tankModel.hasSnapshotCollectToken = false;
						tankModel.snapshotCollector.addFishies(tankModel.localState);
						tankModel.forwarder.sendSnapshotCollectionMarker(tankModel.leftNeighbor, tankModel.snapshotCollector);
					}
				}


			}
			System.out.println("Receiver stopped.");
		}
	}

	public ClientForwarder newClientForwarder() {
		return new ClientForwarder();
	}

	public ClientReceiver newClientReceiver(TankModel tankModel) {
		return new ClientReceiver(tankModel);
	}

}

