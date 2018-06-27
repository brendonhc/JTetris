package elements;

import utils.Consts;

import java.io.Serializable;
import java.util.Random;

public class TetrisObject implements Serializable {
    public TetrisObjectType type;
    public Square[] pieces = new Square[4];


    public TetrisObject() {
        type = TetrisObjectType.values()[new Random().nextInt(TetrisObjectType.values().length)];

        for (int i = 0; i < 4; i++) pieces[i] = new Square();

        switch (type) {
            case I:
                for (int i = 1; i < 4; i++) pieces[i].setPosition(i, Consts.NUM_COLUMNS/2);
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

}
