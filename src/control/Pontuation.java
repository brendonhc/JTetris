package control;

public class Pontuation {
    private int points;
    private int base;

    /**
     * Construtor que recebe a base multiplicada pelo multiFactor nos ganhos de pontos
     * @param base
     */
    Pontuation(int base) {
        points = 0;
        this.base = base;
    }

    /**
     * Método que incrementa os pontos a partir de um fator multiplicativo que será
     * multiplicado pelo valor "base" e somados a "points".
     * @param multFactor fator multiplicativo
     */
    public void gain(int multFactor) {
        points += base*multFactor;
    }

    /**
     * Retorna a quantidade de pontos atual
     * @return points
     */
    public int getPoints() {
        return points;
    }
}