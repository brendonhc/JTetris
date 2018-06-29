package control;

import elements.*;
import utils.Consts;
import utils.Drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Janela principal do Jogo em ação
 * <p>
 *     Classe que implementa o jogo propriamente dito
 * </p>
 *
 *
 *
 * @author Sergio Piza, Brendon Hudson
 *  - Baseado em material do Prof. Luiz Eduardo
 */
public class GameFrame extends javax.swing.JFrame {
    private static final boolean FREE = false;
    private static final boolean OCCUPED = true;

    private ArrayList<Element> elemArray;
    private GameController controller;

    protected TetrisObject currentTetrisObject;
    private boolean gameMatrix[][];
    private boolean isFull;

    private int gameScreenWidth;
    private int gameScreenHeight;


    /**
     * Inicializa uma tela de jogo com seus elementos e obstáculos
     */
    public GameFrame() {
        Drawing.setGameFrame(this);
        initComponents();
        controller = new GameController(this); /*Controlador para o jogo atual*/

        /*O objeto GameController controller passa a "ouvir" o teclado*/
        this.addKeyListener(controller);

        gameScreenWidth = Consts.NUM_COLUMNS * Consts.CELL_SIZE + getInsets().left + getInsets().right;
        gameScreenHeight = Consts.NUM_LINES * Consts.CELL_SIZE + getInsets().top + getInsets().bottom;

        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(gameScreenWidth + 200, gameScreenHeight);

        elemArray = new ArrayList<Element>();

        /*Cria e adiciona elementos no "elemArray" */
        /* COMO ADICIONAR ELEMENTOS AO CENARIO:
        * Exemplo do LoLo:
        *
        * Element lolo = new Lolo("lolo.png");
        * lolo.setPosition(Consts.NUM_COLUMNS/2, Consts.NUM_LINES/2);
        * lolo.setTransposable(false);
        *
        * this.addElement(lolo);
        *
        * Para que as peças não o atravesse, defina como "true" sua posição
        * x e y em gameMatrix[x][y]
        */

        gameMatrix = new boolean[Consts.NUM_LINES][Consts.NUM_COLUMNS];
        isFull = false;
        currentTetrisObject = null;

        playGame();
    }

    /**
     * Método que inicia a lógica do game
     *
     * <p>
     *     Enquanto a "matriz" do game não está coberta até o topo com peças,
     *     é mandado mais peças para o jogador alocar
     * </p>
     */
    private void playGame() {

        /*Gera aleatóriamente peças do game até chegar ao topo*/
        if (!isFull) {
            currentTetrisObject = new TetrisObject();

            /*Adiciona os blocos da peça ao array de elementos do frame*/
            for (int i = 0; i < 4; i++) {
                this.addElement(currentTetrisObject.pieces[i]);
            }
        }
    }

    /**
     * Método que finaliza o jogo e retorna para o Menu
     */
    private void finishGame() {

        this.setVisible(false);

        Main.MAIN_MENU.setVisible(true);
        JOptionPane.showMessageDialog(Main.MAIN_MENU, "Game Over",
                "Game Over", JOptionPane.WARNING_MESSAGE);
    }

    private void saveGame() {

    }

    private void addElement(Element elem) {
        elemArray.add(elem);
    }

    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }

    @Override
    /**
     * Método que aparentemente desenha o jogo constantemente (Brendon)
     */
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();

        /*Criamos um contexto grafico*/
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        /* DESENHA CENARIO
           Trocar essa parte por uma estrutura mais bem organizada
           Utilizando a classe Stage
        */
        for (int i = 0; i < Consts.NUM_LINES; i++) {
            for (int j = 0; j < Consts.NUM_COLUMNS; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.IMG_PATH + "bricks.png");
                    g2.drawImage(newImage, j * Consts.CELL_SIZE,
                                           i * Consts.CELL_SIZE,
                                              Consts.CELL_SIZE,
                                              Consts.CELL_SIZE, null);

                } catch (IOException ex) {
                    Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        /*Desenha todos os elementos do "elemArray" na tela e escreve a peça atual*/
        this.controller.drawAllElements(elemArray, g2);
        this.controller.processAllElements(elemArray);
        this.setTitle(currentTetrisObject.getType().toString());

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }

        /*------------CONDICIONAIS IMPORTANTES----------------------
         *--------------------------------------------------------------------
         * 1º Verifica se a peça tocou em algo e se já deu o "TIME_FIRE"
         * 2º Marca as posições onde a peça parou como ocupadas na "gameMatrix"
         * -------------------------------------------------------------------*/
        if (objLowerBoundsIsOccuped(currentTetrisObject) && currentTetrisObject.pieces[0].getContIntervals() == Square.TIMER_FIRE - 1) {
            currentTetrisObject.desactivatePieces();
            /*MARCA COMO OCUPADO ONDE A PEÇA CAIU*/
            occupSquares(currentTetrisObject);

            /*Possível GAME_OVER*/
            if (currentTetrisObject.getObjectBoundaries().highestX < 3) {
                System.out.println("GAME OVER");
                isFull = true;

                finishGame();
            }
            /*Lança uma nova peça*/
            else {
                playGame();
            }
        }
    }

    /**
     * Método que verifica se o objeto está encostando em algo em baixo
     * <p>
     *     Para cada um dos Squares que compõem o objeto, é testado se há bloco
     *     bloqueado na "gameMatrix" em baixo, se houver, é retornado
     *     <em>true</em>, se não houver nada, <em>false</em>.
     * </p>
     * @param obj um GameObject qualquer
     * @return true or false
     */
    boolean objLowerBoundsIsOccuped(GameObject obj) {
        int x, y;

        for (Square s : obj.pieces) {
            x = s.getPos().getX(); y = s.getPos().getY();

            /*Se o Square atual está adjacente ao chão, true*/
            if (x == Consts.NUM_LINES-1) return true;

            /*Se está adjacente a qualquer outro obstaculo de "gameMatrix", true*/
            else if (gameMatrix[x + 1][y] == OCCUPED) return true;

        }
        return false;
    }

    /**
     * Método que verifica se o objeto está encostando em algo a esquerda
     * <p>
     *     Para cada um dos Squares que compõem o objeto, é testado se há bloco
     *     bloqueado na "gameMatrix" a esquerda, se houver, é retornado
     *     <em>true</em>, se não houver nada, <em>false</em>.
     * </p>
     * @param obj um GameObject qualquer
     * @return true or false
     */
    boolean objLeftBoundsIsOccuped(GameObject obj) {
        int x, y;
        for (Square s : obj.pieces) {
            x = s.getPos().getX();
            y = s.getPos().getY();

            /*O Square atual não pode estar adjacente a parede esquerda*/
            if (y == 0) return true;

            /*A posição adjacente esquerda não pode estar ocupada*/
            else if (gameMatrix[x][y-1] == OCCUPED) return true;

        }
        return false;
}


    /**
     * Método que verifica se o objeto está encostando em algo a direita
     * <p>
     *     Para cada um dos Squares que compõem o objeto, é testado se há bloco
     *     bloqueado na "gameMatrix" a direita, se houver, é retornado
     *     <em>true</em>, se não houver nada, <em>false</em>.
     * </p>
     * @param obj um GameObject qualquer
     * @return true or false
     */
    boolean objRightBoundsIsOccuped(GameObject obj) {
        int x, y;
        for (Square s : obj.pieces) {
            x = s.getPos().getX();
            y = s.getPos().getY();

            /*O Square atual não pode estar adjacente a parede esquerda*/
            if (y == Consts.NUM_COLUMNS - 1) return true;

            /*A posição adjacente esquerda não pode estar ocupada*/
            else if (gameMatrix[x][y+1] == OCCUPED) return true;

        }
        return false;
    }

    /**
     * Método que marca as posições correspondentes aos Squares do objeto em
     * questão como OCCUPED na "gameMatrix".
     * @param obj objeto que será marcado
     */
    private void occupSquares(GameObject obj) {
        for(Square s : obj.pieces) {
            gameMatrix[s.getPos().getX()][s.getPos().getY()] = OCCUPED;
        }
    }

    /**
     * Método que cria um TimerTask para dar constantes "repaint()" ao frame
     * até que "isFull" seja verdadeiro
     */
    public void go() {
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY_SCREEN_UPDATE);
    }


    /*Provavelmente Trecho herdado do GUI Form do netbeans, onde foi originalmente implementado o frame*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(Consts.GAME_NAME);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
