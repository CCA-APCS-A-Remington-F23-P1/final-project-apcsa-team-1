import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static java.awt.event.KeyEvent.VK_SPACE;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int FRAMERATE = 60;

    public static final int TOPBAR_HEIGHT = 150;
    public static final int TRACK_HEIGHT = 200;
    public static final int MAIN_HEIGHT = Game.HEIGHT - TOPBAR_HEIGHT - TRACK_HEIGHT;
    public static final int MAIN_WIDTH = Game.WIDTH;

    private final HashSet<Integer> pressed = new HashSet<>();
    private final ArrayList<Animal> animals = new ArrayList<>();
    private List<Integer> keys;
    private State state = State.STARTING;

    public Game() {
        // set the game board size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);

        for (var type : Animal.Type.values()) {
            var animal = new Animal(type);
            animal.pos(rand(MAIN_WIDTH), TOPBAR_HEIGHT + rand(MAIN_HEIGHT));
            animal.animationRate(400);
            animals.add(animal);
            add(animal);
            animal.setVisible(false);
        }

        // this timer will call the actionPerformed() method every DELAY ms
        Timer timer = new Timer(1000 / FRAMERATE, this);
        timer.start();
    }

    public static int rand(int range) {
        return (int) (Math.random() * range);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        keys = grabKeys();

        switch (state) {
            case STARTING -> startGame(frame);
            case RUNNING -> runGame(frame);
            case DEAD -> {
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void centeredText(Graphics frame, String text, Rectangle rect) {
        var metrics = frame.getFontMetrics(frame.getFont());
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        frame.drawString(text, x, y);
    }

    private void startGame(Graphics frame) {
        frame.setColor(Color.BLACK);
        frame.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        frame.setColor(Color.WHITE);

        setFont(new Font("Monospace", Font.PLAIN, 128));
        var bounds = new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT * 3 / 4);
        centeredText(frame, "Game Title", bounds);

        setFont(new Font("Monospace", Font.PLAIN, 64));
        var startBounds = new Rectangle(0, Game.HEIGHT / 2, Game.WIDTH, Game.HEIGHT / 2);
        centeredText(frame, "[ press space ]", startBounds);

        if (pressed(VK_SPACE)) {
            state = State.RUNNING;
            animals.stream().filter(animal -> animal.type.isPredator()).forEach(animal -> animal.setVisible(true));
        }
    }

    private void runGame(Graphics frame) {
        frame.setColor(Color.WHITE);
        frame.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        frame.setColor(Color.BLACK);
        frame.fillRect(0, 0, Game.WIDTH, Game.TOPBAR_HEIGHT);

        frame.setColor(Color.GREEN);
        frame.fillRect(0, Game.HEIGHT - TRACK_HEIGHT, Game.WIDTH, Game.TRACK_HEIGHT);

        animals.sort((a, b) -> Long.compare(b.hoveredTime, a.hoveredTime));
        IntStream.range(0, animals.size()).forEach(i -> setComponentZOrder(animals.get(i), i));
        animals.forEach(Animal::update);
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
    public void keyTyped(KeyEvent e) {
    }

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