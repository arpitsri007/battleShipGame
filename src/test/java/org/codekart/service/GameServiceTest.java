package org.codekart.service;

import org.codekart.models.*;
import org.codekart.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private FiringStrategy firingStrategy;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService(firingStrategy);
    }

    @Test
    void testInitGame_ValidSize_ReturnsGameId() {
        String gameId = gameService.initGame(6);
        assertNotNull(gameId);
        assertFalse(gameId.isEmpty());
    }

    @Test
    void testInitGame_InvalidSize_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> gameService.initGame(5));
        assertThrows(IllegalArgumentException.class, () -> gameService.initGame(0));
        assertThrows(IllegalArgumentException.class, () -> gameService.initGame(-2));
    }

    @Test
    void testAddShip_ValidShip_ReturnsTrue() {
        gameService.initGame(6);
        boolean result = gameService.addShip("SH1", 2, 1, 5, 4, 4);
        assertTrue(result);
    }

    @Test
    void testAddShip_GameNotInitialized_ThrowsException() {
        assertThrows(IllegalStateException.class, () -> 
            gameService.addShip("SH1", 2, 1, 5, 4, 4));
    }

    @Test
    void testStartGame_ValidGame_StartsSuccessfully() {
        gameService.initGame(6);
        gameService.addShip("SH1", 2, 1, 5, 4, 4);
        assertDoesNotThrow(() -> gameService.startGame());
    }

    @Test
    void testStartGame_NoShips_ThrowsException() {
        gameService.initGame(6);
        assertThrows(IllegalStateException.class, () -> gameService.startGame());
    }

    @Test
    void testPlayTurn_ValidGame_PlaysSuccessfully() {
        gameService.initGame(6);
        gameService.addShip("SH1", 2, 1, 5, 4, 4);
        gameService.startGame();
        
        when(firingStrategy.getStrikeCoordinates(anyString(), anyInt()))
            .thenReturn(new Pair<>(3, 0));
        
        assertDoesNotThrow(() -> gameService.playTurn());
    }

    @Test
    void testPlayTurn_GameNotStarted_ThrowsException() {
        gameService.initGame(6);
        assertThrows(IllegalStateException.class, () -> gameService.playTurn());
    }

    @Test
    void testViewBattleField_ValidGame_DisplaysSuccessfully() {
        gameService.initGame(6);
        gameService.addShip("SH1", 2, 1, 5, 4, 4);
        assertDoesNotThrow(() -> gameService.viewBattleField());
    }

    @Test
    void testIsGameFinished_GameNotStarted_ReturnsFalse() {
        gameService.initGame(6);
        assertFalse(gameService.isGameFinished());
    }
} 