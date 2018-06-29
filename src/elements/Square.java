package elements;

import utils.Drawing;

import java.awt.*;
import java.io.Serializable;

/**
 * Classe que representa um quadrado individual da tela do game
 */
public class Square extends Element implements Serializable {
    public static final int TIMER_FIRE = 40;
    private int contIntervals;
    private Boolean isActive = true;

    public static String LIGHT_BLUE = "lightBlueSquare.png";    // I
    public static String DARK_BLUE = "darkBlueSquare.png";      // J
    public static String ORANGE = "orangeSquare.png";           // L
    public static String YELLOW = "yellowSquare.png";           // O
    public static String GREEN = "greenSquare.png";             // S
    public static String RED = "redSquare.png";                 // Z
    public static String PURPLE = "purpleSquare.png";           // T

    /**
     * Inicializa um objeto Square a partir de uma constante que indica a sua cor
     */
    public Square(String constColor) {
        super(constColor);
        this.isTransposable = false;
        this.contIntervals = 0;

    }

    public int getContIntervals() {
        return this.contIntervals;
    }

    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());

        if (isActive) {
            this.contIntervals++;
            if(this.contIntervals == TIMER_FIRE){
                this.contIntervals = 0;
                this.setPosition(pos.getX()+1,pos.getY());
                //Drawing.getGameScreen().addElement(f);
            }
        }

    }
}
