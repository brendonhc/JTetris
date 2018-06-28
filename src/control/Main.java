package control;

/**
 * Projeto de POO 2018
 * 
 * @authors Brendon Hudson, Matheus Carvalho, Sergio Piza
 *
 * Baseado em material do Prof. Luiz Eduardo
 */
public class Main {

    /**
     * Método estático main que inicia o game com seu menu.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameMenu mainMenu = new GameMenu();
//                GameScreen screen = new GameScreen();
//                screen.setVisible(true);
//                screen.createBufferStrategy(2);
//                screen.go();

            }
        });
    }
}

