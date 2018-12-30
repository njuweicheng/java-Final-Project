package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import myworld.battlefield.*;
import myworld.creatures.*;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static myworld.battlefield.BattleField.CENTRE;

public class Controller {           //线程在这里创建，开始，把formation也拿出来，action也给我出来
    private BattleField battleField;
    private Formation goodFormation;        //葫芦娃阵营的阵型
    private Formation evilFormation;        //妖精阵营的阵型
    private FormationInfo goodFormationInfo;    //葫芦娃阵型位置信息
    private FormationInfo evilFormationInfo;        //妖精阵型位置信息

    private CbBrother[] cbBrothers=new CbBrother[CbBrother.MAX];
    private Grandpa grandpa;
    private Soldier[] soldiers=new Soldier[Soldier.MAX];
    private Scorpion scorpion;
    private Snake snake;

    private CreatureThread[] calabashThreads=new CreatureThread[CbBrother.MAX];
    private CreatureThread[] soldierThreads=new CreatureThread[Soldier.MAX];
    private CreatureThread scorpionThread;
    private CreatureThread grandpaThread;
    private CreatureThread snakeThread;

    private HashMap<String,Creature> creatureMap;

    private GameState state;

    private void setButtonAble(boolean able){
        cranewingButton.setDisable(able);
        wildgooseButton.setDisable(able);
        yokeButton.setDisable(able);
        snakeButton.setDisable(able);
        fishscaleButton.setDisable(able);
        squarearrayButton.setDisable(able);
        meniscusButton.setDisable(able);
        frontalButton.setDisable(able);
    }

    @FXML
    private AnchorPane myPane;
    @FXML
    private Button cranewingButton;
    @FXML
    private Button wildgooseButton;
    @FXML
    private Button yokeButton;
    @FXML
    private Button snakeButton;
    @FXML
    private Button fishscaleButton;
    @FXML
    private Button squarearrayButton;
    @FXML
    private Button meniscusButton;
    @FXML
    private Button frontalButton;

    @FXML
    public void onCranewingButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.CRANEWING){
            setGoodFormation(Formation.CRANEWING);
        }
    }
    @FXML
    public void onWildgooseButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.WILDGOOSE){
            setGoodFormation(Formation.WILDGOOSE);
        }
    }
    @FXML
    public void onYokeButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.YOKE){
            setGoodFormation(Formation.YOKE);
        }
    }
    @FXML
    public void onSnakeButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.SNAKE){
            setGoodFormation(Formation.SNAKE);
        }
    }
    @FXML
    public void onFishscaleButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.FISHSCALE){
            setGoodFormation(Formation.FISHSCALE);
        }
    }
    @FXML
    public void onSquarearrayButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.SQUAREARRAY){
            setGoodFormation(Formation.SQUAREARRAY);
        }
    }
    @FXML
    public void onMeniscusButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.MENISCUS){
            setGoodFormation(Formation.MENISCUS);
        }
    }
    @FXML
    public void onFrontalButtonClick(ActionEvent event){
        if(state==GameState.READY&&goodFormation!=Formation.FRONTAL){
            setGoodFormation(Formation.FRONTAL);
        }
    }

    private ImageView grandpaView;
    private ImageView[] calabashViews=new ImageView[CbBrother.MAX];

    private ImageView[] soldierViews=new ImageView[Soldier.MAX];
    private ImageView scorpionView;
    private ImageView snakeView;

    private Rectangle grandpaHp;
    private Rectangle[] calabashHp=new Rectangle[CbBrother.MAX];

    private Rectangle[] soldierHp=new Rectangle[Soldier.MAX];
    private Rectangle scorpionHp;
    private Rectangle snakeHp;

    ExecutorService exec;

    {
        //初始化ImageView
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

        //初始化Hp Rectangle
        grandpaHp=new Rectangle(45,5,Color.RED);
        for(int i=0;i<CbBrother.MAX;i++){
            calabashHp[i]=new Rectangle(45,5,Color.RED);
        }
        for(int i=0;i<Soldier.MAX;i++){
            soldierHp[i]=new Rectangle(45,5,Color.RED);
        }
        scorpionHp=new Rectangle(45,5,Color.RED);
        snakeHp=new Rectangle(45,5,Color.RED);
    }

    private void setState(GameState gameState){
        state=gameState;
        if(state==GameState.READY){
            setButtonAble(false);
        }
        else{
            setButtonAble(true);
        }
    }

    private boolean isGameOver(){               //state为RUN时检查是否结束，如果未结束或者其他state下误用返回false
        if(state==GameState.RUN){
            exec.shutdown();
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(exec.isTerminated()){
                setState(GameState.END);
                return true;
            }
        }
        return false;
    }

    private void initWorld(){
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
    }

    private void initFormation(){
        setGoodFormation(Formation.SNAKE);
        Random random=new Random();
        setEvilFormation(Formation.values()[random.nextInt(Formation.values().length)]);
    }

    /*
    private Creature valueOfCreature(String name){
        switch (name){
            case "大娃":return calabashThreads[0].getCreature();break;
            case "二娃":return calabashThreads[1].getCreature();break;
        }
    }
    */

    public void start(){
        if(state==GameState.READY) {
            Record record=new Record();
            battleField.setRecord(record);
            record.recordFormation(goodFormation.name(),evilFormation.name());

            setState(GameState.RUN);
            System.out.println("Game Start!");

            exec = Executors.newFixedThreadPool(16, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread=Executors.defaultThreadFactory().newThread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });
            for (int i = 0; i < CbBrother.MAX; i++) {
                exec.execute(calabashThreads[i]);
            }
            exec.execute(grandpaThread);

            exec.execute(scorpionThread);
            for (int i = 0; i < Soldier.MAX; i++) {
                exec.execute(soldierThreads[i]);
            }
            exec.execute(snakeThread);

//            exec.shutdown();
        }
    }             //开始游戏

    public void restart(){
        if(state==GameState.READY||state==GameState.END||isGameOver()){
            System.out.println("Restart");
            initWorld();
            initFormation();

            setState(GameState.READY);
        }
    }           //重新准备开始游戏

    public void replay(){                //回放,直接对生物体行为进行控制，实际上此时battleField对象上的数据是无效的
        if(state==GameState.READY||state==GameState.END||isGameOver()){
            GameState temp=state;
            setState(GameState.RUN);            //防止被打断

            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("选择记录文件");


            File recordPath=new File("./record");
            if(!recordPath.exists()||!recordPath.isDirectory()) {
                recordPath.mkdirs();
            }
            fileChooser.setInitialDirectory(recordPath);

//            fileChooser.setInitialDirectory(new File(this.getClass().getClassLoader().getResource("record").getPath()));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Record","*.txt"));

            File record=fileChooser.showOpenDialog(myPane.getScene().getWindow());      //读取的记录文件
//            System.out.println(record);

            Thread thread= new Thread(()-> {
                try {
                    if (record == null) {
                        setState(temp);
                        return;
                    }
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(record),"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    System.out.println("Replay");
                    String line = bufferedReader.readLine();
                    if (!line.equals("Calabash Brothers record.")) {
                        reader.close();
                        System.out.println(line);
                        throw new RecordException();
                    }

                    initWorld();
                    try {
                        line = bufferedReader.readLine();
                        setGoodFormation(Formation.valueOf(line));

                        line = bufferedReader.readLine();
                        setEvilFormation(Formation.valueOf(line));
                    } catch (IllegalArgumentException e) {        //valueOf可能抛出异常
                        reader.close();
                        throw new RecordException();
                    }

                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    String lastRecord="";
                    int liveNum=16;
                    int count=0;
                    while ((line = bufferedReader.readLine()) != null && !line.equals("Over")) {
                        String[] str = line.split(" ");
                        if (str.length == 0) {
                            reader.close();
                            throw new RecordException();
                        }

                        if (str[0].equals("attack")) {
                            count++;
                            if (str.length != 3) {
                                reader.close();
                                throw new RecordException();
                            }
                            Creature attacker=creatureMap.get(str[1]);
                            Creature target=creatureMap.get(str[2]);
                            if(attacker==null||target==null){
                                reader.close();
                                throw new RecordException();
                            }

                            if(lastRecord.equals("move")) {
                                count=0;
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            attacker.attack(target);
                            if(!target.isLive()){
                                target.setLabel(false);
                            }
                            lastRecord="attack";
                        } else if (str[0].equals("move")) {
                            count++;
                            if (str.length != 4) {
                                reader.close();
                                throw new RecordException();
                            }

                            Creature mover = creatureMap.get(str[1]);
                            int x = 0, y = 0;
                            try {
                                x = Integer.parseInt(str[2]);
                                y = Integer.parseInt(str[3]);
                            } catch (NumberFormatException e) {
                                reader.close();
                                throw new RecordException();
                            }

                            if(lastRecord.equals("attack")) {
                                count=0;
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            mover.moveto(x,y);
                            lastRecord="move";
                        }
                        else if(str[0].equals("dead")){
                            if(str.length!=2){
                                reader.close();
                                throw new RecordException();
                            }
                            Creature defunct=creatureMap.get(str[1]);
                            if(defunct==null){
                                reader.close();
                                throw new RecordException();
                            }
                            defunct.setLabel(false);
                            liveNum--;
                        }else {                       //错误格式
                            reader.close();
                            throw new RecordException();
                        }
                        if(count==liveNum){
                            count=0;
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (!line.equals("Over")) {
                        reader.close();
                        throw new RecordException();
                    }
                    setState(GameState.END);
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Fail to open the record.");
                    e.printStackTrace();
                } catch (RecordException e) {
                    e.printStackTrace();
                    Platform.runLater(()->{
                        new Alert(Alert.AlertType.ERROR, "不正确的记录文件！").showAndWait();
                    });
                    setState(GameState.END);
                    restart();
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void initialize(BattleField battleField) {
        this.battleField = battleField;
        goodFormationInfo = new FormationInfo(Camp.GOOD, CENTRE);
        evilFormationInfo = new FormationInfo(Camp.EVIL, CENTRE);

        try {
            //建立名称与生物体对象的map，可使用name直接获取creature;
            creatureMap=new HashMap<>();

            for(int i=0;i<CbBrother.MAX;i++){
                calabashViews[i].setFitWidth(45);
                calabashViews[i].setFitHeight(45);
                cbBrothers[i]=new CbBrother(calabashViews[i],calabashHp[i]);
                calabashThreads[i]=new CreatureThread(cbBrothers[i],battleField);
                myPane.getChildren().add(calabashViews[i]);
                myPane.getChildren().add(calabashHp[i]);
                creatureMap.put(cbBrothers[i].getName(),cbBrothers[i]);
            }
//            myPane.getChildren().addAll(calabashViews);

            grandpaView.setFitWidth(45);
            grandpaView.setFitHeight(45);
            grandpa=new Grandpa(grandpaView,grandpaHp);
            grandpaThread=new CreatureThread(grandpa,battleField);
            myPane.getChildren().add(grandpaView);
            myPane.getChildren().add(grandpaHp);
            creatureMap.put(grandpa.getName(),grandpa);

            for(int i=0;i<Soldier.MAX;i++){
                soldierViews[i].setFitWidth(45);
                soldierViews[i].setFitHeight(45);
                soldiers[i]=new Soldier(soldierViews[i],soldierHp[i]);
                soldierThreads[i]=new CreatureThread(soldiers[i],battleField);
                myPane.getChildren().add(soldierViews[i]);
                myPane.getChildren().add(soldierHp[i]);
                creatureMap.put(soldiers[i].getName(),soldiers[i]);
            }
//            myPane.getChildren().addAll(soldierViews);

            scorpionView.setFitWidth(45);
            scorpionView.setFitHeight(45);
            scorpion=new Scorpion(scorpionView,scorpionHp);
            scorpionThread=new CreatureThread(scorpion,battleField);
            myPane.getChildren().add(scorpionView);
            myPane.getChildren().add(scorpionHp);
            creatureMap.put(scorpion.getName(),scorpion);

            snakeView.setFitWidth(45);
            snakeView.setFitHeight(45);
            snake=new Snake(snakeView,snakeHp);
            snakeThread=new CreatureThread(snake,battleField);
            myPane.getChildren().add(snakeView);
            myPane.getChildren().add(snakeHp);
            creatureMap.put(snake.getName(),snake);

        } catch (CreateException e) {
            e.printStackTrace();
        }

        initFormation();
        setState(GameState.READY);
    }

    private void setGoodFormation(Formation formation) {
        goodFormation = formation;
        //获取formation的排列位置，将葫芦娃阵营置于相应的排列位置
        goodFormationInfo.setFormationPos(formation);

        grandpaThread.initMoveCreature(goodFormationInfo.getCheerPos());
        calabashThreads[0].initMoveCreature(goodFormationInfo.getLeadPos());
        for (int i = 0; i < 6; i++)
            calabashThreads[i + 1].initMoveCreature(goodFormationInfo.getOtherPos()[i]);

    }

    private void setEvilFormation(Formation formation) {
        evilFormation = formation;
        //获取formation的排列位置，将妖精阵营置于相应的排列位置
        evilFormationInfo.setFormationPos(formation);

        snakeThread.initMoveCreature(evilFormationInfo.getCheerPos());
        scorpionThread.initMoveCreature(evilFormationInfo.getLeadPos());
        for (int i = 0; i < 6; i++)
            soldierThreads[i].initMoveCreature(evilFormationInfo.getOtherPos()[i]);
    }
}
