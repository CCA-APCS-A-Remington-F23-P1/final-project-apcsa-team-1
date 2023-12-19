import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GameOver extends Scene {
  // private final BufferedImage backgroundImage;
  private static final String BUTTON_ACTION_ONE = "i was pressed";
  private static final String BUTTON_ACTION_TWO = "i was pressed as well";
    
  JButton reset = new JButton("Restart");
  JButton menu = new JButton("Main Menu");
  JTextArea text = new JTextArea(20, 64);
  
  public GameOver(){
    super();

    setOpaque(false);

    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    var game = (Game)Main.current();

    JTextArea f = new JTextArea(16, 16);
    f.setFont(new Font("Monospace", Font.PLAIN, 32));
    f.setBackground(Color.GREEN);
    f.setText(" GAME OVER\n" + " Score: " + game.round);
    f.setEditable(false);
    f.setAlignmentX(CENTER_ALIGNMENT);
    add(f, gbc);
    
    reset.setActionCommand(BUTTON_ACTION_ONE);
    reset.addActionListener(this);
    reset.setText("Restart");
    reset.setFocusable(false);
    reset.setFont(Main.BUTTON_FONT);
    reset.setBackground(Color.RED);
    add(reset, gbc);

    menu.setActionCommand(BUTTON_ACTION_TWO);
    menu.addActionListener(this);
    menu.setText("Main Menu");
    menu.setFocusable(false);
    menu.setFont(Main.BUTTON_FONT);
    menu.setBackground(Color.GREEN);
    add(menu, gbc);
  }
  
  @Override
  public void paintComponent(Graphics frame) {
    super.paintComponent(frame);
  }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case BUTTON_ACTION_ONE:
                Main.set(new Game());
                Main.pop();
                break;
            case BUTTON_ACTION_TWO:
                Main.set(new GameIntroScene());
                Main.pop();
                break;
        }
    }
}