package az.ingress;

public class TicTacToe {

    Integer id;
    Integer x;
    Integer y;
    Character player;

    public TicTacToe(Integer x, Integer y, Character player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Character getPlayer() {
        return player;
    }

}
