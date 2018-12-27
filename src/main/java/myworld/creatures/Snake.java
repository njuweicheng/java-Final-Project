package myworld.creatures;
import javafx.scene.image.ImageView;

public class Snake extends EvilCamp implements Cheerable {
    private static boolean isBirth=false;

    public Snake(ImageView view) throws CreateException{
        super(view);
        if(isBirth){
            throw new CreateException("Only one Snake.");
        }
        name="蛇精";

        initHp=hp=100;
        initAtk=atk=60;
//        dis=1;

        isBirth=true;
    }

    @Override
    public void cheer() {

    }
}
