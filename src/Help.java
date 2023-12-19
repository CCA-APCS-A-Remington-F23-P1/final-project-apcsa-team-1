import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Help extends Scene {
  private static final String BUTTON_ACTION = "i was pressed";
  private Animal prey;

  public Help(){
    super();
    ImageIcon icon = new ImageIcon("images/helpButton.png");
    Image image = icon.getImage();
    Image newing = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
    icon = new ImageIcon(newing);

//    JButton button = new JButton("Help");
//    button.setActionCommand(BUTTON_ACTION);
//    button.addActionListener(this);
//    button.setText("Help");
//    button.setFocusable(false);
//    button.setIcon(icon);
//    button.setFont(new Font("Comic Sans", Font.BOLD, 25));
//    button.setForeground(Color.white);
//    button.setBackground(Color.blue);
//    button.setBorder(BorderFactory.createRaisedBevelBorder());
//    button.setBounds(Main.WIDTH / 2 - 50, Main.HEIGHT / 2 - 50, 128, 64);
//    add(button);

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
      frame.drawImage(prey.images[0], 50, 500, prey.width, prey.height, null);
    }
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
  }
}