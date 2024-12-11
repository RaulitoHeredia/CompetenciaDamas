package DamasPrueba;
public class Tablero {
    private final String[][] tablero;

    public Tablero() {
        this.tablero = new String[8][8];
    }

    public void inicializarTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) {
                    if (i < 3) {
                        tablero[i][j] = "x"; // Fichas del jugador 1
                    } else if (i > 4) {
                        tablero[i][j] = "o"; // Fichas del jugador 2
                    } else {
                        tablero[i][j] = " "; // Espacios vacíos
                    }
                } else {
                    tablero[i][j] = " "; // Espacios vacíos
                }
            }
        }
    }

    public String obtenerCelda(int fila, int columna) {
        return tablero[fila][columna];
    }

    public void moverFicha(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        tablero[filaDestino][colDestino] = tablero[filaOrigen][colOrigen];
        tablero[filaOrigen][colOrigen] = " ";
    }

    public void eliminarFicha(int fila, int columna) {
        tablero[fila][columna] = " ";
    }

    public void promocionarDama(int fila, int columna) {
        if (tablero[fila][columna].equals("x")) {
            tablero[fila][columna] = "X";
        } else if (tablero[fila][columna].equals("o")) {
            tablero[fila][columna] = "O";
        }
    }
}
