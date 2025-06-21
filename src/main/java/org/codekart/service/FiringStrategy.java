package org.codekart.service;

import org.codekart.util.Pair;

public interface FiringStrategy {
    Pair<Integer, Integer> getStrikeCoordinates(String playerId, int boardSize);
    String getStrategyName();
} 