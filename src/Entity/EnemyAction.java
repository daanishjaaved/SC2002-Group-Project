package Entity;

/**
 * Strategy interface for enemy behaviors
 * Can define how different enemy types behave 
 * during their turn without modifying the Enemy class
 */
public interface EnemyAction {
    // Executes the specific combat logic for an enemy
    void execute(Combatant self, Combatant target);
}

