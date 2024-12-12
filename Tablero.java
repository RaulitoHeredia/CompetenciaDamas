import java.awt.*; //para la creacion de interfaces graficas
import java.awt.event.*; //para manejar eventos 
import javax.swing.*; //para crear componenetes de la interfaz grafica

public class Tablero extends JPanel {
    private Pieza[][] casillas = new Pieza[8][8]; //matriz que representa las casillas del tablero 8x8
    private int casillaSeleccionadaX = -1; //coordenada x de la casilla seleccionada
    private int casillaSeleccionadaY = -1; //coordenada y de la casilla seleccionada
    private JuegoDamas juego; //referencia a la clase de JuegoDamas

    public Tablero(JuegoDamas juego) {
        this.juego = juego; //inicializa la refeerencia al juego
        inicializarPiezas(); //inicializa las piezas en el tablero
        setPreferredSize(new Dimension(600, 600)); // Tamaño del tablero
        agregarEventosDeRaton(); //agrega eventos de movimiento del raton al tablero
    }

    private void inicializarPiezas() {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (fila < 3 && (fila + columna) % 2 != 0) {
                    casillas[fila][columna] = new Pieza(false); //coloca las fichas negras en las primeras filas
                } else if (fila > 4 && (fila + columna) % 2 != 0) {
                    casillas[fila][columna] = new Pieza(true); // coloca las fichas blancas en las ultimas filas
                }
            }
        }
    }

    private void agregarEventosDeRaton() {
        addMouseListener(new MouseAdapter() { //eventos de raton
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = e.getX() / 75; // calcula la columna basada en la posicion del clic
                int fila = e.getY() / 75; //calcula la fila basada en la posicion del clic

                if (casillaSeleccionadaX == -1 && casillaSeleccionadaY == -1) {
                    // Seleccionar una casilla
                    if (casillas[fila][columna] != null && casillas[fila][columna].esBlanca() == juego.esTurnoBlancas()) {
                        casillaSeleccionadaX = columna; //guarda la columna seleccionada 
                        casillaSeleccionadaY = fila; //guarda la fila seleccionada
                        repaint(); //redibuja el tablero actualizado
                    }
                } else {
                    // Intentar mover la pieza
                    if (moverPieza(casillaSeleccionadaY, casillaSeleccionadaX, fila, columna)) {
                        juego.cambiarTurno(); //cambia el turno si el movimiento es valido
                    }
                    casillaSeleccionadaX = -1; //resetea la seleccion de la columna
                    casillaSeleccionadaY = -1; //resetea la seleccion de la fila
                    repaint(); //redibuja el tablero
                }
            }
        });
    }

    private boolean moverPieza(int desdeY, int desdeX, int hastaY, int hastaX) {
        if (hastaX < 0 || hastaX >= 8 || hastaY < 0 || hastaY >= 8 || casillas[hastaY][hastaX] != null) {
            return false; //verifica que el movimiento este dentro del tablero y la casilla de destino este vacia
        }

        Pieza pieza = casillas[desdeY][desdeX]; //obtiene la pieza a mover 
        if (pieza == null) return false; //verifica que exista una ficha en la casilla de origen

        int deltaX = Math.abs(hastaX - desdeX); //calcula la distancia horizontal del movimiento 
        int deltaY = hastaY - desdeY; //calcula la distancia vertical del movimiento

        if (pieza.esBlanca() && !pieza.esReina() && deltaY > 0) return false; //verifica movimiento valido para piezas blancas no reina
        if (!pieza.esBlanca() && !pieza.esReina() && deltaY < 0) return false; //verifica movimiento valido para piezas negras no reina

        if (deltaX == 1 && Math.abs(deltaY) == 1) {
            casillas[hastaY][hastaX] = pieza; //mueve la pieza a la nueva casilla
            casillas[desdeY][desdeX] = null; //vacia la casilla de origen
            return true; //movimiento valido
        } else if (deltaX == 2 && Math.abs(deltaY) == 2) {
            int capturarX = (desdeX + hastaX) / 2; //calcula la posicion de la pieza a capturar
            int capturarY = (desdeY + hastaY) / 2; //calcula la posicion de la pieza a capturar

            if (casillas[capturarY][capturarX] != null && casillas[capturarY][capturarX].esBlanca() != pieza.esBlanca()) {
                casillas[hastaY][hastaX] = pieza; //mueve la pieza a la nueva casilla
                casillas[desdeY][desdeX] = null; //vacia la casilla de origen
                casillas[capturarY][capturarX] = null; //elimina la pieza capturada
                return true; //indica movimiento valido
            }
        }

        return false; //movimiento no valido
    }

    public boolean tienePiezas(boolean esBlanca) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if (casillas[fila][columna] != null && casillas[fila][columna].esBlanca() == esBlanca) {
                    return true; //verifica si hay piezas de color especificado en el tablero
                }
            }
        }
        return false; //indica que no hay piezas del color especificado
    }

    public boolean hayMovimientosValidos(boolean esBlanca) {
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                Pieza pieza = casillas[fila][columna];
                if (pieza != null && pieza.esBlanca() == esBlanca) {
                    return true; // Por simplicidad, asume que hay movimientos validos si hay piezas del color especificado
                }
            }
        }
        return false; //no hay movimientos validos
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //llama al metodo de la superclase para pintar el componente

        // Dibujar el tablero
        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                if ((fila + columna) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY); //color de las casillas claras
                } else {
                    g.setColor(Color.DARK_GRAY); //color de las casillas oscuras
                }
                g.fillRect(columna * 75, fila * 75, 75, 75); //dibujo de las casillas

                // Dibujar las piezas
                if (casillas[fila][columna] != null) {
                    if (casillas[fila][columna].esBlanca()) {
                        g.setColor(Color.WHITE); //color de las piezas blancas
                    } else {
                        g.setColor(Color.BLACK); //color de las piezas negras
                    }
                    g.fillOval(columna * 75 + 10, fila * 75 + 10, 55, 55); //dibujo de la pieza
                }
            }
        }

        // Resaltar la casilla seleccionada
        if (casillaSeleccionadaX != -1 && casillaSeleccionadaY != -1) {
            g.setColor(Color.RED); //color del resaltado
            g.drawRect(casillaSeleccionadaX * 75, casillaSeleccionadaY * 75, 75, 75);//dibuja el borde de la casilla seleccionada
            g.drawRect(casillaSeleccionadaX * 75 + 1, casillaSeleccionadaY * 75 + 1, 73, 73); //dibuja un segundo borde para resaltar la casilla
        }
    }
}
