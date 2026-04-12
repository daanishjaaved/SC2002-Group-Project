package Entity;

/**
 * Goblin Enemy
 */
public class Goblin extends Enemy {

    public Goblin() {
        // Stats: Name, HP, ATK, DEF, SPD, Action
        super("Goblin", 55, 35, 15, 25, new EnemyBasicAttack());
    }
}

