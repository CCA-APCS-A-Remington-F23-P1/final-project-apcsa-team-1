import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.awt.event.KeyEvent.*;

public class Game extends Scene {
    private Animal prey;
    private final Actor treasure = new Actor().frames(Paths.get("treasurechest.png"));
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final static BufferedImage BACKGROUND = Main.loadImage(Paths.get("images", "forest-background.png"));
    private int levelSeconds = 1;
    private int finishLine;
    private long startTime = System.currentTimeMillis();

    public Game() {
        // set the game board size
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);
        setLayout(null);
        setBounds(0, 0, Main.WIDTH, Main.HEIGHT);

        for (var type : Animal.Type.values()) {
            if (type.isPrey()) continue;

            var animal = new Animal(type);
            animal.pos(rand(Main.MAIN_WIDTH), rand(Main.MAIN_HEIGHT - 300));
            animal.animationRate(400);
            animal.setVisible(false);
            animals.add(animal);
            add(animal);
        }

        animals.forEach(animal -> animal.setVisible(true));

        var preyList = Arrays.stream(Animal.Type.values()).filter(Animal.Type::isPrey).toList();
        prey = new Animal(preyList.get((int)(Math.random() * preyList.size())));
        if (prey.height > Main.TRACK_HEIGHT) {
            prey.scale(200.0 / prey.height);
        }
        prey.pos(0, Main.HEIGHT - prey.height);
        add(prey);

        if (treasure.height > Main.TRACK_HEIGHT) {
            treasure.scale(200.0 / treasure.height);
        }
        treasure.pos(Main.WIDTH - treasure.width, Main.HEIGHT - treasure.height);
        add(treasure);
        finishLine = treasure.x + treasure.width / 2 - prey.width;
//
//        var key = KeyStroke.getKeyStroke(VK_ESCAPE, 0, false);
//        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(key, key.hashCode());
//        getActionMap().put(key.hashCode(), new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("pressed");
//                if (isActive()) {
//                    Main.push(new ExampleScene());
//                    deactivate();
//                } else {
//                    Main.pop();
//                    activate();
//                }
//            }
//        });
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

    public Animal getPrey() {
      return prey;
    }

    public static void centeredText(Graphics frame, String text, Rectangle rect) {
        var metrics = frame.getFontMetrics(frame.getFont());
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        frame.drawString(text, x, y);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawImage(BACKGROUND, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    }

    public void update() {
        if (isActive()) {
            long now = System.currentTimeMillis();
            long elapsed = now - startTime;
            double percent = (double) elapsed / (levelSeconds * 1000.0);
            int preypos = Math.min(finishLine, (int)((double) finishLine * percent));

            prey.pos(preypos, prey.y);

            for (var predator : animals) {
                if (predator.pressed && predator.type.eats(prey.type)) {
                    Main.push(new NextLevel());
                }
            }

            if (prey.isColliding(treasure)) {
                Main.push(new GameOver());
            }

            animals.sort((a, b) -> Long.compare(b.hoveredTime, a.hoveredTime));
            IntStream.range(0, animals.size()).forEach(i -> setComponentZOrder(animals.get(i), i));
            animals.forEach(Animal::update);

            prey.update();
            treasure.update();
        }
    }
}