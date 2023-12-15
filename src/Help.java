import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Help extends Scene {
  private static final String BUTTON_ACTION = "i was pressed";
  
  public Help(){
    super();
    setOpaque(false);
    JButton button = new JButton("Help");
    button.setActionCommand(BUTTON_ACTION);
    button.addActionListener(this);
    button.setFocusable(false);

    button.setBounds(Main.WIDTH / 2 - 50, Main.HEIGHT / 2 - 50, 100, 50);
    add(button);
  }

  @Override
  public void paintComponent(Graphics frame) {
    super.paintComponent(frame);

    frame.drawString("Rules: ", 50, 50);
    frame.drawString("You have to stop the prey before it reaches the treasure. In order to do this you need to select the appropriate predator in time.", 50, 60);
    frame.drawString("List of predators and their corresponding prey:", 50, 60);
    frame.drawString("Eagle: Duck, Parrot, Toucan", 50, 70);
    frame.drawString("Frog: Caterpillar, Ladybug, Grasshopper", 50, 80);
    frame.drawString("Shark: Pufferfish, Clownfish, Octopus", 50, 90);
    frame.drawString("Wolf: Pig, Sheep, Cow", 50, 100);
    frame.drawString("Tiger: Deer, Reindeer, Moose", 50, 110);
    frame.drawString("Snake: Mouse, Rat, Squirrel", 50, 120);
    frame.drawString("Lion: Horse, Donkey, Zebra", 50, 130);
    frame.drawString("T-Rex: Triceratops, Pterodactyl, Brachiosaurus", 50, 140);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    var action = e.getActionCommand();
    if (action != null) switch(action) {
      case BUTTON_ACTION: {
          System.out.println("I was pressed");
          break;
      }
    }
    super.actionPerformed(e);
    
  }
}