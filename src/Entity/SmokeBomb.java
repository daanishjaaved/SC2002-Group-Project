package Entity;

import java.util.List;
/**
 * SmokeBomb is a defensive item that grants the player temporary invulnerability.
 * It leverages the status effect to protect the player from damage.
 */
public class SmokeBomb extends Item {

  /**
     * Initializes the Smoke Bomb with its specific name.
     */
  public SmokeBomb() {
    super("Smoke Bomb");
  }

  /**
     * return A description of the defensive buff provided by the item.
     */
  @Override
  public String getDescription() {
    return "Negate all incoming damages for this turn and next turn!";
  }

  @Override
  public void use(Player user, List<Combatant> targets) {
    // Apply a new status effect to the user.
    // The '2' indicates the duration (this turn and the next).
    user.addStatusEffect(new SmokeBombInvulnerabilityEffect(2));
    // Flag the item as used so it is removed from the inventory.
    updateConsumed();
  }
}
