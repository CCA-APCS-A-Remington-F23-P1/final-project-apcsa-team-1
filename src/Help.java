import java.awt.*;
import java.awt.event.ActionEvent;

public class Help extends Scene {
    private static final String BUTTON_ACTION = "i was pressed";
    private Animal prey;

    public Help() {
        super();

        if (Main.current() instanceof Game) {
            prey = ((Game) Main.current()).prey;
        }
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawString("Rules: ", 50, 50);
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

        if (prey != null) {
            frame.drawImage(prey.images[prey.animationFrame], 50, 500, prey.width, prey.height, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case BUTTON_ACTION: {
                System.out.println("I was pressed");
                break;
            }
        }
    }
}