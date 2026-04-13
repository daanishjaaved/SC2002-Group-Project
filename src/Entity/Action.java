package Entity;

import java.util.List;

public interface Action {
  String getName();

  // Returns a  description of the action for display/logging
  String getDesc(Combatant target);

  // Executes the action on the selected target(s)
  void execute(Combatant target, List<Combatant> targets);

  // Returns true if the action can currently be used (e.g. not on cooldown), vice versa
  boolean isAvail(Combatant target);

 // Indicates whether the BattleEngine must prompt for target selection
  boolean targetSelection();

  // Indicates whether the action affects multiple targets (AOE)
  boolean isAreaOfEffect();
}
