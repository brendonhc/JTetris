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

    public static final String LIGHT_BLUE = "lightBlueSquare.png";    // I
    public static final String DARK_BLUE = "darkBlueSquare.png";      // J
    public static final String ORANGE = "orangeSquare.png";           // L
    public static final String YELLOW = "yellowSquare.png";           // O
    public static final String GREEN = "greenSquare.png";             // S
    public static final String RED = "redSquare.png";                 // Z
    public static final String PURPLE = "purpleSquare.png";           // T

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
