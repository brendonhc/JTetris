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
    private short rotatePosition;

    /**
     * Inicializa uma nova peça aleatória
     */
    public TetrisObject() {
        type = TetrisObjectType.values()[new Random().nextInt(TetrisObjectType.values().length)];
        squaresNumber = 4;
        type = TetrisObjectType.I;
        pieces = new Square[squaresNumber];
        isActive = true;
        rotatePosition = 0;

        // Para debug
        System.out.println("Novo TetrisObject: " + type);


        /*Monta a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < squaresNumber; i++) {
                    pieces[i] = new Square(Square.LIGHT_BLUE);
                    pieces[i].setPosition(0, Consts.NUM_COLUMNS / 2 + i - 2);
                }
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

    /**
     * Devolve o tipo enum correspondente a peça
     * @return enum com valor da letra correspondente a peça
     */
    public TetrisObjectType getType() {
        return type;
    }

    /**
     *  Método auxiliar para as rotações das peças
     */
    public void rotate() {
        switch (rotatePosition) {
            case 0:
                rotatePos1();
                rotatePosition = 1;
                break;
            case 1:
                rotatePos2();
                rotatePosition = 2;
                break;
            case 2:
                rotatePos3();
                rotatePosition = 3;
                break;
            case 3:
                rotatePos0();
                rotatePosition = 0;
                break;
        }
    }

    /**
     * Rotaciona a peça para posição inicial
     */
    void rotatePos0() {
        /*Rotaciona a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < squaresNumber; i++) {
                    pieces[i].setPosition(
                            getObjectBoundaries().highestX - 1, getObjectBoundaries().lowestY - 1 + i);
                }
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

    /**
     * Rotaciona a peça para a segunda posição
     */
    void rotatePos1() {
        /*Monta a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < 4; i++)
                    pieces[i].setPosition(
                            getObjectBoundaries().highestX-2+i, getObjectBoundaries().highestY-1);
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

    /**
     * Rotaciona a peça para a terceira posição
     */
    void rotatePos2() {
        /*Monta a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < squaresNumber; i++) {
                    pieces[i].setPosition(
                            getObjectBoundaries().highestX-1,getObjectBoundaries().highestY-2+i);
                }

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

    /**
     * Rotaciona a peça para a quarta posição
     */
    void rotatePos3() {
        /*Monta a peça de acordo com seu tipo: I, J, O, L, S, T, Z (TetrisObjectType)*/
        switch (type) {
            case I:
                for (int i = 0; i < 4; i++)
                    pieces[i].setPosition(getObjectBoundaries().highestX-2+i, getObjectBoundaries().lowestY+1);
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
