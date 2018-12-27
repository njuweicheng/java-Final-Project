package myworld.creatures;

public class Pos {                     //作struct用
    public int x;
    public int y;

    public Pos() {
        this.x = this.y = -1;
    }

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(Pos pos){
        setPos(pos.x,pos.y);
    }

}
