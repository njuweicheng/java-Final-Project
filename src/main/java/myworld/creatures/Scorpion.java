package myworld.creatures;
import javafx.scene.image.ImageView;

public class Scorpion extends EvilCamp {
    private static boolean isBirth=false;

    public Scorpion(ImageView view) throws CreateException{
        super(view);
        if(isBirth){
            throw new CreateException("Only one scorpion.");
        }
        name="蝎子";
        //根据不同类型葫芦娃确定hp、atk和dis，甚至技能
        initHp=hp=500;
        initAtk=atk=40;
//        dis=1;

        isBirth=true;
    }
}
