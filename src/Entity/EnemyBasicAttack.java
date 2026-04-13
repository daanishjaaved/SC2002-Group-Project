package Entity;

/**
 * The most common behavioral strategy for enemies
 * Executes a straightforward physical attack using the standard damage formula
 */
public class EnemyBasicAttack implements EnemyAction {
    // Performs a single-target attack
    // The final damage is calculated within the target's takeDmg method
    // based on (self.atk - target.def).
    @Override
    public void execute(Combatant self, Combatant target) {
        // Directly applies the source's raw attack stat 
        // to the target's damage calculation logic
        target.takeDmg(self.getAtk());
    }
}
