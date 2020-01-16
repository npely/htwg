public class TelVerbindung {

    TelKnoten anfang;
    TelKnoten ende;
    int c;

    public TelVerbindung(TelKnoten anfang, TelKnoten ende, int c) {
        this.anfang = anfang;
        this.ende = ende;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Anfangsknoten: " + this.anfang + " Endknoten: " + this.ende + " Kosten: " + this.c;
    }
}
