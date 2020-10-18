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

public class Broker {
    Endpoint endpoint = new Endpoint(4711);
    ClientCollection<InetSocketAddress> clientCollection = new ClientCollection();
    int indexCounter = 0;

    public void broker() {
        while (true) {
            Message msg = endpoint.blockingReceive();

            if (msg.getPayload() instanceof RegisterRequest)
                register(msg);

            if (msg.getPayload() instanceof DeregisterRequest)
                deregister(msg);

            if (msg.getPayload() instanceof HandoffRequest)
                handoffFish(msg);

        }
    }

    public void register(Message message)  {
        RegisterRequest registerRequest = (RegisterRequest) message.getPayload();
        InetSocketAddress sender = message.getSender();
        String tankId = "tank" + indexCounter++;
        clientCollection.add(tankId, sender);
        endpoint.send(sender, new RegisterResponse(tankId));
    }

    public void deregister(Message message) {
        DeregisterRequest deregisterRequest = (DeregisterRequest) message.getPayload();
        String tankId = deregisterRequest.getId();
        int tankIndex = clientCollection.indexOf(tankId);
        clientCollection.remove(tankIndex);
        indexCounter--;
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

    public static void main(String[] args) {
        Broker broker = new Broker();
        broker.broker();
    }
}
