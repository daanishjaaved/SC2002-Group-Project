package Entity;

import java.util.List;
/**
 * PowerStone is a specific type of Item that allows a player to trigger
 * their special skill immediately without changing the skill's cooldown.
 */
public class PowerStone extends Item {
/**
     * Initializes the Power Stone with its name.
     */
  public PowerStone() {
    super("Power Stone");
  }

/**
     * return a string explaining the item's effect to the player.
     */
  @Override
  public String getDescription() {
    return "Activate your special skill once! (cooldown unchanged).";
  }

  /**
     * Links the item's targeting logic to the player's special skill.
     * user is the player using the stone.
     * return The Action associated with the player's special skill.
     */
  @Override
  public Action getTargetAction(Player user) {
    return user.getSpecialSkill();
  }

  @Override
  public void use(Player user, List<Combatant> targets) {
    // Capture current cooldown to prevent the stone from "wasting" a naturally ready skill
    int coolDown = user.getSpecialSkillCooldown();
    // Trigger the skill logic
    user.getSpecialSkill().execute(user, targets);
    // Restore the cooldown to its pre-use state
    user.setSpecialSkillCooldown(coolDown);
    // Mark this specific item instance as destroyed/used
    updateConsumed();
  }
}
