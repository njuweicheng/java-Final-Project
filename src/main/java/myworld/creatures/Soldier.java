package myworld.creatures;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Soldier extends EvilCamp {
    private static int birth=0;
    public static final int MAX=6;            //6个小喽啰

    public Soldier(ImageView view,Rectangle rectangle) throws CreateException{
        super(view,rectangle);
        if(birth>=MAX){
            throw new CreateException("Only twelve Soldiers.");
        }
        name="喽啰"+(birth+1);

        //根据不同类型葫芦娃确定hp、atk和dis，甚至技能
        initHp=hp=300;
        initAtk=atk=40;
//        dis=1;

        birth++;
    }
}
