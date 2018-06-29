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
import java.util.Arrays;
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
    private Pontuation points;

    protected TetrisObject currentTetrisObject;
    private Square[][] gameSquares;  // Cada quadrado do game
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
        points = new Pontuation(Consts.BASE_POINT_INC); /*Sistema de pontuação*/



        /*O objeto GameController controller passa a "ouvir" o teclado*/
        this.addKeyListener(controller);

        gameScreenWidth = Consts.NUM_COLUMNS * Consts.CELL_SIZE + getInsets().left + getInsets().right;
        gameScreenHeight = Consts.NUM_LINES * Consts.CELL_SIZE + getInsets().top + getInsets().bottom;

        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(gameScreenWidth + 200, gameScreenHeight);

        elemArray = new ArrayList<Element>();

        /*Inicializa matriz de controle de blocos ocupados na tela do game*/
        /*Adiciono e removo aqui todos blocos do game (inicializados com null)*/
        gameSquares = new Square[Consts.NUM_LINES][Consts.NUM_COLUMNS];
        for (Square[] s : gameSquares) Arrays.fill(s, null);
        isFull = false;

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
        * Para que as peças não o atravesse, adicione-o em gameSquare com
        * addGameObjectSquare()
        * x e y em gameSquare[x][y]
        */

        currentTetrisObject = null;

        /*Lança a primeira peça*/
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

    /**
     * Método que serializa o GameFrame atual, salvando o jogo
     */
    void saveGame() {

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
         * 2º Ocupa a "gameSquares" com cada Square do GameObject (peça)
         * -------------------------------------------------------------------*/
        if (objLowerBoundsIsOccuped(currentTetrisObject) && currentTetrisObject.pieces[0].getContIntervals() == Square.TIMER_FIRE - 1) {
            currentTetrisObject.desactivatePieces();

            /*MARCA COMO OCUPADO ONDE A PEÇA CAIU e adiciona seus squares a "rowsSquares"*/
            addGameObjectSquares(currentTetrisObject);

            /*VERIFICA SE HOUVE PONTUAÇÃO*/
            if (hasFilledRow()) {
                System.out.println("HOUVE PONTUAÇÃO!");
                int multPontuation = freeFilledRows();

            }
            /*Possível GAME_OVER*/
            else if (currentTetrisObject.getObjectBoundaries().highestX < 3) {
                System.out.println("GAME OVER");
                isFull = true;

                finishGame();
            }

            /*Lança uma NOVA PEÇA*/
            playGame();

        }
    }

    /**
     * Método que adiciona um objeto do tipo "GameObject" quadrado por quadrado
     * na matriz "gameSquares" que representa a tela do jogo.
     * @param obj objeto a ser adicionado
     */
    private void addGameObjectSquares(GameObject obj) {
        for (Square s : obj.pieces) {
            gameSquares[s.getPos().getX()][s.getPos().getY()] = s;
        }
    }

    /**
     * Verifica se há alguma linha preenchida atualmente
     * @return true, se sim, false, se não.
     */
    private boolean hasFilledRow() {
        /*Para cada linha, verificar se todos seus quadrados estão preenchidos*/
        for (int i = Consts.NUM_LINES-1; i >= 0 ; i--)
            if (isFilledRow(i)) return true;

        return false;
    }

    /**
     * Verifica se uma linha está preenchida
     * @param rowNumber número da linha a ser verificada
     * @return true, se estiver, false, se não estiver.
     */
    private boolean isFilledRow(int rowNumber) {
        for(Square s : gameSquares[rowNumber])
            if (s == null) return false;

        return true;
    }

    /**
     * Libera as linhas preenchidas
     * @return número de linhas liberadas
     */
    private int freeFilledRows() {
        int freedRows = 0;
        /*Para cada linha preenchida, libero seus squares para descerem*/
        for (int i = Consts.NUM_LINES-1; i >= 0; i--) {

            /*Enquanto ela estiver preenchida*/
            while (isFilledRow(i)) {
                freedRows++;

                /*1. Removo seus elementos*/
                for (Square s : gameSquares[i]) {
                    s.erase();
                    gameSquares[s.getPos().getX()][s.getPos().getY()] = null;
                }

                /*2. Desço todos os Squares que estavam acima*/


            }
        }
        return freedRows;
    }

    /**
     * Método que verifica se o objeto está encostando em algo em baixo
     * <p>
     *     Para cada um dos Squares que compõem o objeto, é testado se há bloco
     *     bloqueado na "gameSquares" em baixo, se houver, é retornado
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

            /*Se está adjacente a qualquer outro obstaculo de "gameSquares", true*/
            else if (gameSquares[x + 1][y] != null) return true;

        }
        return false;
    }

    /**
     * Método que verifica se o objeto está encostando em algo a esquerda
     * <p>
     *     Para cada um dos Squares que compõem o objeto, é testado se há bloco
     *     bloqueado na "gameSquares" a esquerda, se houver, é retornado
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
            else if (gameSquares[x][y-1] != null) return true;

        }
        return false;
}

    /**
     * Método que verifica se o objeto está encostando em algo a direita
     * <p>
     *     Para cada um dos Squares que compõem o objeto, é testado se há bloco
     *     bloqueado na "gameSquares" a direita, se houver, é retornado
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
            else if (gameSquares[x][y+1] != null) return true;

        }
        return false;
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
