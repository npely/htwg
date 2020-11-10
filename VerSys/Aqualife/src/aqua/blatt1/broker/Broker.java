package aqua.blatt1.broker;
import aqua.blatt1.client.ClientCommunicator;
import aqua.blatt1.common.Direction;
import aqua.blatt1.common.FishModel;
import aqua.blatt1.common.msgtypes.DeregisterRequest;
import aqua.blatt1.common.msgtypes.HandoffRequest;
import aqua.blatt1.common.msgtypes.RegisterRequest;
import aqua.blatt1.common.msgtypes.RegisterResponse;
import messaging.Endpoint;
import messaging.Message;

import javax.swing.*;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Broker {
    Endpoint endpoint = new Endpoint(4711);
    ClientCollection<InetSocketAddress> clientCollection = new ClientCollection();
    int indexCounter = 0;
    ExecutorService executor = Executors.newFixedThreadPool(6);
    boolean stopRequested = false;

    public class BrokerTask implements Runnable {
        private ReadWriteLock lock;
        Message message;

        public BrokerTask(Message message) {
            this.lock = new ReentrantReadWriteLock();
            this.message = message;
        }

        @Override
        public void run() {
            var payload = message.getPayload();
            if (payload instanceof RegisterRequest)
                register(message);
            if (payload instanceof DeregisterRequest)
                deregister(message);
            if (payload instanceof HandoffRequest)
                handoffFish(message);
            if (payload instanceof PoisonPill)
                stopRequested = true;
        }

        public void register(Message message)  {
            InetSocketAddress sender = message.getSender();
            String tankId = "tank" + ++indexCounter;
            lock.writeLock().lock();
            clientCollection.add(tankId, sender);
            lock.writeLock().unlock();
            endpoint.send(sender, new RegisterResponse(tankId));
        }

        public void deregister(Message message) {
            DeregisterRequest deregisterRequest = (DeregisterRequest) message.getPayload();
            String tankId = deregisterRequest.getId();
            lock.readLock().lock();
            int tankIndex = clientCollection.indexOf(tankId);
            lock.readLock().unlock();
            lock.writeLock().lock();
            clientCollection.remove(tankIndex);
            lock.writeLock().unlock();
        }

        public void handoffFish(Message message) {
            HandoffRequest handoffRequest = (HandoffRequest) message.getPayload();
            InetSocketAddress receiver;
            FishModel fish = handoffRequest.getFish();
            lock.readLock().lock();
            int fishTankIndex = clientCollection.indexOf(message.getSender());
            lock.readLock().unlock();

            if (fish.getDirection() == Direction.LEFT)
                receiver = clientCollection.getLeftNeighborOf(fishTankIndex);
            else
                receiver = clientCollection.getRightNeighborOf(fishTankIndex);

            endpoint.send(receiver, handoffRequest);
        }
    }

    public void broker() {
        Thread stopRequestedDialogThread = new Thread(() -> {
            int res = JOptionPane.showOptionDialog(null,
                    "Press Ok button to stop server","",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    null);
            if (res == 0) {
                stopRequested = true;
                System.exit(0);
            }
        });
        stopRequestedDialogThread.start();

        while (!stopRequested) {
            Message msg = endpoint.blockingReceive();
            var brokerTask = new BrokerTask(msg);
            executor.execute(brokerTask);
        }
    }

    public static void main(String[] args) {
        Broker broker = new Broker();
        broker.broker();
    }
}
