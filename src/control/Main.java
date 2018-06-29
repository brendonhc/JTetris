package control;

/**
 * Projeto de POO 2018
 * 
 * @author Brendon Hudson, Matheus Carvalho, Sergio Piza
 *
 * Baseado em material do Prof. Luiz Eduardo
 */
public class Main {

    /**
     * Método estático main que inicia o game com seu menu.
     * @param args não utilizado na implementação.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameMenu mainMenu = new GameMenu();
                mainMenu.setVisible(true);
            }
        });
    }
}

