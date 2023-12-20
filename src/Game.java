import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.awt.event.KeyEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_SLASH;

public class Game extends Scene {
    private final static BufferedImage BACKGROUND = Main.loadImage(Paths.get("images", "forest-background.png"));
    private final Actor treasure = new Actor().frames(Paths.get("treasurechest.png"));
    private final ArrayList<Animal> animals = new ArrayList<>();
    private long levelMillis;
    private final double levelSeconds;
    public Animal prey;
    public int round = 0;
    public long totalElapsed = 0;
    private int finishLine;
    private long startTime = System.currentTimeMillis();
    private long elapsed;

    public Game() {
        levelSeconds = Main.DEFAULT_GAME_SECONDS;
        levelMillis = (long) (levelSeconds * 1000);

        // set the game board size
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);
        setLayout(null);
        setBounds(0, 0, Main.WIDTH, Main.HEIGHT);

        reset(true);

        var key = KeyStroke.getKeyStroke(VK_SLASH, SHIFT_DOWN_MASK, false);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(key, key.hashCode());
        getActionMap().put(key.hashCode(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isActive()) {
                    Main.push(new Help(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            activate();
                        }
                    });
                    deactivate();
                }
            }
        });
    }

    public static int rand(int range) {
        return (int) (Math.random() * (double) range);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        animals.forEach(Animal::deactivate);
        prey.deactivate();
    }

    @Override
    public void activate() {
        super.activate();
        animals.forEach(Animal::activate);
        if (prey != null) prey.activate();
        startTime = System.currentTimeMillis();
    }

    public Animal getPrey() {
        return prey;
    }

    public long getLevelMillis() {
        return levelMillis;
    }

    public long getElapsed() {
        return elapsed;
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawImage(BACKGROUND, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    }

    public void nextRound() {
        round += 1;

        boolean speedUp = Math.random() >= 0.5;
        boolean shuffle = Math.random() >= 0.7;
        if (speedUp) {
            levelMillis = Math.max(1500, levelMillis - 500);
        }

        reset(shuffle);
    }

    public void update() {
        if (isActive()) {
            long now = System.currentTimeMillis();
            long deltaElapsed = now - startTime;
            elapsed += deltaElapsed;
            totalElapsed += deltaElapsed;
            double percent = (double) elapsed / levelMillis;
            int preypos = Math.min(finishLine, (int) ((double) finishLine * percent));
            startTime = now;

            prey.pos(preypos, prey.y);

            for (var predator : animals) {
                if (predator.pressed && predator.type.eats(prey.type)) {
                    Main.push(new NextLevel(), false);
                    deactivate();
                    return;
                }
            }

            if (prey.isColliding(treasure)) {
                try {
                    FileWriter fw = new FileWriter("scores.csv", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(new Score(round, totalElapsed, System.currentTimeMillis() / 1000L).serialize());
                    bw.newLine();
                    bw.close();
                } catch (IOException e) {
                }
                Main.push(new GameOver(), false);
                deactivate();
                return;
            }

            animals.sort((a, b) -> Long.compare(b.hoveredTime, a.hoveredTime));
            IntStream.range(0, animals.size()).forEach(i -> setComponentZOrder(animals.get(i), i));
            animals.forEach(Animal::update);

            prey.update();
            treasure.update();
        }
    }

    public void reset(boolean shuffle) {
        activate();

        for (var component : getComponents()) {
            if (!animals.contains(component)) {
                remove(component);
            }
        }

        startTime = System.currentTimeMillis();
        elapsed = 0;

        if (animals.isEmpty()) {
            for (var type : Animal.Type.values()) {
                if (type.isPrey()) continue;

                var animal = new Animal(type);
                animal.animationRate(400);
                animal.setVisible(false);
                animals.add(animal);
                add(animal);
            }
        }

        animals.forEach(animal -> {
            if (animal.height > 150) {
                animal.scale(150.0 / animal.height);
            }
        });

        if (shuffle) {
            animals.forEach(animal -> animal.pos(rand(Main.MAIN_WIDTH), rand(Main.MAIN_HEIGHT - animal.height)));
        }

        animals.forEach(animal -> animal.setVisible(true));

        var preyList = Arrays.stream(Animal.Type.values()).filter(Animal.Type::isPrey).toList();
        prey = new Animal(preyList.get((int) (Math.random() * preyList.size())));
        prey.draggable(false);
        prey.hoverable(false);
        if (prey.height > Main.TRACK_HEIGHT) {
            prey.scale(200.0 / prey.height);
        }
        prey.pos(0, Main.HEIGHT - Main.TRACK_HEIGHT / 2 - prey.height / 2);
        add(prey);

        if (treasure.height > Main.TRACK_HEIGHT) {
            treasure.scale(200.0 / treasure.height);
        }
        treasure.pos(Main.WIDTH - treasure.width, Main.HEIGHT - treasure.height);
        add(treasure);
        finishLine = treasure.x + treasure.width / 2 - prey.width;
    }
}