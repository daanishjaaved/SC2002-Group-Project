package Entity;

/**
 * Represents a non-player combatant 
 * Behavior is determined by an EnemyAction object
 */
public abstract class Enemy extends Combatant {
    private EnemyAction action;

    public Enemy(String name, int hp, int atk, int def, int spd, EnemyAction action) {
        super(name, hp, atk, def, spd);
        this.action = action;
    }

    public EnemyAction getAction() {
        return action;
    }

    public void doAction(Combatant target) {
        if (action != null) {
            action.execute(this, target);
        }
    }
}
