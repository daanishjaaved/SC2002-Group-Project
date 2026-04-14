package Entity;

import java.util.List;

// Represents an action where a player uses an item during combat
public class ItemAction implements Action {

    // The item associated with this action
    private Item selectedItem;

    public ItemAction(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    // Returns the item tied to this action
    public Item getItem() { 
        return selectedItem; 
    }

    /*
     * Some items (e.g. PowerStone) require target selection similar to skills.
     * This method returns an Action that handles target selection logic.
     * 
     * BattleEngine can reuse existing targeting flow using this delegate.
     * 
     * Returns:
     * -> An Action → if item requires external target selection handling
     * -> null → if item handles targeting internally or does not require targets
     */
    public Action getTarget(Player user) {
        return selectedItem.getTargetAction(user);
    }

    @Override
    public String getName() {
        // Display name shown in UI
        return (selectedItem != null) 
            ? "Use Item: " + selectedItem.getName() 
            : "Use Item";
    }

    @Override
    public String getDesc(Combatant target) {
        // Delegates description to the item itself if available
        return (selectedItem != null) 
            ? selectedItem.getDescription() 
            : "Use an item from your inventory.";
    }

    @Override
    public void execute(Combatant target, List<Combatant> targets) {
        // Executes item logic:
        // - Cast target to Player since only players can use items
        // - Pass selected targets to item for effect resolution
        selectedItem.use((Player) target, targets);
    }

    @Override
    public boolean isAvail(Combatant target) {
        // Action is only available if:
        // 1. target is a Player
        // 2. Player has at least one usable item
        if (target instanceof Player) {
            return ((Player) target).hasItems();
        }
        return false;
    }

    @Override
    public boolean targetSelection() {
        // Default: ItemAction itself does not require targeting
        // Targeting may instead be handled by delegated action (if any)
        return false;
    }

    @Override
    public boolean isAOE() {
        // ItemAction itself does not define AOE behaviour
        // AOE logic should be handled inside the item implementation
        return false;
    }
}