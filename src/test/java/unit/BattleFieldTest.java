package unit;

import myworld.battlefield.BattleField;
import org.junit.Test;

public class BattleFieldTest extends BattleField {
    @Test
    public void inFieldTest(){
        String errorInfo="Boundary Error.";

        assert inField(5,5):errorInfo;
        assert !inField(66,6):errorInfo;
    }
}
