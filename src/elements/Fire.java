package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class Fire extends Element implements Serializable{
    public static final int DELAY_MOVIMENT = 5;
    private int countDelay = 0;
    
    public Fire(String imageName) {
        super(imageName);
        this.isMortal = true;
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
        
        countDelay++;
        if(countDelay == DELAY_MOVIMENT) {
            if(!this.moveRight())
                Drawing.getGameScreen().removeElement(this);
            
            countDelay = 0;
        }
    }
    
}
