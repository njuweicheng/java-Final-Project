package myworld.battlefield;

import myworld.creatures.Camp;
import myworld.creatures.Pos;

public class FormationInfo {
    private Pos cheerPos;                   //老爷爷/蛇精位置
    private Pos leadPos;                    //大娃/蝎子精位置
    private Pos[] otherPos=new Pos[6];      //葫芦娃/小喽喽位置
    private final Camp CAMP;
    private final Pos CENTRE;

    public FormationInfo(Camp camp,Pos pos){
        cheerPos=new Pos();
        leadPos=new Pos();
        for(int i=0;i<6;i++)
            otherPos[i]=new Pos();
        this.CAMP=camp;
        this.CENTRE=pos;
    }

    public Pos getCheerPos(){
        return cheerPos;
    }

    public Pos getLeadPos(){
        return leadPos;
    }

    public Pos[] getOtherPos(){
        return otherPos;
    }

    private void setCraneWing(){
        cheerPos.setPos(5,6);
        leadPos.setPos(5,5);

        otherPos[0].setPos(2,2);
        otherPos[1].setPos(3,3);
        otherPos[2].setPos(4,4);
        otherPos[3].setPos(6,4);
        otherPos[4].setPos(7,3);
        otherPos[5].setPos(8,2);
    }

    private void setWildGoose(){
        cheerPos.setPos(5,9);
        leadPos.setPos(5,5);

        otherPos[0].setPos(8,2);
        otherPos[1].setPos(7,3);
        otherPos[2].setPos(6,4);
        otherPos[3].setPos(4,6);
        otherPos[4].setPos(3,7);
        otherPos[5].setPos(2,8);
    }

    private void setYoke(){
        cheerPos.setPos(4,9);
        leadPos.setPos(5,2);

        otherPos[0].setPos(4,3);
        otherPos[1].setPos(5,4);
        otherPos[2].setPos(4,5);
        otherPos[3].setPos(5,6);
        otherPos[4].setPos(4,7);
        otherPos[5].setPos(5,8);
    }

    private void setSnake(){
        cheerPos.setPos(5,9);
        leadPos.setPos(5,2);

        otherPos[0].setPos(5,3);
        otherPos[1].setPos(5,4);
        otherPos[2].setPos(5,5);
        otherPos[3].setPos(5,6);
        otherPos[4].setPos(5,7);
        otherPos[5].setPos(5,8);
    }

    private void setFishScale(){
        cheerPos.setPos(5,5);
        leadPos.setPos(5,2);

        otherPos[0].setPos(6,3);
        otherPos[1].setPos(3,4);
        otherPos[2].setPos(4,4);
        otherPos[3].setPos(5,4);
        otherPos[4].setPos(6,4);
        otherPos[5].setPos(7,4);
    }

    private void setSquareArray(){
        cheerPos.setPos(5,6);
        leadPos.setPos(5,2);

        otherPos[0].setPos(4,3);
        otherPos[1].setPos(6,3);
        otherPos[2].setPos(3,4);
        otherPos[3].setPos(7,4);
        otherPos[4].setPos(4,5);
        otherPos[5].setPos(6,5);
    }

    private void setMeniscus(){
        cheerPos.setPos(7,6);
        leadPos.setPos(5,4);

        otherPos[0].setPos(7,2);
        otherPos[1].setPos(5,3);
        otherPos[2].setPos(6,3);
        otherPos[3].setPos(4,4);
        otherPos[4].setPos(5,5);
        otherPos[5].setPos(6,5);
    }

    private void setFrontal(){
        cheerPos.setPos(5,5);
        leadPos.setPos(5,2);

        otherPos[0].setPos(4,3);
        otherPos[1].setPos(5,3);
        otherPos[2].setPos(6,3);
        otherPos[3].setPos(3,4);
        otherPos[4].setPos(5,4);
        otherPos[5].setPos(7,4);
    }

    public void setFormationPos(Formation formation){
        switch (formation){
            case CRANEWING:setCraneWing();break;
            case WILDGOOSE:setWildGoose();break;
            case YOKE:setYoke();break;
            case SNAKE:setSnake();break;
            case FISHSCALE:setFishScale();break;
            case SQUAREARRAY:setSquareArray();break;
            case MENISCUS:setMeniscus();break;
            case FRONTAL:setFrontal();break;
            default:System.err.println("Unbelievable error about Formation.");
        }

        //根据中心点CENTRE确定位置
        if(CAMP==Camp.GOOD){
            cheerPos.setPos(cheerPos.x,CENTRE.y-cheerPos.y);
            leadPos.setPos(leadPos.x,CENTRE.y-leadPos.y);
            for(int i=0;i<6;i++)
                otherPos[i].setPos(otherPos[i].x,CENTRE.y-otherPos[i].y);
        }
        else{
            cheerPos.setPos(cheerPos.x,CENTRE.y+cheerPos.y);
            leadPos.setPos(leadPos.x,CENTRE.y+leadPos.y);
            for(int i=0;i<6;i++)
                otherPos[i].setPos(otherPos[i].x,CENTRE.y+otherPos[i].y);
        }
    }

}
