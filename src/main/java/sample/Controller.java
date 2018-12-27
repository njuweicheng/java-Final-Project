package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.control.TextField;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import myworld.battlefield.*;
import myworld.creatures.*;

//import javax.swing.text.html.ImageView;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static myworld.battlefield.BattleField.CENTRE;

public class Controller {           //线程在这里创建，开始，把formation也拿出来，action也给我出来
    private BattleField battleField;
    private Formation goodFormation;        //葫芦娃阵营的阵型
    private Formation evilFormation;        //妖精阵营的阵型
    private FormationInfo goodFormationInfo;    //葫芦娃阵型位置信息
    private FormationInfo evilFormationInfo;        //妖精阵型位置信息

    private CreatureThread[] calabashThreads=new CreatureThread[CbBrother.MAX];
    private CreatureThread[] soldierThreads=new CreatureThread[Soldier.MAX];
    private CreatureThread scorpionThread;
    private CreatureThread grandpaThread;
    private CreatureThread snakeThread;

    boolean gameStart;

    @FXML
    private AnchorPane myPane;

    private ImageView grandpaView;
    private ImageView[] calabashViews=new ImageView[CbBrother.MAX];

    private ImageView[] soldierViews=new ImageView[Soldier.MAX];
    private ImageView scorpionView;
    private ImageView snakeView;

    ExecutorService exec = Executors.newFixedThreadPool(16);

    {
        grandpaView=new ImageView(new Image("image/grandpa.jpg"));
        calabashViews[0]=new ImageView(new Image("image/red.jpg"));
        calabashViews[1]=new ImageView(new Image("image/orange.jpg"));
        calabashViews[2]=new ImageView(new Image("image/yellow.jpg"));
        calabashViews[3]=new ImageView(new Image("image/green.jpg"));
        calabashViews[4]=new ImageView(new Image("image/cyan.jpg"));
        calabashViews[5]=new ImageView(new Image("image/blue.jpg"));
        calabashViews[6]=new ImageView(new Image("image/purple.jpg"));
        soldierViews[0]=new ImageView(new Image("image/soldier.jpg"));
        soldierViews[1]=new ImageView(new Image("image/soldier.jpg"));
        soldierViews[2]=new ImageView(new Image("image/soldier.jpg"));
        soldierViews[3]=new ImageView(new Image("image/soldier.jpg"));
        soldierViews[4]=new ImageView(new Image("image/soldier.jpg"));
        soldierViews[5]=new ImageView(new Image("image/soldier.jpg"));
        scorpionView=new ImageView(new Image("image/scorpion.jpg"));
        snakeView=new ImageView(new Image("image/snake.jpg"));
    }

    public void start(){
        if(gameStart==false) {
            gameStart = true;
            System.out.println("Game Start!");

            for (int i = 0; i < CbBrother.MAX; i++) {
                exec.execute(calabashThreads[i]);
            }
            exec.execute(grandpaThread);

            exec.execute(scorpionThread);
            for (int i = 0; i < Soldier.MAX; i++) {
                exec.execute(soldierThreads[i]);
            }
            exec.execute(snakeThread);

            exec.shutdown();
        }
    }

    public void initialize(BattleField battleField) {
        this.battleField = battleField;
        goodFormationInfo = new FormationInfo(Camp.GOOD, CENTRE);
        evilFormationInfo = new FormationInfo(Camp.EVIL, CENTRE);

        try {
            for(int i=0;i<CbBrother.MAX;i++){
                calabashViews[i].setFitWidth(45);
                calabashViews[i].setFitHeight(45);
                calabashThreads[i]=new CreatureThread(new CbBrother(calabashViews[i]),battleField);
            }
            myPane.getChildren().addAll(calabashViews);
//            grandpaThread = new CreatureThread(new Grandpa(grandpa), battleField);
            grandpaView.setFitWidth(45);
            grandpaView.setFitHeight(45);
            grandpaThread=new CreatureThread(new Grandpa(grandpaView),battleField);
            myPane.getChildren().add(grandpaView);

            for(int i=0;i<Soldier.MAX;i++){
                soldierViews[i].setFitWidth(45);
                soldierViews[i].setFitHeight(45);
                soldierThreads[i]=new CreatureThread(new Soldier(soldierViews[i]),battleField);
            }
            myPane.getChildren().addAll(soldierViews);

            scorpionView.setFitWidth(45);
            scorpionView.setFitHeight(45);
            scorpionThread=new CreatureThread(new Scorpion(scorpionView),battleField);
            myPane.getChildren().add(scorpionView);

            snakeView.setFitWidth(45);
            snakeView.setFitHeight(45);
            snakeThread=new CreatureThread(new Snake(snakeView),battleField);
            myPane.getChildren().add(snakeView);
        } catch (CreateException e) {
            e.printStackTrace();
        }
//        battleField.showField();

        setGoodFormation(Formation.SNAKE);
        Random random=new Random();
        setEvilFormation(Formation.values()[random.nextInt(Formation.values().length)]);
//        init();
    }

    public void init(){
        gameStart=false;

        battleField.initField();
        for(int i=0;i<CbBrother.MAX;i++){
            calabashThreads[i].initCreature();
        }
        grandpaThread.initCreature();
        for(int i=0;i<Soldier.MAX;i++){
            soldierThreads[i].initCreature();
        }
        scorpionThread.initCreature();
        snakeThread.initCreature();

        setGoodFormation(Formation.SNAKE);
        Random random=new Random();
        setEvilFormation(Formation.values()[random.nextInt(Formation.values().length)]);
    }

    private void setGoodFormation(Formation formation){
        if(goodFormation!=formation){
            goodFormation=formation;
            //获取formation的排列位置，将葫芦娃阵营置于相应的排列位置
            goodFormationInfo.setFormationPos(formation);

            grandpaThread.initMoveCreature(goodFormationInfo.getCheerPos());
            calabashThreads[0].initMoveCreature(goodFormationInfo.getLeadPos());
            for(int i=0;i<6;i++)
                calabashThreads[i+1].initMoveCreature(goodFormationInfo.getOtherPos()[i]);
        }
    }

    private void setEvilFormation(Formation formation){
        if(evilFormation!=formation){
            evilFormation=formation;
            //获取formation的排列位置，将妖精阵营置于相应的排列位置
            evilFormationInfo.setFormationPos(formation);

            snakeThread.initMoveCreature(evilFormationInfo.getCheerPos());
            scorpionThread.initMoveCreature(evilFormationInfo.getLeadPos());
            for(int i=0;i<6;i++)
                soldierThreads[i].initMoveCreature(evilFormationInfo.getOtherPos()[i]);
        }
    }
}
