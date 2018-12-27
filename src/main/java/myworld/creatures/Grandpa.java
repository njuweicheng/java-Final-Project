package myworld.creatures;
import javafx.scene.image.ImageView;

public class Grandpa extends GoodCamp implements Cheerable {
    private static boolean isBirth=false;

    public Grandpa(ImageView view) throws CreateException{
        super(view);
        if(isBirth){
            throw new CreateException("Only one Grandpa");
        }
        name="爷爷";

        //根据不同类型葫芦娃确定hp、atk和dis，甚至技能
        initHp=hp=200;
        initAtk=atk=0;
//        dis=1;

        isBirth=true;
    }

    @Override
    public void cheer() {

    }
}
