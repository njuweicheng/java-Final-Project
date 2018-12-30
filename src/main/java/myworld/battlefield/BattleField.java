package myworld.battlefield;
import myworld.creatures.*;
import gui.Record;

public class BattleField {
    public static final int ROW=11;
    public static final int COL=19;
    public static final Pos CENTRE=new Pos(0,COL/2);

    private Position<Creature>[][] field=new Position[ROW][COL];

    private int goodNum;
    private int evilNum;

    private Record record;

    public BattleField() {
//        setRecord(record);

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                field[i][j] = new Position<Creature>();
            }
        }

        goodNum=CbBrother.MAX+1;
        evilNum=Soldier.MAX+1+1;
    }

    public void initField(){
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                setBody(i,j,null);
            }
        }

        goodNum=CbBrother.MAX+1;
        evilNum=Soldier.MAX+1+1;
    }

    public void setRecord(Record record){
        this.record=record;
    }

    private void overOne(Creature creature){
        if(creature.CAMP==Camp.GOOD)
            goodNum--;
        else
            evilNum--;
        creature.setLabel(false);
//        System.out.println("good: "+goodNum+"   evil: "+evilNum);
    }

    public synchronized Creature getBody(int x,int y){
        if(!inField(x,y)){
            return null;
        }
        return field[x][y].getBody();
    }
    public synchronized Creature getBody(Pos pos){
        return getBody(pos.x,pos.y);
    }

    public synchronized boolean inField(int x,int y){
        return (x>=0&&x<ROW&&y>=0&&y<COL);
    }
    public synchronized boolean inField(Pos pos){return inField(pos.x,pos.y);}

    public synchronized void setBody(int x,int y,Creature creature){
        field[x][y].setBody(creature);
    }
    public synchronized void setBody(Pos pos,Creature creature){
        field[pos.x][pos.y].setBody(creature);
    }

    @Deprecated
    public void showField(){    //打印战场信息
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                if(getBody(i,j)==null)
                    System.out.print("|     ");
                else if(!getBody(i,j).isLive())
                    System.out.print("| 尸体");
                else
                    System.out.print("| "+getBody(i,j).getName());
            }
            System.out.println("|");
        }
    }

    @Deprecated
    public synchronized boolean isLiveCreature(int x,int y){
        return getBody(x,y)!=null&&getBody(x,y).isLive();
    }

    public synchronized void moveCreature(Creature creature, int x,int y) {
        if(inField(x,y)&&y!=CENTRE.y) {         //不过楚河汉界
            if (getBody(x, y) == null) {
//                System.out.println(creature.getName() + " move from (" + creature.getX() + "," + creature.getY() + ") to (" + x + "," + y + ")");
                if(creature.isLive()) {
                    setBody(creature.getPos(), null);
                    setBody(x, y, creature);

                    creature.moveto(x, y);
                    record.recordMove(creature.getName(), x, y);
                }
            }
        }
    }

    public synchronized boolean attack(Creature creature,int x,int y){
        Creature target=getBody(x,y);
        if (creature.isEnemy(target)) {
            if(creature.isLive()&&target.isLive()) {
                creature.attack(target);
                record.recordAttack(creature.getName(), target.getName());
                if (!target.isLive()) {
                    overOne(target);
                    record.recordDead(target.getName());
                }
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isOver(){
        //每结束一个线程，相关数目-1
        return goodNum==0||evilNum==0;
    }

    public synchronized void overRecord(){
        record.recordOver();
    }
}
