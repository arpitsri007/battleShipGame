package org.codekart.models;

import lombok.Data;
import java.util.List;
import java.util.Optional;

@Data
public class Game {
    private String gameId;
    private Board board;
    private GameState state;
    private Player currentPlayer;
    private Player winner;
    private int turnCount;

    public Game(String gameId, int boardSize) {
        this.gameId = gameId;
        this.board = new Board(boardSize);
        this.state = GameState.INITIALIZED;
        this.currentPlayer = board.getPlayers().get(0); // Player A starts
        this.turnCount = 0;
    }

    public boolean addShip(String shipId, int size, int posX1, int posY1, int posX2, int posY2) {
        if (state != GameState.INITIALIZED) {
            throw new IllegalStateException("Cannot add ships after game has started");
        }

        // Validate positions are within respective player territories
        Player playerA = board.getPlayers().get(0);
        Player playerB = board.getPlayers().get(1);

        if (!isValidShipPosition(posX1, posY1, size, playerA) || 
            !isValidShipPosition(posX2, posY2, size, playerB)) {
            return false;
        }

        // Check if ships can be placed
        if (!board.canPlaceShip(posX1, posY1, size) || !board.canPlaceShip(posX2, posY2, size)) {
            return false;
        }

        // Create and place ships
        BattleShip shipA = new BattleShip(shipId + "_A", size, playerA);
        BattleShip shipB = new BattleShip(shipId + "_B", size, playerB);

        board.placeShip(shipA, posX1, posY1);
        board.placeShip(shipB, posX2, posY2);

        playerA.addShip(shipA);
        playerB.addShip(shipB);

        return true;
    }

    private boolean isValidShipPosition(int x, int y, int size, Player player) {
        // Check if ship fits within player's territory
        return x >= player.getTerritoryStartX() && 
               x + size <= player.getTerritoryEndX() &&
               y >= 0 && y + size <= board.getSize();
    }

    public void startGame() {
        if (state != GameState.INITIALIZED) {
            throw new IllegalStateException("Game can only be started once");
        }
        
        if (board.getPlayers().get(0).getShips().isEmpty() || 
            board.getPlayers().get(1).getShips().isEmpty()) {
            throw new IllegalStateException("Both players must have ships to start the game");
        }

        this.state = GameState.IN_PROGRESS;
        System.out.println("Game started! " + currentPlayer.getName() + " goes first.");
    }

    public boolean hitShip(Player attackingPlayer, int x, int y) {
        if (state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }

        if (!attackingPlayer.equals(currentPlayer)) {
            throw new IllegalStateException("Not your turn");
        }

        Cell targetCell = board.getCell(x, y);
        if (targetCell == null) {
            return false;
        }

        // Check if already hit
        if (targetCell.isCellHit()) {
            return false;
        }

        boolean hit = targetCell.hit();
        turnCount++;

        if (hit && targetCell.hasShip()) {
            BattleShip hitShip = targetCell.getShip();
            hitShip.checkAndUpdateDestroyedStatus();
            
            System.out.println(attackingPlayer.getName() + "'s turn: Missile fired at (" + x + ", " + y + "). \"Hit\". " + 
                             targetCell.getPlayer().getName() + "'s ship with id \"" + hitShip.getId() + "\" destroyed.");
            
            // Check if ship is destroyed
            if (hitShip.isDestroyed()) {
                targetCell.getPlayer().checkAndUpdateAliveStatus();
            }
        } else {
            System.out.println(attackingPlayer.getName() + "'s turn: Missile fired at (" + x + ", " + y + "). \"Miss\"");
        }

        // Check for game end
        checkGameEnd();

        // Switch turns if game is still ongoing
        if (state == GameState.IN_PROGRESS) {
            switchTurn();
        }

        return hit;
    }

    private void switchTurn() {
        currentPlayer = currentPlayer.equals(board.getPlayers().get(0)) ? 
                       board.getPlayers().get(1) : board.getPlayers().get(0);
    }

    private void checkGameEnd() {
        Player playerA = board.getPlayers().get(0);
        Player playerB = board.getPlayers().get(1);

        if (!playerA.hasShipsAlive()) {
            winner = playerB;
            state = GameState.FINISHED;
            System.out.println("GameOver. " + winner.getName() + " wins.");
        } else if (!playerB.hasShipsAlive()) {
            winner = playerA;
            state = GameState.FINISHED;
            System.out.println("GameOver. " + winner.getName() + " wins.");
        }
    }

    public void viewBattleField() {
        System.out.println("Battlefield Status:");
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getCell(i, j);
                if (cell.isCellOccupied()) {
                    System.out.print(cell.getPlayer().getName() + "-" + cell.getShip().getId() + " ");
                } else {
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
    }

    public boolean isGameFinished() {
        return state == GameState.FINISHED;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getState() {
        return state;
    }
} 