package Entity;

import java.util.List;
/**
 * Represents a generic item within the game.
 * This is an abstract class, it serves as a blueprint for specific 
 * item types like Potion, PowerStone, SmokeBomb etc.
 */
public abstract class Item {
  private String name;
  private boolean consumed; // Tracks if the item has been used up

  public Item(String name) {
    this.name = name;
    this.consumed = false; // By default, items are not consumed upon creation
  }
  /*
     * return The name of the item.
     */
  public String getName() {
    return name;
  }
  /*
     * Checks if the item has already been used/consumed.
     * return true if the item is gone, false otherwise.
     */
  public boolean isConsumed() {
    return consumed;
  }
  /*
     * Update the item as consumed. 
     * This should be called after an item is used if it is a single-use object.
     */
  public void updateConsumed() {
    this.consumed = true;
  }
  /*
     * Every item must provide a description of what it does.
     */
  public abstract String getDescription();
  /*
     * Defines the logic for when an item is used during gameplay.
     * user is the Player object using the item.
     * targets is the list of Combatants (enemies or player) the item affects.
     */
  public abstract void use(Player user, List<Combatant> targets);
  /*
     * Returns null by default if the item requires no specific target action.
     * user is the player initiating the action.
     * return the Action associated with target.
     */
  public Action getTargetAction(Player user) {
    return null;
  }
}
