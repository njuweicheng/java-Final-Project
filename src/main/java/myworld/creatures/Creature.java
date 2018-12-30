package myworld.creatures;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

abstract public class Creature {
    protected String name;      //名称
    public final Camp CAMP;

    protected int initHp;       //初始生命值(也即最大生命值)
    protected int initAtk;      //初始攻击力
    protected int hp;           //当前生命值
    protected int atk;          //当期攻击力

    protected Pos pos;

    final ImageView view;
    final Rectangle rectangle;

    Creature(ImageView view, Rectangle rectangle) {
        if(this instanceof GoodCamp)
            CAMP=Camp.GOOD;
        else
            CAMP=Camp.EVIL;

        pos=new Pos();

        this.view=view;
        this.rectangle=rectangle;
    }

    public void init(){
        hp=initHp;
        atk=initAtk;
        pos.setPos(-1,-1);

        setLabel(true);
        rectangle.setWidth(45);
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
                   final Timeline timeline=new Timeline();
                   final KeyValue rotateValue=new KeyValue(view.rotateProperty(), 180f);
                   final KeyFrame kf=new KeyFrame(Duration.millis(500),rotateValue);

                   //将关键帧加到时间轴中
                   timeline.getKeyFrames().add(kf);
                   timeline.play();//运行

                   ColorAdjust colorAdjust = new ColorAdjust();
                   colorAdjust.setBrightness(-0.5);
                   view.setEffect(colorAdjust);
               }
           });
       }
       else{
           Platform.runLater(()->{
               if(view.getRotate()==180f) {
                   view.setRotate(0);
               }
               view.setEffect(null);
           });
       }
   }

    public synchronized void attack(Creature creature){
       /*
       cd--;
       if(creature instanceof Cheerable && cd==0){
           cd=initCd;
           return ((Cheerable) creature).cheer();
       }
       */

       int dec=Math.abs(getY()-creature.getY())+9*Math.abs(getX()-creature.getX());     //距离越远伤害越低
       if(dec>0.6*atk)   dec=(int)(0.6*atk);        //最少造成40%的伤害
       dec=atk-dec;

       //攻击动画
        Platform.runLater(()->{
            final Circle circle=new Circle(25+50*getY()+23,25+50*getX()+23,10);
            if(this instanceof GoodCamp){
                circle.setFill(Color.WHITE);
            }
            else{
                circle.setFill(Color.BLACK);
            }

            AnchorPane pane= (AnchorPane) (view.getParent());
            pane.getChildren().add(circle);

            final Timeline timeline=new Timeline();
            final KeyValue kvx=new KeyValue(circle.centerXProperty(), 25+50*creature.getY()+23);
            final KeyValue kvy=new KeyValue(circle.centerYProperty(), 25+50*creature.getX()+23);
            final KeyFrame kf=new KeyFrame(Duration.millis(1000), kvx,kvy);
            //将关键帧加到时间轴中
            timeline.getKeyFrames().add(kf);
            timeline.play();//运行

            timeline.setOnFinished((ActionEvent event)->{
                pane.getChildren().remove(circle);
            });
        });

       creature.modifyHp(-dec);
//       System.err.println(this.name+" attack "+creature.getName()+" "+dec+" HP.");
   }

   public synchronized void modifyHp(int change){        //正为加血，负为伤害
       if(isLive()) {
           hp += change;
           hp = hp > initHp ? initHp : hp;             //加血不超过最大生命值

           Platform.runLater(() -> {
               final Timeline timeline = new Timeline();
               final KeyValue widthValue = new KeyValue(rectangle.widthProperty(), 45 * (float) hp / initHp);
               final KeyFrame kf = new KeyFrame(Duration.millis(1000), widthValue);
               //将关键帧加到时间轴中
               timeline.getKeyFrames().add(kf);
               timeline.play();//运行
           });
       }
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
               final Timeline timeline=new Timeline();
               final KeyValue viewValueX=new KeyValue(view.xProperty(), 25+50*y);
               final KeyValue viewValueY=new KeyValue(view.yProperty(), 25+50*x);
               final KeyValue hpValueX=new KeyValue(rectangle.xProperty(),25+50*y);
               final KeyValue hpValueY=new KeyValue(rectangle.yProperty(),25+50*x+45);
               final KeyFrame kf=new KeyFrame(Duration.millis(1000),viewValueX,viewValueY,hpValueX,hpValueY);

               //将关键帧加到时间轴中
               timeline.getKeyFrames().add(kf);
               timeline.play();//运行

               /*
               view.setX(25 + 50 * y);
               view.setY(25 + 50 * x);
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
