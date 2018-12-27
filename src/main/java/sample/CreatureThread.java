package sample;

import myworld.battlefield.BattleField;
import myworld.creatures.Camp;
import myworld.creatures.Creature;
//import javafx.scene.control.Label;
import myworld.creatures.Pos;

import static myworld.battlefield.BattleField.*;

public class CreatureThread implements Runnable{
    private Creature creature;
    private BattleField battleField;
//    Label label;
//    private final int currentState;
    CreatureThread(Creature creature, BattleField battleField){
        this.creature=creature;
        this.battleField=battleField;
//        this.label=label;
//        this.currentState=currentState;
    }

    @Deprecated
    public Creature getCreature(){
        return creature;
    }

    public void initCreature(){                         //非线程操作
        creature.init();
    }

    public void initMoveCreature(Pos target){           //非线程操作
        Creature temp = battleField.getBody(target);
        battleField.setBody(target,creature);
        if (battleField.inField(creature.getPos())) {
            battleField.setBody(creature.getPos(),temp);
        }
        if(temp!=null) {
            temp.moveto(creature.getPos());

//            battleField.moveto(temp,creature.getPos().x,creature.getPos().y);
        }
        creature.moveto(target);
//        battleField.moveto(creature,target.x,target.y);
    }

    private boolean attackLine(int x){
        int add = (creature.CAMP==Camp.GOOD)?1:-1;
        for(int j=CENTRE.y;j>=0&&j<COL;j+=add) {
            if(battleField.attack(creature,x,j)){
                return true;
            }
        }
        return false;
    }

    private int attack() {
        int x = creature.getX();
        for (int i = 0; i < ROW; i++) {
            if (attackLine(x + i))
                return x+i;
            if (attackLine(x - i))
                return x-i;
        }
        return -1;
    }

    private void action() {
//        actionLock.lock();
        if (!creature.isLive()) return;
        int x = creature.getX(),y=creature.getY();

        int target= attack();
        if(target==-1){
//            System.out.println("No enemy in field! Thread exception?");
            return ;
        }

//        System.out.println(creature.getName()+": "+creature.getX()+" , "+creature.getY());
        int add=0;
        if(target==x){
            add=creature.CAMP==Camp.GOOD?1:-1;
            battleField.moveCreature(creature, target,y+add);
        }
        else{
            add=target>x?1:-1;
            battleField.moveCreature(creature,x+add,y);
        }
    }

    @Override
    public void run() {
        while (!battleField.isOver()&&creature.isLive()) {
            action();
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("sleep in run() interrupted.");
                e.printStackTrace();
            }
        }
        /*
        if(!creature.isLive()) {
            System.out.println(creature.getName()+" dead.");
            battleField.overOne(creature);
        }
        */
    }
}
