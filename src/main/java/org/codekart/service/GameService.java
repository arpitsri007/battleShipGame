package org.codekart.service;

import org.codekart.models.*;
import org.codekart.util.Pair;
import java.util.UUID;

public class GameService {
    private Game game;
    private FiringStrategy firingStrategy;

    public GameService(FiringStrategy firingStrategy) {
        this.firingStrategy = firingStrategy;
    }

    public String initGame(int boardSize) {
        if (boardSize <= 0 || boardSize % 2 != 0) {
            throw new IllegalArgumentException("Board size must be a positive even number");
        }
        
        String gameId = UUID.randomUUID().toString();
        this.game = new Game(gameId, boardSize);
        return gameId;
    }

    public boolean addShip(String shipId, int size, int posX1, int posY1, int posX2, int posY2) {
        validateGameExists();
        return game.addShip(shipId, size, posX1, posY1, posX2, posY2);
    }

    public void startGame() {
        validateGameExists();
        game.startGame();
    }

    public void playTurn() {
        validateGameExists();
        
        if (game.getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }
        
        if (game.isGameFinished()) {
            System.out.println("Game is already finished!");
            return;
        }

        Player currentPlayer = game.getCurrentPlayer();
        Pair<Integer, Integer> coordinates = firingStrategy.getStrikeCoordinates(
            currentPlayer.getId(), game.getBoard().getSize()
        );

        // Determine target coordinates based on current player
        int targetX, targetY;
        if ("A".equals(currentPlayer.getId())) {
            // Player A attacks Player B's territory
            targetX = coordinates.getFirst();
            targetY = coordinates.getSecond();
        } else {
            // Player B attacks Player A's territory
            targetX = coordinates.getFirst();
            targetY = coordinates.getSecond();
        }

        game.hitShip(currentPlayer, targetX, targetY);
        
        // Print remaining ships count
        printShipsRemaining();
    }

    private void printShipsRemaining() {
        Player playerA = game.getBoard().getPlayers().get(0);
        Player playerB = game.getBoard().getPlayers().get(1);
        System.out.println("Ships Remaining - " + playerA.getName() + ":" + 
                          playerA.getAliveShipCount() + ", " + playerB.getName() + ":" + 
                          playerB.getAliveShipCount());
    }

    public void viewBattleField() {
        validateGameExists();
        game.viewBattleField();
    }

    public boolean hitShip(Player player, int x, int y) {
        validateGameExists();
        return game.hitShip(player, x, y);
    }

    public void playFullGame() {
        validateGameExists();
        
        if (game.getState() != GameState.INITIALIZED) {
            throw new IllegalStateException("Game must be in initialized state to play full game");
        }

        startGame();
        
        while (!game.isGameFinished()) {
            playTurn();
        }
    }

    private void validateGameExists() {
        if (game == null) {
            throw new IllegalStateException("Game not initialized. Call initGame() first.");
        }
    }

    public Game getGame() {
        return game;
    }

    public boolean isGameFinished() {
        return game != null && game.isGameFinished();
    }
} 