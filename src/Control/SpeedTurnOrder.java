package Control;

import Entity.Combatant;

import java.util.ArrayList;
import java.util.List;

/**
 * SpeedTurnOrder implements a turn system where the fastest combatant act first.
 * It sorts combatants based on their Speed (SPD) stat in descending order.
 */
public class SpeedTurnOrder implements TurnOrderStrategy {
    /**
     * Determines the acting sequence by sorting the provided list by speed.
     * * combatants is the list of active participants in the battle.
     * return a new list of combatants ordered from highest speed to lowest.
     */
    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        // Create a shallow copy of the list to avoid modifying the original 
        List<Combatant> sorted = new ArrayList<>(combatants);
        // Sort the list using a Lambda expression:
        // (b - a) results in descending order (Fastest -> Slowest)
        sorted.sort((a, b) -> b.getSpd() - a.getSpd());
        return sorted;
    }
}
