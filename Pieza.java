public class Pieza {
    private boolean esBlanca; //indica si la pieza es blanca
    private boolean esReina; //indica si la pieza es una reina

    public Pieza(boolean esBlanca) {
        this.esBlanca = esBlanca; //el juego inicia con este color de ficha
        this.esReina = false;  // el juego inicia con todas las piezas como NO reina
    }

    public boolean esBlanca() {
        return esBlanca; //devuelve si la pieza es blanca
    }

    public boolean esReina() {
        return esReina; // devuelve si la pieza es una reina
    }

    public void convertirEnReina() {
        esReina = true; //convierte la pieza en una reina
    }
}
