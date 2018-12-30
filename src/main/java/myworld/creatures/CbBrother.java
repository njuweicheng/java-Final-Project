package myworld.creatures;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CbBrother extends GoodCamp {
    public static final int MAX=7;
    private static int birth=0;

    private CalabashBrothers cb;
    public CbBrother(ImageView view, Rectangle rectangle) throws CreateException {
        super(view,rectangle);
        if(birth>=MAX){
            throw new CreateException("Only seven calabashBrothers.");
        }
        cb=CalabashBrothers.values()[birth++];
        name=cb.name;
        //根据不同类型葫芦娃确定hp、atk和dis，甚至技能
        initHp=hp=cb.initHp;
        initAtk=atk=cb.initAtk;
//        dis=1;
    }
}
