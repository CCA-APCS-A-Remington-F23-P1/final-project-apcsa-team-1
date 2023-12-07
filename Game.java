import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 900;
    public static final int FRAMERATE = 30;

    public Game() {
        // set the game board size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);

        // this timer will call the actionPerformed() method every DELAY ms
        Timer timer = new Timer(1000 / FRAMERATE, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        var font = new Font("Monospace", Font.PLAIN, 64);
        frame.setFont(font);

        frame.setColor(Color.WHITE);
        frame.drawString("TESTING", WIDTH / 8, HEIGHT / 2);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
