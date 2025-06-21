package org.codekart.service;

import org.codekart.util.Pair;
import java.util.*;

public class RandomFiringStrategy implements FiringStrategy {
    
    private final Map<String, Set<Pair<Integer, Integer>>> hitCoordinates = new HashMap<>();
    private final Random random = new Random();

    @Override
    public Pair<Integer, Integer> getStrikeCoordinates(String playerId, int boardSize) {
        Set<Pair<Integer, Integer>> playerHits = hitCoordinates.computeIfAbsent(playerId, k -> new HashSet<>());
        
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
        
        Pair<Integer, Integer> coordinates;
        do {
            int x = random.nextInt(endX - startX) + startX;
            int y = random.nextInt(boardSize);
            coordinates = new Pair<>(x, y);
        } while (playerHits.contains(coordinates));
        
        playerHits.add(coordinates);
        return coordinates;
    }

    @Override
    public String getStrategyName() {
        return "Random Firing Strategy";
    }
} 