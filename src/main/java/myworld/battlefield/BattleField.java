package myworld.battlefield;
import myworld.creatures.*;
import sample.Controller;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BattleField {
    public static final int ROW=11;
    public static final int COL=19;
    public static final Pos CENTRE=new Pos(0,COL/2);

    private Position<Creature>[][] field=new Position[ROW][COL];

//    private Lock actionLock=new ReentrantLock();
//    private int state=0;

    private int goodNum;
    private int evilNum;

//    private Controller controller;

    public BattleField() {
        //初始化生命体
        //初始为葫芦娃阵营长蛇阵，妖怪阵型随机，并显示
//        this.controller=controller;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                field[i][j] = new Position<Creature>();
            }
        }

        initField();
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

    private void overOne(Creature creature){
        if(creature.CAMP==Camp.GOOD)
            goodNum--;
        else
            evilNum--;
        creature.setLabel(false);
//        System.out.println("good: "+goodNum+"   evil: "+evilNum);
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
                setBody(creature.getPos(), null);
                setBody(x, y, creature);

                creature.moveto(x,y);
            }
        }
    }

    public synchronized boolean attack(Creature creature,int x,int y){
        Creature target=getBody(x,y);
        if (creature.isEnemy(target)) {
            creature.attack(target);
            if(!target.isLive()){
                overOne(target);
            }
            return true;
        }
        return false;
    }

    public synchronized boolean isOver(){
        //每结束一个线程，相关数目-1
        return goodNum==0||evilNum==0;
    }
}
