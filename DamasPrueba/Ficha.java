package DamasPrueba;

public class Ficha {
    private final boolean esJugador1;
    private boolean esDama;

    public Ficha(boolean esJugador1) {
        this.esJugador1 = esJugador1;
        this.esDama = false;
    }

    public boolean esJugador1() {
        return esJugador1;
    }

    public boolean esDama() {
        return esDama;
    }

    public void convertirEnDama() {
        esDama = true;
    }

    @Override
    public String toString() {
        if (esJugador1) {
            return esDama ? "X" : "x";
        } else {
            return esDama ? "O" : "o";
        }
    }
}
