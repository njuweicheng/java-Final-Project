package myworld.creatures;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Scorpion extends EvilCamp {
    private static boolean isBirth=false;

    public Scorpion(ImageView view,Rectangle rectangle) throws CreateException{
        super(view,rectangle);
        if(isBirth){
            throw new CreateException("Only one scorpion.");
        }
        name="蝎子";
        //根据不同类型葫芦娃确定hp、atk和dis，甚至技能
        initHp=hp=400;
        initAtk=atk=50;
//        dis=1;

        isBirth=true;
    }
}
