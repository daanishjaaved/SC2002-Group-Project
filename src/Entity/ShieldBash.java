package Entity;

import java.util.List;

public class ShieldBash extends SpecialSkill {
  /**
   * Constructs a new ShieldBash skill linked to a specific player.
   *
   * (Player player) is the player who owns and can use this skill.
   */  
  public ShieldBash(Player player) {
    super(player);
  }
  /**
   * Retrieves the display name of the skill.
   *
   * return The string "Shield Bash".
   */
  @Override
  public String getName() {
    return "Shield Bash";
  }
  /**
   * Retrieves the description of the skill.
   *
   * (Combatant player) is the combatant (player) using the skill.
   * return a string describing the skill's effects.
   */
  @Override
  public String getDesc(Combatant player) {
    return "Deals " + player.getAtk() + " damage to one enemy and stuns them for 2 turns. (Cooldown: 3 turns)";
  }

  /**
   * Executes the Shield Bash skill on a chosen target.
   *
   * (Combatant player) is the combatant using the skill.
   * (List<Combatant> targets) is the list of selected targets (expected to contain exactly one enemy).
   */
  @Override
  public void execute(Combatant player, List<Combatant> targets) {
    // Retrieve the first (and only expected) target from the list
    Combatant target = targets.get(0);
    // Deal damage equal to the player's attack stat
    target.takeDmg(player.getAtk());
    // Apply a stun effect. 
    target.addStatusEffect(new StunEffect(3));
    // Start the cooldown timer for this skill
    triggerCooldown();
  }

  /**
   * Determines if this skill requires the user to select a specific target.
   *
   * return true, as Shield Bash is a targeted attack.
   */
  @Override
  public boolean targetSelection() {
    return true;
  }

  /**
   * Determines if this skill affects multiple targets (Area of Effect).
   *
   * return false, as Shield Bash only hits a single target.
   */
  @Override
  public boolean isAOE() {
    return false;
  }
}
