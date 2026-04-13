package Control;

import Entity.Item;
import Entity.Potion;
import Entity.PowerStone;
import Entity.SmokeBomb;


/*
    * Creates object of chosen item
    * 0: Potion
    * 1: PowerStone
    * 2: SmokeBomb
    * Else: Throw exception
    
    * Returns object(item) created
*/
public class ItemManager {
    public static Item chooseItem(int index) {
        return switch (index) {
            case 0 -> new Potion();
            case 1 -> new PowerStone();
            case 2 -> new SmokeBomb();
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        };
    }
}
