import javax.swing.*;

public class VentanaJuego extends JFrame {
    public VentanaJuego() {
        setTitle("Juego de Damas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JuegoDamas juego = new JuegoDamas();
        add(juego.getTablero()); // Agrega el panel del tablero a la ventana

        pack(); // Ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaJuego::new);
    }
}
