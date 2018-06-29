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
public class TetrisObject extends GameObject implements Serializable {
    private TetrisObjectType type;

    /**
     * Inicializa uma nova peça aleatória
     */
    public TetrisObject() {
        type = TetrisObjectType.values()[new Random().nextInt(TetrisObjectType.values().length)];
        squaresNumber = 4;
        pieces = new Square[squaresNumber];
        isActive = true;

        // Para debug
        System.out.println("Novo TetrisObject: " + type);


        /*Monta a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.LIGHT_BLUE);
                for (int i = 0; i < 4; i++) pieces[i].setPosition(i, Consts.NUM_COLUMNS/2);
                break;

            case J:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.DARK_BLUE);
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 2);
                break;

            case O:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.YELLOW);
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(0, Consts.NUM_COLUMNS/2 + 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                break;

            case L:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.ORANGE);
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2 + 2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 2);
                break;

            case S:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.GREEN);
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 - 1);
                pieces[3].setPosition(0, Consts.NUM_COLUMNS/2 + 1);
                break;

            case T:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.PURPLE);
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(1, Consts.NUM_COLUMNS/2 - 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                break;

            case Z:
                for (int i = 0; i < 4; i++) pieces[i] = new Square(Square.RED);
                pieces[0].setPosition(0, Consts.NUM_COLUMNS/2);
                pieces[1].setPosition(1, Consts.NUM_COLUMNS/2);
                pieces[2].setPosition(0, Consts.NUM_COLUMNS/2 - 1);
                pieces[3].setPosition(1, Consts.NUM_COLUMNS/2 + 1);
                break;
        }
    }

    public TetrisObjectType getType() {
        return type;
    }

}
