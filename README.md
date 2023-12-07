# Project

## Building
To compile the project use:
`make`

To compile and run the project use:
`make run`

## Structure

### Game
This is where the main rendering logic should be branched out from

### Actor
This class takes an image path, width, height, and position.
All actors have the ability to move and collide with other actors.
This class should be inherited from to make player sprites, enemies, etc.