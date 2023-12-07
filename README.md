# Project

## Building
To compile and run the project use:
`make run`

Alternatively use whatever system your IDE provides.

## Structure

### src
Source files. This is where all the java files should go.

#### Game.java
This is where the main rendering logic should be branched out from

#### Actor.java
This class takes an image path, width, height, and position.
All actors have the ability to move and collide with other actors.
This class should be inherited from to make player sprites, enemies, etc.

#### Direction.java
Defines a basic enum for the four cardinal directions UP, DOWN, LEFT, RIGHT.

### images
Image files for actors, game backgrounds, icons, etc.