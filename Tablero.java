import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tablero extends JPanel {
    private Pieza[][] casillas = new Pieza[8][8];
    private int casillaSeleccionadaX = -1;
    private int casillaSeleccionadaY = -1;
    private JuegoDamas juego;

    public Tablero(JuegoDamas juego) {
        this.juego = juego;
        inicializarPiezas();
        setPreferredSize(new Dimension(600, 600)); // Tamaño del tablero
        agregarEventosDeRaton();
    }

    private void inicializarPiezas() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (fila < 3 && (fila + columna) % 2 != 0) {
                    casillas[fila][columna] = new Pieza(false); // Negras
                } else if (fila > 4 && (fila + columna) % 2 != 0) {
                    casillas[fila][columna] = new Pieza(true); // Blancas
                }
            }
        }
    }

    private void agregarEventosDeRaton() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = e.getX() / 75;
                int fila = e.getY() / 75;

                if (casillaSeleccionadaX == -1 && casillaSeleccionadaY == -1) {
                    // Seleccionar una casilla
                    if (casillas[fila][columna] != null && casillas[fila][columna].esBlanca() == juego.esTurnoBlancas()) {
                        casillaSeleccionadaX = columna;
                        casillaSeleccionadaY = fila;
                        repaint();
                    }
                } else {
                    // Intentar mover la pieza
                    if (moverPieza(casillaSeleccionadaY, casillaSeleccionadaX, fila, columna)) {
                        juego.cambiarTurno();
                    }
                    casillaSeleccionadaX = -1;
                    casillaSeleccionadaY = -1;
                    repaint();
                }
            }
        });
    }

    private boolean moverPieza(int desdeY, int desdeX, int hastaY, int hastaX) {
        if (hastaX < 0 || hastaX >= 8 || hastaY < 0 || hastaY >= 8 || casillas[hastaY][hastaX] != null) {
            return false;
        }

        Pieza pieza = casillas[desdeY][desdeX];
        if (pieza == null) return false;

        int deltaX = Math.abs(hastaX - desdeX);
        int deltaY = hastaY - desdeY;

        if (pieza.esBlanca() && !pieza.esReina() && deltaY > 0) return false;
        if (!pieza.esBlanca() && !pieza.esReina() && deltaY < 0) return false;

        if (deltaX == 1 && Math.abs(deltaY) == 1) {
            casillas[hastaY][hastaX] = pieza;
            casillas[desdeY][desdeX] = null;
            return true;
        } else if (deltaX == 2 && Math.abs(deltaY) == 2) {
            int capturarX = (desdeX + hastaX) / 2;
            int capturarY = (desdeY + hastaY) / 2;

            if (casillas[capturarY][capturarX] != null && casillas[capturarY][capturarX].esBlanca() != pieza.esBlanca()) {
                casillas[hastaY][hastaX] = pieza;
                casillas[desdeY][desdeX] = null;
                casillas[capturarY][capturarX] = null;
                return true;
            }
        }

        return false;
    }

    public boolean tienePiezas(boolean esBlanca) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (casillas[fila][columna] != null && casillas[fila][columna].esBlanca() == esBlanca) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hayMovimientosValidos(boolean esBlanca) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = casillas[fila][columna];
                if (pieza != null && pieza.esBlanca() == esBlanca) {
                    return true; // Por simplicidad
                }
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar el tablero
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if ((fila + columna) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(columna * 75, fila * 75, 75, 75);

                // Dibujar las piezas
                if (casillas[fila][columna] != null) {
                    if (casillas[fila][columna].esBlanca()) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(columna * 75 + 10, fila * 75 + 10, 55, 55);
                }
            }
        }

        // Resaltar la casilla seleccionada
        if (casillaSeleccionadaX != -1 && casillaSeleccionadaY != -1) {
            g.setColor(Color.RED);
            g.drawRect(casillaSeleccionadaX * 75, casillaSeleccionadaY * 75, 75, 75);
            g.drawRect(casillaSeleccionadaX * 75 + 1, casillaSeleccionadaY * 75 + 1, 73, 73);
        }
    }
}
