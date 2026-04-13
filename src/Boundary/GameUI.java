package Boundary;

import Entity.Action;
import Entity.Combatant;
import Entity.Enemy;
import Entity.Player;
import Entity.Item;

import java.util.List;

/**
 * Defines the boundary between the game logic and the user
 * Any implementation must provide methods for 
 * displaying game state and capturing user decisions
 */
public interface GameUI {

    // Shows the initial screen with available characters and enemies
    void displayLoadingScreen(List<Player> playerList, List<Enemy> enemyList);

    // Prompts the user to select their character class
    int getPlayerChoice(List<String> playerList);

    // Displays a prompt for selecting starting equipment
    void chooseInitialItems(int itemNumber, int totalItems);

    // Captures the user's selection for a specific item slot
    int getItemChoice(List<String> itemNames);

    // Prompts user to select a difficulty level (Easy, Medium, Hard)
    int getDifficultyChoice(List<String> list);

    // Battle Sequence Displays
    void displayRoundStart(int roundNumber);

    // Visualizes the order in which combatants will take their turns
    void displayTurnOrder(List<Combatant> turnOrder);

    // Shows the current player's status and resources at the start of their turn
    void displayPlayerTurn(Player player);

    // Input Capturing
    int getActionChoice(List<Action> actions);
    int getTargetChoice(List<Enemy> enemies);
    int getItemUseChoice(List<Item> items);

    // Displays formatted log messages
    void displayActionResult(String message);

    // display for enemy moves, showing damage and negation status
    void displayEnemyAttack(Enemy enemy, Player player, int dmg, boolean isDmgNegated);

    // Shows a summary of all combatants' HP and status at the end of a round
    void displayRoundEnd(Player player, List<Enemy> enemies, int roundNumber);

    // Game Over Sequence
    void displayVictory(Player player, int totalRounds);
    void displayDefeat(List<Enemy> remainingEnemies, int totalRounds);

    // Prompts the user to Replay, start a New Game, or Exit
    int getReplayChoice();
    void displayExitMessage();
}
