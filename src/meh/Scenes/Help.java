package meh.Scenes;

import meh.*;
import java.awt.*;

public class Help extends Scene {
    private static final String BUTTON_ACTION = "i was pressed";
    private Animal prey;
    private boolean gameRunning = false;

    public Help() {
        super();

        if (Main.current() instanceof Game) {
            gameRunning = true;
            prey = ((Game) Main.current()).prey;
        }
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawString("Rules (Press the ? on your keyboard to access this in game): ", 50, 50);
        frame.drawString("You have to stop the prey before it reaches the treasure. In order to do this you need to select the appropriate predator in time.", 50, 80);
        frame.drawString("List of predators and their corresponding prey:", 50, 110);
        frame.drawString("Eagle: Duck, Parrot, Toucan", 50, 140);
        frame.drawString("Frog: Caterpillar, Ladybug, Grasshopper", 50, 170);
        frame.drawString("Shark: Pufferfish, Clownfish, Octopus", 50, 200);
        frame.drawString("Wolf: Pig, Sheep, Cow", 50, 230);
        frame.drawString("Tiger: Deer, Reindeer, Moose", 50, 260);
        frame.drawString("Snake: Mouse, Rat, Squirrel", 50, 290);
        frame.drawString("Lion: Horse, Donkey, Zebra", 50, 320);
        frame.drawString("T-Rex: Triceratops, Pterodactyl, Brachiosaurus", 50, 350);
        if (gameRunning) {
            String str = "You are " + percent(frame) + "% through the round.";
            frame.drawString(str, 50, 400);
            frame.drawImage(prey.images[prey.animationFrame], 50, 500, prey.width, prey.height, null);
        }
    }

    public static int percent(Graphics frame) {
        var game = (Game) Main.current();
        double totalTime = (double) game.getLevelMillis();
        double currentTime = (double) game.getElapsed();
        int percent = (int) ((currentTime / totalTime) * 100.0);
        if (percent >= 75) {
          frame.setColor(Color.RED);
        }
        else if (percent >= 50) {
          frame.setColor(Color.ORANGE);
        }
        else if (percent >= 25) {
          frame.setColor(Color.YELLOW);
        }
        else if (percent >= 0) {
          frame.setColor(Color.GREEN);
        }
    }
}