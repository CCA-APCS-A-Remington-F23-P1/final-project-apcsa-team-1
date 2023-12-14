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
import static java.awt.event.KeyEvent.*;

public class Game extends Scene {
    private final ArrayList<Animal> animals = new ArrayList<>();

    public Game() {
        // set the game board size
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);
        setLayout(null);
        setBounds(0, 0, Main.WIDTH, Main.HEIGHT);

        for (var type : Animal.Type.values()) {
            var animal = new Animal(type);
            animal.pos(rand(Main.MAIN_WIDTH), Main.TOPBAR_HEIGHT + rand(Main.MAIN_HEIGHT));
            animal.animationRate(400);
            animal.setVisible(false);
            animals.add(animal);
            add(animal);
        }

        animals.stream().filter(animal -> animal.type.isPredator()).forEach(animal -> animal.setVisible(true));

        var key = KeyStroke.getKeyStroke(VK_ESCAPE, 0, false);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(key, key.hashCode());
        getActionMap().put(key.hashCode(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pressed");
                if (isActive()) {
                    Main.push(new ExampleScene());
                    deactivate();
                } else {
                    Main.pop();
                    activate();
                }
            }
        });
    }

    @Override
    public void deactivate() {
        super.deactivate();
        animals.forEach(Animal::deactivate);
    }

    @Override
    public void activate() {
        super.activate();
        animals.forEach(Animal::activate);
    }

    public static int rand(int range) {
        return (int) (Math.random() * range);
    }

    private void centeredText(Graphics frame, String text, Rectangle rect) {
        var metrics = frame.getFontMetrics(frame.getFont());
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        frame.drawString(text, x, y);
    }

    private void startGame(Graphics frame) {
        frame.setColor(Color.WHITE);

        setFont(new Font("Monospace", Font.PLAIN, 128));
        var bounds = new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT * 3 / 4);
        centeredText(frame, "Game Title", bounds);

        setFont(new Font("Monospace", Font.PLAIN, 64));
        var startBounds = new Rectangle(0, Main.HEIGHT / 2, Main.WIDTH, Main.HEIGHT / 2);
        centeredText(frame, "[ press space ]", startBounds);
    }

    @Override
    public void paintComponent(Graphics frame) {
        frame.setColor(Color.WHITE);
        frame.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        frame.setColor(Color.BLACK);
        frame.fillRect(0, 0, Main.WIDTH, Main.TOPBAR_HEIGHT);

        frame.setColor(Color.GREEN);
        frame.fillRect(0, Main.HEIGHT - Main.TRACK_HEIGHT, Main.WIDTH, Main.TRACK_HEIGHT);
    }

    public void update() {
        if (isActive()) {
            animals.sort((a, b) -> Long.compare(b.hoveredTime, a.hoveredTime));
            IntStream.range(0, animals.size()).forEach(i -> setComponentZOrder(animals.get(i), i));
            animals.forEach(Animal::update);
        }
    }
}