package elements;

import utils.Drawing;

import java.awt.*;
import java.io.Serializable;

public class Square extends Element implements Serializable {
    public static final int TIMER_FIRE = 40;
    private int contIntervals;
    private Boolean isActive = true;

    public Square() {
        super("caveira.png");
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
