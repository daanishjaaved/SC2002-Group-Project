package Entity;

import java.util.List;
/**
 * Potion is a consumable healing item.
 * It restores a fixed amount of health to the player upon use.
 */
public class Potion extends Item {
  // The amount of Health Points (HP) restored by this potion
  private int healAmount;

  /**
     * Initializes the Potion with a default name and a set healing value.
     */
  public Potion() {
    super("Potion");
    this.healAmount = 100;
  }

  /**
     * return The amount of HP this potion is capable of restoring.
     */
  public int getHealAmount() {
    return healAmount;
  }

  /**
     * return a description the healing.
     */
  @Override
  public String getDescription() {
    return "Restore 100 HP/to max HP.";
  }

  @Override
  public void use(Player user, List<Combatant> targets) {
    // Calls the heal method on the player object using the predefined healAmount
    user.heal(healAmount);
    // Marks the potion as used so it can be removed from the inventory
    updateConsumed();
  }
}
