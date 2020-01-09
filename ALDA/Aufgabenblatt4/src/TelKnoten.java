public class TelKnoten {
    int x;
    int y;

    public TelKnoten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TelKnoten) {
            if (this.x == ((TelKnoten) obj).x && this.y == ((TelKnoten) obj).y)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ("x: " + this.x + " y: " + this.y);
    }

    @Override
    public int hashCode() {
        return x * y * 31;
    }
}
