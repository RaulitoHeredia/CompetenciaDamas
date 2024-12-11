package DamasPrueba;

import javax.swing.*;
import java.awt.*;

public class PanelTablero extends JPanel {
    private final Tablero tablero;
    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;
    private boolean turnoJugador1 = true;

    public PanelTablero(Tablero tablero) {
        this.tablero = tablero;
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = e.getY() / 75;
                int columna = e.getX() / 75;
                manejarClick(fila, columna);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarTablero(g);
    }

    private void dibujarTablero(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(j * 75, i * 75, 75, 75);

                String ficha = tablero.obtenerCelda(i, j);
                if (ficha.equals("x")) {
                    g.setColor(Color.RED);
                    g.fillOval(j * 75 + 10, i * 75 + 10, 55, 55);
                } else if (ficha.equals("o")) {
                    g.setColor(Color.BLUE);
                    g.fillOval(j * 75 + 10, i * 75 + 10, 55, 55);
                }
            }
        }
    }

    private void manejarClick(int fila, int columna) {
        if (filaSeleccionada == -1 && tablero.obtenerCelda(fila, columna).equals(turnoJugador1 ? "x" : "o")) {
            filaSeleccionada = fila;
            colSeleccionada = columna;
        } else if (filaSeleccionada != -1) {
            if (esMovimientoValido(filaSeleccionada, colSeleccionada, fila, columna)) {
                realizarMovimiento(filaSeleccionada, colSeleccionada, fila, columna);
                turnoJugador1 = !turnoJugador1;
            }
            filaSeleccionada = -1;
            colSeleccionada = -1;
        }
        repaint();
    }

    private boolean esMovimientoValido(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        String ficha = tablero.obtenerCelda(filaOrigen, colOrigen);
        String destino = tablero.obtenerCelda(filaDestino, colDestino);

        if (!destino.equals(" ")) {
            return false; // Movimiento inválido si el destino no está vacío
        }

        int desplazamientoFila = filaDestino - filaOrigen;
        int desplazamientoColumna = Math.abs(colDestino - colOrigen);

        if (Math.abs(desplazamientoFila) == 1 && desplazamientoColumna == 1) {
            // Movimiento simple
            return (turnoJugador1 && desplazamientoFila == 1) || (!turnoJugador1 && desplazamientoFila == -1);
        } else if (Math.abs(desplazamientoFila) == 2 && desplazamientoColumna == 2) {
            // Movimiento de captura
            int filaCaptura = (filaOrigen + filaDestino) / 2;
            int colCaptura = (colOrigen + colDestino) / 2;
            String fichaCapturada = tablero.obtenerCelda(filaCaptura, colCaptura);
            return (turnoJugador1 && fichaCapturada.equals("o")) || (!turnoJugador1 && fichaCapturada.equals("x"));
        }

        return false;
    }

    private void realizarMovimiento(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        if (Math.abs(filaDestino - filaOrigen) == 2) {
            // Movimiento de captura
            int filaCaptura = (filaOrigen + filaDestino) / 2;
            int colCaptura = (colOrigen + colDestino) / 2;
            tablero.eliminarFicha(filaCaptura, colCaptura);
        }

        tablero.moverFicha(filaOrigen, colOrigen, filaDestino, colDestino);

        // Promoción a dama
        if (turnoJugador1 && filaDestino == 7) {
            tablero.promocionarDama(filaDestino, colDestino);
        } else if (!turnoJugador1 && filaDestino == 0) {
            tablero.promocionarDama(filaDestino, colDestino);
        }
    }
}
