package control;

import utils.Consts;

import javax.swing.*;


public class GameMenu extends JFrame {
    private JPanel GameMenu;
    private JPanel Buttons;
    private JPanel Title;
    private JButton continueButton;
    private JButton newGameButton;
    private JButton optionsButton;

    /**
     * Inicializa a tela de menu com seus botões e opções
     */
    GameMenu() {
        setTitle(Consts.GAME_NAME);
        add(GameMenu); // Este JPanel contém os outros componentes

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(Consts.NUM_COLUMNS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                Consts.NUM_LINES * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);
    }
}
