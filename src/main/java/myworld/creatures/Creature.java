package myworld.creatures;

import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

abstract public class Creature {
    protected String name;      //名称
    public final Camp CAMP;

    protected int initHp;       //初始生命值(也即最大生命值)
    protected int initAtk;      //初始攻击力
    protected int hp;           //当前生命值
    protected int atk;          //当期攻击力
//    protected int dis;          //攻击距离

    protected Pos pos;

    final ImageView view;

    Creature(ImageView view) {
        if(this instanceof GoodCamp)
            CAMP=Camp.GOOD;
        else
            CAMP=Camp.EVIL;

        pos=new Pos();

        this.view=view;
    }

    public void init(){
        hp=initHp;
        atk=initAtk;
        pos.setPos(-1,-1);
    }

    public String getName(){
        return name;
    }

    /*
    public int getDis(){
        return dis;
    }
    */

   public void setLabel(boolean live){
       if(!live){
           Platform.runLater(new Runnable() {
               @Override
               public void run() {
//                   System.out.println("Are you here?");
                   ColorAdjust colorAdjust = new ColorAdjust();
                   colorAdjust.setBrightness(-0.5);
                   view.setEffect(colorAdjust);
               }
           });
       }
       else{
           Platform.runLater(()->{view.setEffect(null);});
       }
   }

    public void attack(Creature creature){
        creature.modifyHp(-atk);
//        System.out.println(this.name+" attack "+creature.getName());
   }

   public synchronized void modifyHp(int change){        //正为加血，负为伤害
        hp+=change;
        hp=hp>initHp?initHp:hp;             //加血不超过最大生命值
//       System.out.println(this.name+" hp is changed from "+(hp-change)+" to "+hp);
   }

   public synchronized boolean isLive(){
        return hp>0;
    }

   public synchronized void moveto(final int x,final int y){        //Creature的位置只会在其唯一对应的Thread里被修改
        pos.setPos(x,y);
       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               view.setX(25 + 50 * y);
               view.setY(25 + 50 * x);
               /*
               ColorAdjust colorAdjust=new ColorAdjust();
               colorAdjust.setBrightness(-0.5);
               view.setEffect(colorAdjust);
               */
           }
       });
   }
   public synchronized void moveto(Pos pos){
        moveto(pos.x,pos.y);
   }

   public synchronized Pos getPos(){
        return pos;
   }

   public synchronized int getX(){
        return pos.x;
   }

   public synchronized int getY(){
        return pos.y;
   }

   public boolean isEnemy(Creature creature){       //unnecessary?
       if(creature!=null&&creature.isLive())
            return this.CAMP!=creature.CAMP;
       return false;
   }
}
