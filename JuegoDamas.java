import javax.swing.*; // Importa las clases necesarias para crear interfaces gráficas

public class JuegoDamas { //declaracion de variables (todos los private)
    private Tablero tablero;
    private String jugadorBlancas;
    private String jugadorNegras;
    private boolean turnoBlancas; //variable para controlar el turno de las fichas blancas

    public JuegoDamas() {
        solicitarNombresJugadores(); //solicita el nombre de los jugadores
        tablero = new Tablero(this);
        turnoBlancas = true; // Comienzan las fichas blancas
    }

    private void solicitarNombresJugadores() { //solicita el nombre de los jugadores para las fichas blancas y negras
        jugadorBlancas = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador de las blancas:");
        jugadorNegras = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador de las negras:");
        if (jugadorBlancas == null || jugadorBlancas.trim().isEmpty()) jugadorBlancas = "Jugador Blancas"; // si el campo del nombre esta vacio se va asignar un nombre por defecto 
        if (jugadorNegras == null || jugadorNegras.trim().isEmpty()) jugadorNegras = "Jugador Negras";
    }

    public Tablero getTablero() {
        return tablero; //devuelve el tablero del juego 
    }

    public boolean esTurnoBlancas() {
        return turnoBlancas; //devuelve el mando si es el turno de las fichas blancas
    }

    public void cambiarTurno() {
        turnoBlancas = !turnoBlancas; //cambia el turno al otro jugador
        verificarGanador(); //verifica si hay un ganador despues de cambiar el turno
    }

    private void verificarGanador() { 
        //verifica si las blancas tienen piezas en el tablero
        boolean blancasTienenPiezas = tablero.tienePiezas(true);
        //verifica si las negras tienen piezas en el tablero
        boolean negrasTienenPiezas = tablero.tienePiezas(false);

        if (!blancasTienenPiezas || !tablero.hayMovimientosValidos(true)) {
            mostrarGanador(jugadorNegras); //si las blancas no tienen piezas, las negras ganan
        } else if (!negrasTienenPiezas || !tablero.hayMovimientosValidos(false)) {
            mostrarGanador(jugadorBlancas); //si las negras no tienen piezas, las blancas ganan
        }
    }

    private void mostrarGanador(String ganador) {
        //mostrar el mensaje indicando al ganador del juego
        JOptionPane.showMessageDialog(null, "¡" + ganador + " ha ganado el juego!");
        System.exit(0); // con esto se termina el juego y cierra la aplicacion y la ventana
    }
}
