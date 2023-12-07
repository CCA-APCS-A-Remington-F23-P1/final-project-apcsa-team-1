import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import static java.awt.event.KeyEvent.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 550;
    public static final int HEIGHT = 900;
    public static final int FRAMERATE = 30;

    private Actor teemo;
    private final HashSet<Integer> pressed = new HashSet<>();
    private List<Integer> keys;
    private State state = State.STARTING;

    public Game() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    System.out.println(e);
                    return false;
                }
            });

        // set the game board size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);
        // set the font
        var font = new Font("Monospace", Font.PLAIN, 16);
        setFont(font);

        teemo = new Actor()
                .frames("testing")
                .pos(WIDTH / 2, HEIGHT / 2)
                .scale(0.5);

        // this timer will call the actionPerformed() method every DELAY ms
        Timer timer = new Timer(1000 / FRAMERATE, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        keys = grabKeys();

        switch (state) {
            case STARTING -> {
                frame.setColor(Color.RED);
                frame.drawString("PRESS SPACE TO START", WIDTH / 8, HEIGHT / 2);
                if (pressed(VK_SPACE)) {
                    state = State.RUNNING;
                }
            }
            case RUNNING -> runGame(frame);
            case DEAD -> {}
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void runGame(Graphics frame) {
        frame.setColor(Color.WHITE);
        frame.drawString("USE THE ARROW KEYS TO MOVE", WIDTH / 8, HEIGHT / 2);

        for (var key : keys) {
            switch (key) {
                case VK_UP -> teemo.translate(Direction.UP, 10);
                case VK_DOWN -> teemo.translate(Direction.DOWN, 10);
                case VK_LEFT -> teemo.translate(Direction.LEFT, 10);
                case VK_RIGHT -> teemo.translate(Direction.RIGHT, 10);
            }
        }

        teemo.draw(frame);
        teemo.update();
    }

    private boolean pressed(int key) {
        return keys.contains(key);
    }

    private synchronized ArrayList<Integer> grabKeys() {
        return new ArrayList<>(pressed);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        pressed.add(key);
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressed.remove(key);
    }
}