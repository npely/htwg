package aqua.blatt1.common.msgtypes;

import java.io.Serializable;
import java.net.InetSocketAddress;

@SuppressWarnings("serial")
public final class CollectSnapshot implements Serializable {
    public int sumOfFishies;

    public CollectSnapshot() {
        this.sumOfFishies = 0;
    }

    public void addFishies(int amount) {
        this.sumOfFishies += amount;
    }

    public int getFishies() {
        return this.sumOfFishies;
    }
}

