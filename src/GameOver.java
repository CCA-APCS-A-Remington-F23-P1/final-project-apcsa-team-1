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

  private int textWidth(Font f, String msg) {
      var metrics = getFontMetrics(f);
      return metrics.stringWidth(msg);
  }

  public int textHeight(Font f, String msg) {
      var metrics = getFontMetrics(f);
      return metrics.getHeight();
  }
  
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

    text.setEditable(false);
    text.setText("HELLO THERE");
    text.setBounds(0, 0, 500, 500);
    add(text);
  }
  
  @Override
  public void paintComponent(Graphics frame) {
        super.paintComponent(frame);
        JFrame window = new JFrame("Testing, testing...");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(400, 400));
        window.setLayout(null);

        JTextArea f = new JTextArea(16, 16);
        f.setText(" GAME OVER\n" + " Score: " + Main.score);
        f.setEditable(false);
        f.setBounds(0, 0, 200, 200);
        window.add(f);

        window.setResizable(false);

        window.pack();

        window.setLocationRelativeTo(null);
        
        window.setVisible(true);
        window.createBufferStrategy(2);
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