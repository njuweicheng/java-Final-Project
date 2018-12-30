package myworld.creatures;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Snake extends EvilCamp implements Cheerable {
    private static boolean isBirth=false;

    public Snake(ImageView view,Rectangle rectangle) throws CreateException{
        super(view,rectangle);
        if(isBirth){
            throw new CreateException("Only one Snake.");
        }
        name="蛇精";

        initHp=hp=250;
        initAtk=atk=80;
//        dis=1;

        isBirth=true;
    }

    @Override
    public void cheer() {
    }
}
