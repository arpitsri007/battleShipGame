package org.codekart.models;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class BattleShip {
    private String id;
    private int size;
    private boolean isDestroyed;
    private List<Cell> occupiedCells;
    private Player owner;

    public BattleShip(String id, int size, Player owner) {
        this.id = id;
        this.size = size;
        this.isDestroyed = false;
        this.occupiedCells = new ArrayList<>();
        this.owner = owner;
    }

    public void addOccupiedCell(Cell cell) {
        this.occupiedCells.add(cell);
        cell.setOccupied(true);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void checkAndUpdateDestroyedStatus() {
        this.isDestroyed = occupiedCells.stream().allMatch(Cell::isCellHit);
    }

    public boolean isHitAt(int x, int y) {
        return occupiedCells.stream()
                .anyMatch(cell -> cell.getX() == x && cell.getY() == y && cell.isCellHit());
    }

    public int getSize() {
        return size;
    }

    public String getId() {
        return id;
    }
} 