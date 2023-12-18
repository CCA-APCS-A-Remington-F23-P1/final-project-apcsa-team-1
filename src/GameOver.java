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
  JButton menu = new JButton("Back to Main Menu");
  JTextArea text = new JTextArea(20, 64);
  
  public GameOver(){
    super();

    setOpaque(false);
    
    reset.setActionCommand(BUTTON_ACTION_ONE);
    reset.addActionListener(this);
    reset.setText("Help");
    reset.setFocusable(false);
    reset.setFont(new Font("Monospace", Font.PLAIN, 128));
    reset.setForeground(Color.WHITE);
    reset.setBackground(Color.RED);
    reset.setBounds(Main.WIDTH / 2, Main.HEIGHT / 2, 100, 50);
    add(reset);

    menu.setActionCommand(BUTTON_ACTION_TWO);
    menu.addActionListener(this);
    menu.setText("Back to Main Menu");
    menu.setFocusable(false);
    menu.setFont(new Font("Monospace", Font.PLAIN, 128));
    menu.setForeground(Color.WHITE);
    menu.setBackground(Color.GREEN);
    menu.setBounds(Main.WIDTH / 2, Main.HEIGHT / 2 + 100, 100, 50);
    add(menu);

    text.setEditable(false);
    text.setText("HELLO THERE");
    text.setBounds(0, 0, 500, 500);
    add(text);
  }
  
  @Override
  public void paintComponent(Graphics frame) {
      super.paintComponent(frame);
//      text.setText("Game Over");
//      text.append("Score: " + Main.score);
//    frame.setColor(Color.BLACK);
//    Game.centeredText(frame, "Game Over", new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT));
//    frame.drawString("Score: " + Main.score, Main.WIDTH / 2, Main.HEIGHT / 2 - 50);
  }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case BUTTON_ACTION_ONE:
                Main.set(new Game());
                break;
            case BUTTON_ACTION_TWO:
                Main.set(new GameIntroScene(null));
                break;
        }
    }
}