package org.codekart;


import org.codekart.service.GameService;
import org.codekart.service.RandomFiringStrategy;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Battleship Game ===");
        
        // Initialize game service with random firing strategy
        GameService gameService = new GameService(new RandomFiringStrategy());
        
        try {
            // Initialize game with 6x6 board
            String gameId = gameService.initGame(6);
            System.out.println("Game initialized with ID: " + gameId);
            
            // Add ships for both players
            boolean shipAdded = gameService.addShip("SH1", 2, 1, 5, 4, 4);
            if (shipAdded) {
                System.out.println("Ship SH1 added successfully");
            } else {
                System.out.println("Failed to add ship SH1");
                return;
            }
            
            // View battlefield
            System.out.println("\n=== Battlefield Layout ===");
            gameService.viewBattleField();
            
            // Start and play the full game
            System.out.println("\n=== Starting Game ===");
            gameService.playFullGame();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 