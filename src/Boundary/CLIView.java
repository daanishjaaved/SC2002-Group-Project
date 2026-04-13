package Boundary;

import Entity.Player;
import Entity.Enemy;
import Entity.Combatant;
import Entity.StatusEffect;
import Entity.Action;
import Entity.Item;

import java.util.List;
import java.util.Scanner;


/**
 * Command Line Interface implementation of the GameUI
 * Handles all console output formatting and user input
 */
public class CLIView implements GameUI {
    private Scanner scanner;

    public CLIView() {
        this.scanner = new Scanner(System.in);
    }

    // Renders a formatted table of all active combatants and their starting stats
    @Override
    public void displayLoadingScreen(List<Player> playerList, List<Enemy> enemyList) {
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("                       FCS5 Group 1 Game                           ");
        System.out.println("------------------------------------------------------------------");
        
        System.out.println("\n[ LIST OF PLAYERS ]");
        for (Player p : playerList) {
            // Uses printf to ensure columns stay vertically aligned
            // %-8s: Left-aligned name (8 chars)
            // %-3d: Padded integer for HP to keep bars straight
            System.out.printf("  %-8s | HP: %-3d | ATK: %-2d | DEF: %-2d | SPD: %-2d | Skill: %s%n",
                p.getName(), p.getMaxHp(), p.getAtk(), p.getDef(), p.getSpd(),
                p.getSpecialSkill().getName());
        }

        System.out.println("\n[ LIST OF ENEMIES ]");
        for (Enemy e : enemyList) {
            System.out.printf("  %-8s | HP: %-3d | ATK: %-2d | DEF: %-2d | SPD: %-2d%n",
                e.getName(), e.getMaxHp(), e.getAtk(), e.getDef(), e.getSpd());
        }
        System.out.println("------------------------------------------------------------------\n");
    }

    @Override public int getPlayerChoice(List<String> playerList) {
        System.out.println("\n[ PLAYER SELECTION ]");
        System.out.println("Choose your character:");
        
        for (int i = 0; i < playerList.size(); i++) {
            System.out.print("(" + (i + 1) + ") " + playerList.get(i) + "  ");
        }
        System.out.println(); 
        
        return validateInput(1, playerList.size()) - 1;
    }

    public void chooseInitialItems(int itemNumber, int totalItems) {
        if (itemNumber == 1) {
            System.out.println("\n[ ITEM SELECTION ]");
            System.out.println("Choose " + totalItems + " items:");
        }
        System.out.print("Slot " + itemNumber + " > ");
    }

    @Override public int getItemChoice(List<String> itemNames) {
        for (int i = 0; i < itemNames.size(); i++) {
            System.out.print("(" + (i + 1) + ") " + itemNames.get(i) + "  ");
        }
        System.out.println();
        
        int choice = validateInput(1, itemNames.size());
        return choice - 1;
    }

    @Override public int getDifficultyChoice(List<String> list) { 
        System.out.println("\n[ DIFFICULTY SELECTION ]");
        System.out.println("\nSelect Difficulty:");
        for (int i = 0; i < list.size(); i++) System.out.println(" " + (i + 1) + ". " + list.get(i));
        return validateInput(1, list.size()) - 1; 
    }

    // Standardized input validator
    // Loops until the user provides an integer within the specified [min, max] range
    private int validateInput(int min, int max) {
        while (true) {
            System.out.print("> ");
            try {
                // Read line and trim to handle accidental whitespace
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } 
            catch (Exception e) {}
            System.out.println("Invalid input (" + min + "-" + max + ")");
        }
    }

    @Override public void displayRoundStart(int roundNumber) { 
        System.out.println("\n[ ROUND " + roundNumber + " ]"); 
    }

    // Displays the Turn Order of combatants
    // Example: Warrior -> Goblin A -> Goblin B
    @Override public void displayTurnOrder(List<Combatant> turnOrder) {
        System.out.print("Turn Order: ");
        for (int i=0; i<turnOrder.size(); i++) {
            System.out.print(turnOrder.get(i).getName() + (i < turnOrder.size()-1 ? " -> " : "\n"));
        }
    }

    // Displays the output for the player's turn, including HP, cooldowns, 
    // inventory status, and active status effects.
    @Override
    public void displayPlayerTurn(Player player) {
        System.out.println("\n>>> YOUR TURN: " + player.getName().toUpperCase());
        System.out.printf("    HP: %d/%d  |  ATK: %d  |  DEF: %d  |  SPD: %d%n", 
            player.getHp(), player.getMaxHp(), player.getAtk(), player.getDef(), player.getSpd());

        int cd = player.getSpecialSkillCooldown();
        System.out.print("    Skill: " + (cd == 0 ? "[READY]" : "[" + cd + " turns left]"));

        // Inline formatting for inventory list
        // Iterates through inventory and marks consumed items visually
        List<Item> inv = player.getInventory();
        System.out.print("  |  Items: ");
        if (inv.isEmpty()) System.out.print("none");
        else {
            for (int i = 0; i < inv.size(); i++) {
                Item it = inv.get(i);
                System.out.print(it.getName() + (it.isConsumed() ? " (USED)" : "") + (i < inv.size() - 1 ? ", " : ""));
            }
        }
        
        // Only display the effects line if the player actually has active effects
        List<StatusEffect> effects = player.getStatusEffects();
        if (!effects.isEmpty()) {
            System.out.print("\n    Active Effects: ");
            for (StatusEffect e : effects) System.out.print("[" + e.getName() + ": " + e.getDuration() + " turn] ");
        }
        System.out.println("\n---------------------------------------------------");
    }

    @Override public int getActionChoice(List<Action> actions) {
        System.out.print("Select Action: ");
        for (int i = 0; i < actions.size(); i++) System.out.print("(" + (i + 1) + ") " + actions.get(i).getName() + "  ");
        System.out.println();
        return validateInput(1, actions.size()) - 1;
    }

    @Override public int getTargetChoice(List<Enemy> enemies) {
        System.out.print("Select Target: ");
        for (int i = 0; i < enemies.size(); i++) System.out.print("(" + (i + 1) + ") " + enemies.get(i).getName() + " ");
        System.out.print(" (0) Cancel\n");
        int choice = validateInput(0, enemies.size());
        return (choice == 0) ? -1 : choice - 1;
    }

    @Override public int getItemUseChoice(List<Item> items) {
        System.out.println("Inventory:");
        for (int i = 0; i < items.size(); i++) System.out.println(" " + (i + 1) + ". " + items.get(i).getName() + " - " + items.get(i).getDescription());
        System.out.println(" 0. Cancel");
        int choice = validateInput(0, items.size());
        return (choice == 0) ? -1 : choice - 1;
    }

    @Override
    public void displayActionResult(String message) {
        System.out.println("  " + message);
    }

    // Displays the outcome of an Enemy's turn
    @Override
    public void displayEnemyAttack(Enemy enemy, Player player, int dmg, boolean isDmgNegated) {
        String result = isDmgNegated ? "NO DAMAGE X" : "dmg: " + dmg;
        System.out.printf("  %-10s -> Attack -> %-10s | %s -> HP: %d/%d%n", 
            enemy.getName(), player.getName(), result, player.getHp(), player.getMaxHp());
    }

    // Prints a post-round summary of health and status for all surviving combatants
    @Override
    public void displayRoundEnd(Player player, List<Enemy> enemies, int roundNumber) {
        System.out.println("\n-- End of Round " + roundNumber + " Summary --");
        System.out.printf("   %-10s : %d/%d HP%n", player.getName(), player.getHp(), player.getMaxHp());
        for (Enemy e : enemies) {
            String status = e.isAlive() ? e.getHp() + " HP" : "ELIMINATED X";
            if (e.isAlive() && e.isStunned()) status += " [STUNNED]";
            System.out.printf("   %-10s : %s%n", e.getName(), status);
        }
        System.out.println("---------------------------------------");
    }
    
    @Override public void displayVictory(Player player, int roundNumber) { 
        System.out.println("\n*** VICTORY in " + roundNumber + " rounds! ***"); 
    }
    
    @Override public void displayDefeat(List<Enemy> remainingEnemies, int totalRounds) { 
        System.out.println("\n--- DEFEAT (Survived " + totalRounds + " rounds) ---"); 
    }
    
    @Override public int getReplayChoice() { 
        System.out.println("\n1. Replay | 2. New Game | 3. Exit");
        return validateInput(1, 3); 
    }
    
    @Override public void displayExitMessage() { 
        System.out.println("Thanks for playing! Goodbye!"); 
    }
}