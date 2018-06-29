package elements;

import utils.Consts;

import java.io.Serializable;
import java.util.Random;

/**
 * Classe que implementa uma peça qualquer do jogo
 *
 * <p> descrição detalhada do funcionamento da classe... </p>
 *
 * @author Sergio Piza, Brendon Hudson
 */
public class TetrisObject implements Serializable {
    public TetrisObjectType type;
    public Square[] pieces = new Square[4];
    public Boolean isActive = true;


    /**
     * Inicializa uma nova peça aleatória
     */
    public TetrisObject() {
        type = TetrisObjectType.values()[new Random().nextInt(TetrisObjectType.values().length)];

        // Para debug
        System.out.println("Novo TetrisObject: " + type);

        for (int i = 0; i < 4; i++) pieces[i] = new Square();

        /*Monta a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < 4; i++) pieces[i].setPosition(i, Consts.NUM_COLUMNS/2);
                break;

            case J:
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 2);
                break;

            case O:
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(0, Consts.NUM_COLUMNS/2 + 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                break;

            case L:
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2 + 2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 2);
                break;

            case S:
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 - 1);
                pieces[3].setPosition(0, Consts.NUM_COLUMNS/2 + 1);
                break;

            case T:
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 - 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                break;

            case Z:
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(0, Consts.NUM_COLUMNS/2 - 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                break;
        }
    }

    public Boundaries getObjectBoundaries() {
        Boundaries b = new Boundaries();
        b.highestX = pieces[0].pos.getX();
        b.lowestY = pieces[0].pos.getY();
        b.highestY = pieces[0].pos.getY();
        for (int i = 1; i < 4; i++) {
            if (pieces[i].pos.getX() > b.highestX) b.highestX = pieces[i].pos.getX();
            if (pieces[i].pos.getY() < b.lowestY) b.lowestY = pieces[i].pos.getY();
            if (pieces[i].pos.getY() > b.highestY) b.highestY = pieces[i].pos.getY();
        }

        //System.out.println(b.toString());

        return b;
    }

    public void deactivatePieces() {
        if (isActive) {
            this.isActive = false;
            for (int i = 0; i < 4; i++) pieces[i].deactivate();
        }
    }

}
