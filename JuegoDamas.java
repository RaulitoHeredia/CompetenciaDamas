import javax.swing.*;

public class JuegoDamas {
    private Tablero tablero;
    private String jugadorBlancas;
    private String jugadorNegras;
    private boolean turnoBlancas;

    public JuegoDamas() {
        solicitarNombresJugadores();
        tablero = new Tablero(this);
        turnoBlancas = true; // Comienzan las blancas
    }

    private void solicitarNombresJugadores() {
        jugadorBlancas = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador de las blancas:");
        jugadorNegras = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador de las negras:");
        if (jugadorBlancas == null || jugadorBlancas.trim().isEmpty()) jugadorBlancas = "Jugador Blancas";
        if (jugadorNegras == null || jugadorNegras.trim().isEmpty()) jugadorNegras = "Jugador Negras";
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean esTurnoBlancas() {
        return turnoBlancas;
    }

    public void cambiarTurno() {
        turnoBlancas = !turnoBlancas;
        verificarGanador();
    }

    private void verificarGanador() {
        boolean blancasTienenPiezas = tablero.tienePiezas(true);
        boolean negrasTienenPiezas = tablero.tienePiezas(false);

        if (!blancasTienenPiezas || !tablero.hayMovimientosValidos(true)) {
            mostrarGanador(jugadorNegras);
        } else if (!negrasTienenPiezas || !tablero.hayMovimientosValidos(false)) {
            mostrarGanador(jugadorBlancas);
        }
    }

    private void mostrarGanador(String ganador) {
        JOptionPane.showMessageDialog(null, "¡" + ganador + " ha ganado el juego!");
        System.exit(0);
    }
}
