package org.codekart.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private String id;
    private String name;
    private List<BattleShip> ships;
    private boolean isAlive;
    private int territoryStartX;
    private int territoryEndX;

    public Player(String id, String name, int territoryStartX, int territoryEndX) {
        this.id = id;
        this.name = name;
        this.ships = new ArrayList<>();
        this.isAlive = true;
        this.territoryStartX = territoryStartX;
        this.territoryEndX = territoryEndX;
    }

    public void addShip(BattleShip ship) {
        this.ships.add(ship);
    }

    public boolean hasShipsAlive() {
        return ships.stream().anyMatch(ship -> !ship.isDestroyed());
    }

    public void checkAndUpdateAliveStatus() {
        this.isAlive = hasShipsAlive();
    }

    public int getAliveShipCount() {
        return (int) ships.stream().filter(ship -> !ship.isDestroyed()).count();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTerritoryStartX() {
        return territoryStartX;
    }

    public int getTerritoryEndX() {
        return territoryEndX;
    }

    public List<BattleShip> getShips() {
        return ships;
    }
} 