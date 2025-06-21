# BattleGame

A production-quality implementation of the classic Battleship game following SOLID principles and modern design patterns.

## Features

- **Modular Architecture**: Clean separation of concerns with well-defined interfaces
- **Strategy Pattern**: Pluggable firing strategies (Random, Systematic)
- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **Production Quality**: Comprehensive error handling, validation, and unit tests
- **Extensible Design**: Easy to add new features and strategies

## Architecture Overview

### Core Components

```
src/main/java/org/codekart/
├── models/           # Domain models
│   ├── Game.java     # Game orchestration
│   ├── Board.java    # Game board management
│   ├── Player.java   # Player entity
│   ├── BattleShip.java # Ship entity
│   ├── Cell.java     # Individual cell
│   └── GameState.java # Game state enum
├── service/          # Business logic
│   ├── GameService.java # Main game service
│   ├── FiringStrategy.java # Strategy interface
│   ├── RandomFiringStrategy.java # Random strategy
│   └── SystematicFiringStrategy.java # Systematic strategy
└── util/             # Utilities
    └── Pair.java     # Generic pair utility
```

### Design Patterns Used

1. **Strategy Pattern**: Different firing strategies can be plugged in
2. **State Pattern**: Game state management (INITIALIZED, IN_PROGRESS, FINISHED)
3. **Factory Pattern**: Ship creation and placement
4. **Observer Pattern**: Game state notifications (extensible)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Game

```bash
# Clone the repository
git clone <repository-url>
cd BattleGame

# Build the project
mvn clean compile

# Run the game
mvn exec:java -Dexec.mainClass="org.codekart.Main"
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=GameServiceTest
```

## API Usage

### Basic Game Flow

```java
// Initialize game service with firing strategy
GameService gameService = new GameService(new RandomFiringStrategy());

// Initialize game with 6x6 board
String gameId = gameService.initGame(6);

// Add ships for both players
gameService.addShip("SH1", 2, 1, 5, 4, 4);

// View battlefield
gameService.viewBattleField();

// Start and play the full game
gameService.playFullGame();
```

### Using Different Strategies

```java
// Random firing strategy
GameService randomGame = new GameService(new RandomFiringStrategy());

// Systematic firing strategy
GameService systematicGame = new GameService(new SystematicFiringStrategy());
```

## Game Rules

1. **Board Division**: NxN board divided equally between two players
2. **Ship Placement**: Ships are square-shaped and placed in respective territories
3. **Turn-based Play**: Players take turns firing missiles
4. **Random Targeting**: Missiles hit random coordinates in opponent's territory
5. **No Duplicate Hits**: Same coordinate cannot be hit twice
6. **Victory Condition**: Player loses when all their ships are destroyed

## Code Quality Improvements Made

### 1. SOLID Principles Implementation

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: New strategies can be added without modifying existing code
- **Liskov Substitution**: All strategy implementations are interchangeable
- **Interface Segregation**: Clean interfaces with focused responsibilities
- **Dependency Inversion**: High-level modules depend on abstractions

### 2. Design Patterns

- **Strategy Pattern**: Firing strategies are pluggable
- **State Pattern**: Game state management
- **Factory Pattern**: Ship creation
- **Builder Pattern**: Complex object construction (extensible)

### 3. Production Quality Features

- **Input Validation**: Comprehensive parameter validation
- **Error Handling**: Proper exception handling with meaningful messages
- **Unit Tests**: Comprehensive test coverage
- **Documentation**: Clear code documentation and README
- **Logging**: Structured logging (extensible)
- **Configuration**: Externalized configuration (extensible)

### 4. Code Organization

- **Package Structure**: Logical separation of concerns
- **Naming Conventions**: Consistent Java naming conventions
- **Code Comments**: Clear and meaningful comments
- **Lombok**: Reduced boilerplate code

## Extensibility

The codebase is designed for easy extension:

### Adding New Firing Strategies

```java
public class SmartFiringStrategy implements FiringStrategy {
    @Override
    public Pair<Integer, Integer> getStrikeCoordinates(String playerId, int boardSize) {
        // Implement smart targeting logic
        return new Pair<>(x, y);
    }
    
    @Override
    public String getStrategyName() {
        return "Smart Firing Strategy";
    }
}
```

### Adding New Game Features

- **Multiple Players**: Extend Player management
- **Different Ship Types**: Implement Ship hierarchy
- **Power-ups**: Add PowerUp interface and implementations
- **AI Players**: Implement AI strategy interfaces

## Testing Strategy

- **Unit Tests**: Individual component testing
- **Integration Tests**: Component interaction testing
- **Mock Testing**: External dependency mocking
- **Edge Case Testing**: Boundary condition testing

## Performance Considerations

- **Memory Efficiency**: Proper object lifecycle management
- **Algorithm Optimization**: Efficient coordinate generation
- **Scalability**: Support for larger board sizes
- **Concurrency**: Thread-safe implementations (extensible)

## Future Enhancements

1. **Web Interface**: REST API with Spring Boot
2. **Database Integration**: Persistent game state
3. **Real-time Multiplayer**: WebSocket support
4. **AI Players**: Machine learning-based strategies
5. **Tournament Mode**: Multiple game support
6. **Analytics**: Game statistics and analysis

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

/*

BattleShip Game
Design and implement a battleship game to be played between two players until one comes out as the winner.

Requirements:

● The game will be played in a square area of the sea with NxN grids which will be called
a battlefield. "N" should be taken as input in your code.

● The battlefield will be divided in half between both the players. So in a NxN battlefield,
NxN/2 grids will belong to PlayerA and the other NxN/2 grids will belong to playerB

● The size and location of each ship will be taken as input. Each ship will be assumed to
be of Square shape. Both the players should be assigned equal fleet.

● ● The location of each ship in the NxN grids has to be taken as input (X, Y). X and Y
should be integers. For eg. if a ship "SH1" is at (2, 2) and has the size of 4, its corners
will be at (0, 0), (0, 4), (4, 0) and (4,4)

● Ships will remain stationary. No two ships should overlap with each other. However they can touch boundaries with each other.

● Each player will fire one missile towards the enemy field during his turn using the "random coordinate fire" strategy, which means the missile will hit at a random coordinate of the opponent's field. It might hit or miss the opponent ship. In either case the turn is then transferred to the other player.
○ In case of a hit, the opponent's ship is destroyed.
○ In case of a miss, nothing happens.

● No two missiles should ever be fired at the same coordinates throughout the course of the game.

● When all the ships of a particular player has been destroyed, he loses the game




The following APIs have to be implemented:

● initGame(N)

This will initialize the game with a battlefield of size NxN. Where the left half of
N/2xN will be assigned to PlayerA and the right half will be assigned to PlayerB


● addShip(id, size, x position PlayerA, y position PlayerA, x position PlayerB, y position
PlayerB)
This will add a ship of given size at the given coordinates in both the player's
fleet.



● startGame()

This will begin the game, where PlayerA will always take the first turn. The output
of each step should be printed clearly in the console.
For eg.
PlayerA's turn: Missile fired at (2, 4). "Hit". PlayerB's ship with id "SH1"
destroyed.
PlayerB's turn: Missile fired at (6, 1). "Miss"


● hitShip(playerChance, int x, int y)

playerChance is the player id whose chance to fire the missile
x and y are the coordinates of the location of the fired missile

Sample Ex:

>> initGame(6)
>> addShip("SH1", size = 2, 1, 5, 4, 4)
>> viewBattleField()

A-sh1- left bottom 0,4, size =2.    -> right top = 2,6
B-sh1- left bottom 3,3, size= 2.  -> right top = 5, 5

>> startGame()
PlayerA's turn: Missile fired at (3, 0) : "Miss" : Ships
Remaining - PlayerA:1, PlayerB:1
PlayerB's turn: Missile fired at (1, 1) : "Miss" : Ships
Remaining - PlayerA:1, PlayerB:1
PlayerA's turn: Missile fired at (5, 3) : "Hit" B-SH2 destroyed
: Ships Remaining - PlayerA:1, PlayerB:0
GameOver. PlayerA wins.


*/

// x - [0 to N] 
    // player A - [0 to N/2]
    // player B - [N/2 to N]


(4,4)  -- ship was present at 4,4
    - isHit - false --> true
    - isShipDestryed - false -> true

(4,3) -- empty land at B side
    - isHit - false --> true
    - isShipDestoyed - 
    
    
    