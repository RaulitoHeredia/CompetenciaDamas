public class Pieza {
    private boolean esBlanca;
    private boolean esReina;

    public Pieza(boolean esBlanca) {
        this.esBlanca = esBlanca;
        this.esReina = false;
    }

    public boolean esBlanca() {
        return esBlanca;
    }

    public boolean esReina() {
        return esReina;
    }

    public void convertirEnReina() {
        esReina = true;
    }
}
