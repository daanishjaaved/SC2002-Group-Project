package Entity;

import java.util.List;

/**
 * Represents a basic "Defend" action available to combatants.
 * This action allows a combatant to temporarily increase their defense stat 
 * to mitigate incoming damage for the current and following turn.
 */
public class Defend implements Action {

  /**
   * Retrieves the display name of the action.
   *
   * return The string "Defend".
   */  
  @Override
  public String getName() {
    return "Defend";
  }

  /**
   * Retrieves the dynamic description of the action.
   * It calculates and previews the combatant's new defense stat if they choose to defend.
   *
   * (Combatant target) is the combatant (user) previewing the action.
   * return a string detailing the defense buff and duration.
   */
  @Override
  public String getDesc(Combatant target) {
    return "Increase DEF by 10 for 2 turns ("+ target.getDef() + " → " + (target.getDef() + 10) + ")";
  }

  /**
   * Executes the Defend action, applying the defensive buff to the user.
   *
   * (Combatant target) is the combatant performing the defend action (the source).
   * (List<Combatant> targets) is the list of targets (ignored in this case, as Defend targets the user).
   */
  @Override
  public void execute(Combatant target, List<Combatant> targets) {
    target.addStatusEffect(new DefendEffect(2));
  }

  /**
   * Determines if this action requires the player to select a target from the battlefield.
   *
   * return false, because defending automatically targets the user.
   */
  @Override
  public boolean targetSelection() {
    return false;
  }

  /**
   * Determines if the action is currently available to be used.
   *
   * (Combatant target) is the combatant attempting to use the action.
   * return true, as basic defending has no cooldowns or costs and is always available.
   */
  @Override
  public boolean isAvail(Combatant target) {
    return true;
  }

  /**
   * Determines if this action affects multiple targets (Area of Effect).
   *
   * return false, as this action only affects the user.
   */
  @Override
  public boolean isAOE() {
    return false;
  }
}
