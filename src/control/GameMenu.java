package control;

import utils.Consts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameMenu extends JFrame implements ActionListener {
    private GameFrame gameScreen;

    private JPanel GameMenu;
    private JPanel Buttons;
    private JPanel Title;
    private JButton continueButton;
    private JButton newGameButton;
    private JButton optionsButton;

    private JPanel background;
    private ImageIcon imgBackground;

    /**
     * Inicializa a tela de menu com seus botões e opções
     */
    GameMenu() {
        setTitle(Consts.GAME_NAME);
        add(GameMenu); // Este JPanel contém os outros componentes

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(Consts.NUM_COLUMNS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                Consts.NUM_LINES * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        // Listeners para os botões
        newGameButton.addActionListener(this);
    }

    private void newGame() {
        gameScreen = new GameFrame();
        gameScreen.setVisible(true);
        gameScreen.createBufferStrategy(2);
        gameScreen.go();
    }

    /**
     * Método que responde as ações no Menu do Jogo
     * (Novo Jogo, Continuar, Opções)
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == newGameButton) {
            setVisible(false);
            newGame();
        }
    }
}
