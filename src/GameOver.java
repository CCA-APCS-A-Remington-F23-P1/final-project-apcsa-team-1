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
    
    reset.setActionCommand(BUTTON_ACTION_ONE);
    reset.addActionListener(this);
    reset.setText("Restart");
    reset.setFocusable(false);
    reset.setFont(Main.BUTTON_FONT);
    reset.setForeground(Color.WHITE);
    reset.setBackground(Color.RED);
    reset.setBounds(Main.WIDTH / 2, Main.HEIGHT / 2, textWidth(Main.BUTTON_FONT, reset.getText()) + 64, textHeight(Main.BUTTON_FONT, reset.getText()));
    add(reset);

    menu.setActionCommand(BUTTON_ACTION_TWO);
    menu.addActionListener(this);
    menu.setText("Main Menu");
    menu.setFocusable(false);
    menu.setFont(Main.BUTTON_FONT);
    menu.setForeground(Color.WHITE);
    menu.setBackground(Color.GREEN);
    menu.setBounds(Main.WIDTH / 2, Main.HEIGHT / 2 + 100, textWidth(Main.BUTTON_FONT, menu.getText()) + 64, textHeight(Main.BUTTON_FONT, menu.getText()));
    add(menu);

    JTextArea f = new JTextArea(16, 16);
    f.setText(" GAME OVER\n" + " Score: " + Main.score);
    f.setEditable(false);
    f.setBounds(0, 0, 200, 200);
    add(f);
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