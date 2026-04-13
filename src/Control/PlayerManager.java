package Control;

import Entity.Player;
import Entity.Warrior;
import Entity.Wizard;

/*
    * Creates object of chosen Player class
    * 0: Warrior
    * 1: Wizard
    * Else: throw exception
*/
public class PlayerManager {
    public static Player choosePlayer(int index) {
        return switch (index) {
            case 0 -> new Warrior();
            case 1 -> new Wizard();
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        };
    }
}
