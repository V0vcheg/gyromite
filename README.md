# Gyromite Project README

## Project Overview

### Game Principle
You control Professor Hector in a 2D side-scrolling laboratory. The laboratory consists of multiple levels. Professor Hector can move left, right, climb up, or down using ropes. Some levels have pillars/columns that can be raised or lowered when a specific key is pressed. Your goal is to collect all the bombs while avoiding enemies called Smicks. To achieve this, you may need to manipulate the pillars/columns to create pathways.

## Project Features
We divided the project into four main features:

- **Ropes**: Allow Professor Hector to move vertically.
- **Interface**: Display all entities within the level.
- **Pillars/Columns**: Enable the use of the 'space' key to raise/lower pillars.
- **Collisions**:
   - Hero <> Enemy: If the hero encounters an enemy, the hero loses; if the hero falls on an enemy, the enemy is removed from the level.
   - Hero, Enemy <> Column: If a column crushes the hero, the game is lost; if a column crushes an enemy, the enemy is removed from the level.
- **Enemies**: Implement enemy movement from left to right without the ability to climb ropes.

The most time-consuming feature to develop was the implementation of the pillar/column system. However, dividing tasks allowed us to work on features other than pillars. The last feature implemented was collision handling between different entities.

## Project Details

### Ropes
Ropes are static entities that enable vertical movement for the player using the `canClimbDescend()` function in the player's movement controller (`DirectionController`). To handle this functionality, we created the `initializeRopes()` function, called within the `game.update()` function.

### Pillars/Columns
All columns are separate entities defined by five attributes:
- PosX (int): X-axis position of the entity.
- PosY (int): Y-axis position of the entity.
- Size (int): overall size of the column.
- Part (int): position of the entity within the column (top/middle/bottom).
- Orientation (int): current orientation of the column (up/down).

Columns use a similar control system to the hero, but the 'space' key is used to activate them. Each time the 'space' key is pressed, the entity moves along the Y-axis by adding or subtracting the column's size from the current position. Collision detection is performed when an entity of type Column moves, checking if the target position is occupied by another entity:

- If it's the hero, the game triggers a game over.
- If it's a bot, the bot entity is removed from the board.

### Bombs and Timer
Bombs are the victory conditions for each level. The hero must collect all the bombs before the timer runs out or before being eliminated by a Smick. We implemented a variable to keep track of the number of bombs in the level. Whenever the hero collects a bomb, the bomb counter decreases by 1. When it reaches 0, the game is considered over.

The timer decreases with each hero movement, and there are two scenarios:

- If the timer reaches 0 and not all bombs have been collected, the game ends in a loss.
- If the timer is stopped before reaching 0, the player wins, indicating that all bombs have been collected.

### Enemies
Enemies are obstacles that can lead to the player's loss. They move randomly within the level. If an enemy collides head-on with the player or vice versa, the game is lost. The player can jump on an enemy's head to eliminate it and increase their chances of winning.

This functionality includes two parts:

- Enemy movement controller.
- Collision detection between the player and enemies.

For enemy movement, we use a 'timer' variable to determine their direction. The enemy controller uses a similar principle to the player's controller, but instead of listening to keyboard input, it relies on the timer's value.

The `deleteEntity()` function was created for collision handling, removing entities from the HashMap and scheduler as needed. These deletions are crucial for level functionality.

## Unfinished/Unimplemented Features
Due to delays in developing the functions related to columns and their movements, we couldn't implement certain features:

- **Score**: We considered implementing a scoring system where the player's score increases for each bomb or enemy eliminated. The player's score would also be influenced by the time taken to complete the level, multiplied by the scores for bombs and enemies.
- **Additional Platforms**: We thought about adding platforms similar to columns with horizontal movement.

