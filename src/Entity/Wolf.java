package Entity;

/**
 * Wolf Enemy
 */
public class Wolf extends Enemy {

    public Wolf() {
        // Stats: Name, HP, ATK, DEF, SPD, Action
        super("Wolf", 40, 45, 5, 35, new EnemyBasicAttack());
    }
}
