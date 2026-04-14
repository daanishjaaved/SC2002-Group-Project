package Entity;

import java.util.List;

/**
 * Represents the "Basic Attack" action available to all combatants.
 * This action deals physical damage to a single enemy based on the user's attack stat.
 */
public class BasicAttack implements Action {

  /**
   * Retrieves the display name of the action.
   *
   * return the string "Basic Attack".
   */
  @Override
  public String getName() {
    return "Basic Attack";
  }

  /**
   * Retrieves the description of the attack, calculating the exact damage 
   * based on the attacker's current stats.
   *
   * (Combatant target) is the combatant (attacker) previewing or using the action.
   * return a string describing the damage output.
   */
  @Override
  public String getDesc(Combatant target) {
    return "Deals " + target.getAtk() + "dmg to enemy.";
  }

  /**
   * Executes the basic attack on a chosen enemy.
   *
   * (Combatant target) is the combatant performing the attack (the source).
   * (List<Combatant> targets) is the list of selected targets (expected to contain exactly one enemy).
   */
  @Override
  public void execute(Combatant target, List<Combatant> targets) {
    // Retrieve the first (and only expected) enemy from the target list
    Combatant enemy = targets.get(0);
    // Deal damage to the enemy equal to the attacker's attack stat
    enemy.takeDmg(target.getAtk());
  }

  /**
   * Determines if this action requires the player to select a target from the battlefield.
   *
   * return true, as a basic attack must be aimed at a specific enemy.
   */
  @Override
  public boolean targetSelection() {
    return true;
  }

  /**
   * Determines if the action is currently available to be used.
   *
   * (Combatant target) is the combatant attempting to use the action.
   * return true, as basic attacks have no cooldowns or costs and are always available.
   */
  @Override
  public boolean isAvail(Combatant target) {
    return true;
  }

  /**
   * Determines if this action affects multiple targets (Area of Effect).
   *
   * return false, as a basic attack only hits a single target.
   */
  @Override
  public boolean isAOE() {
    return false;
  }
}
