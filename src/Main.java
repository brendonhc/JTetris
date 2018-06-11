import javax.swing.*;
import java.awt.*;

/**
 * Classe principal do programa.
 *
 */
public class Main {

	/**
	 * Função principal do programa.
	 *
	 * @param args argumentos de linha de comando
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame MainWindow = new StartWindow();
				MainWindow.setSize(400, 400);
				MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				MainWindow.setVisible(true); // NÃO FUNCIONA
			}
		});
	}

}
