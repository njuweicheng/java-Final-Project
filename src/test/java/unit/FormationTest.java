package unit;

import myworld.battlefield.Formation;
import myworld.battlefield.FormationInfo;
import myworld.creatures.Camp;
import myworld.creatures.Pos;
import org.junit.Test;

public class FormationTest extends FormationInfo {
    public FormationTest(){
        super(Camp.GOOD,new Pos(0,9));
    }

    private boolean isEqual(Pos pos,int x,int y){
        return pos.x==x&&pos.y==y;
    }

    @Test
    public void snakeFormationTest(){       //Test 长蛇阵
        String errorInfo="Snake Formation Error.";
        setFormationPos(Formation.SNAKE);

        assert isEqual(getCheerPos(),5,0):errorInfo;
        assert isEqual(getLeadPos(),5,7):errorInfo;
        for(int i=0;i<6;i++){
            assert isEqual(getOtherPos()[i],5,6-i):errorInfo;
        }
    }

    @Test
    public void wildgooseFormationTest(){       //Test 雁形阵
        String errorInfo="wildgoose Formation Error.";
        setFormationPos(Formation.WILDGOOSE);

        assert isEqual(getCheerPos(),5,0):errorInfo;
        assert isEqual(getLeadPos(),5,4):errorInfo;
        assert isEqual(getOtherPos()[0],8,7):errorInfo;
        assert isEqual(getOtherPos()[1],7,6):errorInfo;
        assert isEqual(getOtherPos()[2],6,5):errorInfo;
        assert isEqual(getOtherPos()[3],4,3):errorInfo;
        assert isEqual(getOtherPos()[4],3,2):errorInfo;
        assert isEqual(getOtherPos()[5],2,1):errorInfo;
    }
}
