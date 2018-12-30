package myworld.creatures;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Grandpa extends GoodCamp implements Cheerable {
    private static boolean isBirth=false;

    public Grandpa(ImageView view,Rectangle rectangle) throws CreateException{
        super(view,rectangle);
        if(isBirth){
            throw new CreateException("Only one Grandpa");
        }
        name="爷爷";

        //根据不同类型葫芦娃确定hp、atk和dis，甚至技能
        initHp=hp=200;
        initAtk=atk=20;
//        dis=1;

        isBirth=true;
    }

    @Override
    public void cheer() {
    }
}
