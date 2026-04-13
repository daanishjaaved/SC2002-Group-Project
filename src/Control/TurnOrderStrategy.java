package Control;

import Entity.Combatant;

import java.util.List;

/**
 * Interface for implementing the logic of deciding the order in which 
 * combatants act each round.
 * * Using the Strategy Pattern here allows the game to support different 
 * systems (e.g., Agility-based, Round Robin, or Random order) by simply 
 * swapping the implementation.
 */
public interface TurnOrderStrategy {
/**
     * Takes a list of alive combatants and returns them in the correct acting order.
     * * combatants is the list of all entities currently participating in the battle.
     * return a sorted list of Combatants representing the sequence of turns for the round.
     */
     List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
