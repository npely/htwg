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

    public class BrokerTask {
        private ReadWriteLock lock;

        public BrokerTask() {
            this.lock = new ReentrantReadWriteLock();
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
            executor.execute(() -> {
                BrokerTask brokerTask = new BrokerTask();

                if (msg.getPayload() instanceof RegisterRequest)
                    brokerTask.register(msg);

                if (msg.getPayload() instanceof DeregisterRequest)
                    brokerTask.deregister(msg);

                if (msg.getPayload() instanceof HandoffRequest)
                    brokerTask.handoffFish(msg);
            });
        }
    }

    public static void main(String[] args) {
        Broker broker = new Broker();
        broker.broker();
    }
}
