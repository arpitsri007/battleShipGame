package org.codekart.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Board {
    private Cell[][] grid;
    private List<Player> players;
    private int size;

    public Board(int N) {
        this.size = N;
        this.grid = new Cell[N][N];
        Player playerA = new Player("A", "PlayerA", 0, N / 2);
        Player playerB = new Player("B", "PlayerB", N / 2, N);
        this.players = new ArrayList<>();
        players.add(playerA);
        players.add(playerB);
        initializeBoard();
    }

    private void initializeBoard() {
        int N = size;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Player owner;
                if (i < N / 2) {
                    owner = players.get(0); // Player A
                } else {
                    owner = players.get(1); // Player B
                }
                grid[i][j] = new Cell(i, j, owner);
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return null;
        }
        return grid[x][y];
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public boolean canPlaceShip(int x, int y, int size) {
        // Check if ship can be placed at given position
        for (int i = x; i < x + size && i < this.size; i++) {
            for (int j = y; j < y + size && j < this.size; j++) {
                if (grid[i][j].isCellOccupied()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShip(BattleShip ship, int startX, int startY) {
        for (int i = startX; i < startX + ship.getSize() && i < size; i++) {
            for (int j = startY; j < startY + ship.getSize() && j < size; j++) {
                grid[i][j].setShip(ship);
                ship.addOccupiedCell(grid[i][j]);
            }
        }
    }

    public List<Cell> getBoardLayout() {
        List<Cell> layout = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                layout.add(grid[i][j]);
            }
        }
        return layout;
    }

    public int getSize() {
        return size;
    }

    public List<Player> getPlayers() {
        return players;
    }
} 