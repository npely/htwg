package aqua.blatt1.common.msgtypes;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class NeighborUpdate implements Serializable {

    private InetSocketAddress left;
    private InetSocketAddress right;
    private String id;

    public NeighborUpdate(String id, InetSocketAddress left, InetSocketAddress right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }


    public String getId() {
        return id;
    }

    public InetSocketAddress getLeftNeighbor() {
        return left;
    }

    public InetSocketAddress getRightNeighbor() {
        return right;
    }
}
