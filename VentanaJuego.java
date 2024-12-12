import javax.swing.*; //para crear interfaces graficas

public class VentanaJuego extends JFrame { //define una clase que extiende JFrame para crear una ventana
    public VentanaJuego() {
        setTitle("Juego de Damas"); //Establece el titulo de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //configura la operacion de cierre para que termine la aplicacion

        JuegoDamas juego = new JuegoDamas(); //crea una instancia del juego de damas 
        add(juego.getTablero()); // Agrega el panel del tablero a la ventana

        pack(); // Ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false); //evita que la ventana se pueda redimensionar 
        setVisible(true); //hace visible la ventana
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaJuego::new); //inicia la aplicacion en el hilo de despacho de eventos de Swing
    }
}
