package Control;

import Entity.Goblin;
import Entity.Wolf;

import java.util.Arrays;
import java.util.Collections;

/*
    * Creates object of class "Level" for chosen difficulty
    * Set up environment for corresponding difficulty
    
    * EASY: Initial wave: 3 Goblins, No backup wave
    * MEDIUM: Initial wave: 1 Goblin & 1 Wolf, Backup wave: 2 Wolves
    * HARD: Initial wave: 2 Goblins, Backup wave: 1 Goblin & 2 Wolves
*/
public class LevelManager {
    public static Level chooseLevel(Difficulty diff) {
        return switch (diff) {
            case EASY -> new Level(diff, diff.getDesc(), 
                Arrays.asList(new Goblin(), new Goblin(), new Goblin()), 
                Collections.emptyList());

            case MEDIUM -> new Level(diff, diff.getDesc(), 
                Arrays.asList(new Goblin(), new Wolf()), 
                Arrays.asList(new Wolf(), new Wolf()));

            case HARD -> new Level(diff, diff.getDesc(), 
                Arrays.asList(new Goblin(), new Goblin()), 
                Arrays.asList(new Goblin(), new Wolf(), new Wolf()));
        };
    }
}
