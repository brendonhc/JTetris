package control;

import elements.*;
import utils.Consts;
import utils.Drawing;

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
public class GameFrame extends javax.swing.JFrame implements KeyListener {

    private final Lolo lolo;
    private final ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private final TetrisObject activeTetrisObject;

    private int gameScreenWidth;
    private int gameScreenHeight;


    /**
     * Inicializa uma tela de jogo com seus elementos e obstáculos
     */
    public GameFrame() {
        Drawing.setGameFrame(this);
        initComponents();

        /*GameFrame atual passa a "ouvir" o teclado*/
        this.addKeyListener(this);

        gameScreenWidth = Consts.NUM_COLUMNS * Consts.CELL_SIZE + getInsets().left + getInsets().right;
        gameScreenHeight = Consts.NUM_LINES * Consts.CELL_SIZE + getInsets().top + getInsets().bottom;

        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(gameScreenWidth + 200, gameScreenHeight);

        elemArray = new ArrayList<Element>();

        /*Cria e adiciona elementos no "elemArray" */
        lolo = new Lolo("lolo.png");
        lolo.setPosition(Consts.NUM_COLUMNS/2, Consts.NUM_LINES/2);
        lolo.setTransposable(false);
        this.addElement(lolo);

        activeTetrisObject = new TetrisObject();
        for(int i = 0; i < 4; i++)
            this.addElement(activeTetrisObject.pieces[i]);
    }

    public final void addElement(Element elem) {
        elemArray.add(elem);
    }

    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }

    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();

        /*Criamos um contexto grafico*/
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        /* DESENHA CENARIO
           Trocar essa parte por uma estrutura mais bem organizada
           Utilizando a classe Stage
        */
        for (int i = 0; i < Consts.NUM_LINES                                                                                                                            ; i++) {
            for (int j = 0; j < Consts.NUM_COLUMNS; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.IMG_PATH + "bricks.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);

                } catch (IOException ex) {
                    Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        this.controller.drawAllElements(elemArray, g2);
        this.controller.processAllElements(elemArray);
        this.setTitle("-> Cell: " + lolo.getStringPosition());

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }


        if (activeTetrisObject.getObjectBoundaries().highestX == Consts.NUM_LINES - 1 && activeTetrisObject.pieces[0].getContIntervals() == Square.TIMER_FIRE - 1)
            activeTetrisObject.deactivatePieces();
    }

    /**
     * Método que cria um TimerTask para dar constantes "repaint()" ao frame
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

    /**
     * Método "ouvinte" das teclas que são pressionadas no teclado
     * @param e Evento de tecla
     */
    public void keyPressed(KeyEvent e) {
        Boundaries objBoundaries = activeTetrisObject.getObjectBoundaries();

        /* Para cada um dos 4 "Squares" que compõem a peça, faça o movimento
         * requisitado (direita, esquerda ou pra baixo) */
        for (int i = 0; i < 4 && activeTetrisObject.isActive; i++) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                if (objBoundaries.highestX < Consts.NUM_LINES - 1)
                    activeTetrisObject.pieces[i].moveDown();
                else
                    activeTetrisObject.deactivatePieces();

            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && objBoundaries.lowestY > 0) {
                activeTetrisObject.pieces[i].moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && objBoundaries.highestY < Consts.NUM_COLUMNS - 1) {
                activeTetrisObject.pieces[i].moveRight();
            } else break;
        }

        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
