package DamasPrueba;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private final Tablero tablero;

    public VentanaPrincipal() {
        setTitle("Juego de Damas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tablero = new Tablero();
        tablero.inicializarTablero();

        PanelTablero panelTablero = new PanelTablero(tablero);
        add(panelTablero);

        setResizable(false);
    }
}