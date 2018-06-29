package control;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import utils.Consts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


/**
 * <b>Classe que implementa o menu do game e suas funcionalidades</b>
 *     <p>
 *         No primeiro momento é verificado se há ou não jogos salvos no diretório do game,
 *         se houver, a interface do botão "Continuar" fica normal, se não, fica como "clicado",
 *         não sendo possível escolher essa opção.
 *         <br>
 *         &emsp;Ao clicar em "Novo Jogo" (newGameButton), inicializa um jogo novo
 *         <br>
 *         &emsp;Ao clicar em "Continuar (continueButton), quando possível, é carregado um estado
 *         de jogo previamente salvo no diretório do jogo.
 *     </p>
 *
 * @author Brendon Hudson
 **/
public class GameMenu extends JFrame implements ActionListener {
    private GameFrame gameScreen;
    private File savedGame;

    private JPanel GameMenu;
    private JPanel Buttons;
    private JPanel Title;
    private JButton continueButton;
    private JButton newGameButton;
    private JButton optionsButton;

    private JPanel background;
    private ImageIcon imgBackground;

    /**
     * Inicializa a tela de menu com seus botões e opções, verificando se há gravações
     * para continuar o jogo, ou, apenas iniciar um novo
     */
    GameMenu() {
        setTitle(Consts.GAME_NAME);
        setResizable(false);
        add(GameMenu); // Este JPanel contém os outros componentes

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(Consts.NUM_COLUMNS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                Consts.NUM_LINES * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        savedGame = new File(Consts.SAVED_GAME_PATH);
        if (!savedGame.exists()) { // Se não existir gravação, impossibilita o "continueButton"
            continueButton.setEnabled(false);
        }

        // Listeners para os botões
        newGameButton.addActionListener(this);
        continueButton.addActionListener(this);
        optionsButton.addActionListener(this);
    }

    /**
     * Inicia um novo jogo a partir de uma nova instância de GameFrame
     */
    private void startNewGame() {
        gameScreen = new GameFrame();
        startGame(gameScreen);
    }

    /**
     * Inicia o jogo a partir de um GameFrame inicializado
     * <p>
     *     Serve para carregar jogos salvos anteriormente
     * </p>
     * @param gameScreen JFrame com o jogo inicializado
     */
    private void startGame(GameFrame gameScreen) {
        gameScreen.setVisible(true);
        gameScreen.createBufferStrategy(2);
        gameScreen.go();
    }

    /**
     * Método que responde as ações no Menu do Jogo
     * <p>
     *     <b>Novo Jogo</b>
     *         <p>Simplesmente, inicia um jogo do zero com o método startGame()</p>
     *
     *
     *     <b>Continuar</b>
     *         <p>Des-serializa o Objeto "GameFrame" serializado, referente a uma
     *             gravação previamente salva por um jogador, e então, inicia o jogo
     *             com o método startGame(savedGameScreen).
     *             <!--(É necessário verificar antes de a gravação em Consts.SAVED_GAME_PATH existe--></p>
     *
     *     <b>Opções:</b>
     *         <p>Lança uma janela com opções para um Novo Jogo como ... </p>
     *
     * @param e Evento gerado no frame do menu por algum de seus botões
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == newGameButton) {
            setVisible(false);
            startNewGame();
        }
        else if (src == continueButton) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(new FileInputStream(Consts.SAVED_GAME_PATH));
                GameFrame savedGameScreen = (GameFrame) in.readObject();
                startGame(savedGameScreen);
                JOptionPane.showMessageDialog(null,"Deu bom!!??");
                in.close();
            } catch (IOException | ClassNotFoundException exc) {
                // Gera uma caixa de dialogo com o problema do carregamento do jogo
                JOptionPane.showMessageDialog(this, exc.getMessage(),
                        "Problema no carregamento", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (src == optionsButton) {
            // Janela de opções
        }
    }
}
