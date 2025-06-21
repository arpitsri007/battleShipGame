package org.codekart.service;

import org.codekart.util.Pair;
import java.util.*;

public class SystematicFiringStrategy implements FiringStrategy {
    
    private final Map<String, Integer> playerPositions = new HashMap<>();
    private final Map<String, Set<Pair<Integer, Integer>>> hitCoordinates = new HashMap<>();

    @Override
    public Pair<Integer, Integer> getStrikeCoordinates(String playerId, int boardSize) {
        Set<Pair<Integer, Integer>> playerHits = hitCoordinates.computeIfAbsent(playerId, k -> new HashSet<>());
        int currentPosition = playerPositions.getOrDefault(playerId, 0);
        
        // Determine opponent's territory bounds
        int startX, endX;
        if ("A".equals(playerId)) {
            // Player A attacks Player B's territory (right half)
            startX = boardSize / 2;
            endX = boardSize;
        } else {
            // Player B attacks Player A's territory (left half)
            startX = 0;
            endX = boardSize / 2;
        }
        
        // Calculate coordinates systematically
        int territoryWidth = endX - startX;
        int x = startX + (currentPosition % territoryWidth);
        int y = currentPosition / territoryWidth;
        
        Pair<Integer, Integer> coordinates = new Pair<>(x, y);
        
        // Skip if already hit
        while (playerHits.contains(coordinates) && currentPosition < territoryWidth * boardSize) {
            currentPosition++;
            x = startX + (currentPosition % territoryWidth);
            y = currentPosition / territoryWidth;
            coordinates = new Pair<>(x, y);
        }
        
        if (!playerHits.contains(coordinates)) {
            playerHits.add(coordinates);
            playerPositions.put(playerId, currentPosition + 1);
        }
        
        return coordinates;
    }

    @Override
    public String getStrategyName() {
        return "Systematic Firing Strategy";
    }
} 