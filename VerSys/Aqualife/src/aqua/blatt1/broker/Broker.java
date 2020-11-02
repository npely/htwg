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

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broker {
    Endpoint endpoint = new Endpoint(4711);
    ClientCollection<InetSocketAddress> clientCollection = new ClientCollection();
    int indexCounter = 0;
    ExecutorService executor = Executors.newFixedThreadPool(6);

    public class BrokerTask {
        public void register(Message message)  {
            InetSocketAddress sender = message.getSender();
            String tankId = "tank" + ++indexCounter;
            clientCollection.add(tankId, sender);
            endpoint.send(sender, new RegisterResponse(tankId));
        }

        public void deregister(Message message) {
            DeregisterRequest deregisterRequest = (DeregisterRequest) message.getPayload();
            String tankId = deregisterRequest.getId();
            int tankIndex = clientCollection.indexOf(tankId);
            clientCollection.remove(tankIndex);
        }

        public void handoffFish(Message message) {
            HandoffRequest handoffRequest = (HandoffRequest) message.getPayload();
            InetSocketAddress receiver;
            FishModel fish = handoffRequest.getFish();
            int fishTankIndex = clientCollection.indexOf(message.getSender());

            if (fish.getDirection() == Direction.LEFT)
                receiver = clientCollection.getLeftNeighborOf(fishTankIndex);
            else
                receiver = clientCollection.getRightNeighborOf(fishTankIndex);

            endpoint.send(receiver, handoffRequest);
        }
    }

    public void broker() {
        while (true) {
            executor.execute(() -> {
                BrokerTask brokerTask = new BrokerTask();
                Message msg = endpoint.blockingReceive();

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
