package org.codekart.models;

import lombok.Data;

@Data
public class Cell {
    private Player player;
    private int x;
    private int y;
    private boolean isHit;
    private boolean isOccupied;
    private BattleShip ship;

    public Cell(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.isHit = false;
        this.isOccupied = false;
        this.ship = null;
    }

    public boolean isCellHit() {
        return isHit;
    }

    public boolean isCellOccupied() {
        return isOccupied;
    }

    public boolean hit() {
        if (isHit) {
            return false; // Already hit
        }
        this.isHit = true;
        return true;
    }

    public boolean hasShip() {
        return isOccupied && ship != null;
    }

    public void setShip(BattleShip ship) {
        this.ship = ship;
        this.isOccupied = true;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getPlayer() {
        return player;
    }

    public BattleShip getShip() {
        return ship;
    }
} 