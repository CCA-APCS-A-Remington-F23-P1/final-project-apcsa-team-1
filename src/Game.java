import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private final Actor treasure = new Actor().frames(Paths.get("treasurechest.png"));
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final static BufferedImage BACKGROUND = Main.loadImage(Paths.get("images", "forest-background.png"));
    private int finishLine;
    private long startTime = System.currentTimeMillis();
    private long elapsed;
    private long levelMillis;
    private double levelSeconds;

    public Animal prey;
    public int round = 0;

    public Game() {
        levelSeconds = Main.DEFAULT_GAME_SECONDS;
        levelMillis = (long)(levelSeconds * 1000);

        // set the game board size
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        // set the game board background color
        setBackground(Color.BLACK);
        setLayout(null);
        setBounds(0, 0, Main.WIDTH, Main.HEIGHT);

        reset();

//        var helpButton = new JButton("Help");
//        helpButton.setBackground(Color.GREEN);
//        helpButton.setFont(new Font("Monospace", Font.PLAIN, 32));
//        helpButton.setFocusable(false);
//        helpButton.setBounds(0, 0, textWidth(helpButton.getFont(), helpButton.getText()) + 64, textHeight(helpButton.getFont(), helpButton.getText()));
//        add(helpButton);

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

    public static int rand(int range) {
        return (int) (Math.random() * (double)range);
    }

    public Animal getPrey() {
        return prey;
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawImage(BACKGROUND, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    }

    public void nextRound() {
        round += 1;
        reset();
    }

    public void update() {
        if (isActive()) {
            long now = System.currentTimeMillis();
            long deltaElapsed = now - startTime;
            elapsed += deltaElapsed;
            double percent = (double) elapsed / levelMillis;
            int preypos = Math.min(finishLine, (int)((double) finishLine * percent));
            startTime = now;

            prey.pos(preypos, prey.y);

            for (var predator : animals) {
                if (predator.pressed && predator.type.eats(prey.type)) {
                    Main.push(new NextLevel());
                    deactivate();
                    return;
                }
            }

            if (prey.isColliding(treasure)) {
                try {
                    FileWriter fw = new FileWriter("scores.csv", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(new Score(round, 0, System.currentTimeMillis() / 1000L).serialize());
                    bw.newLine();
                    bw.close();
                } catch (IOException e) {}
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

    public void reset() {
        activate();

        for (var component : getComponents()) {
            remove(component);
        }
        startTime = System.currentTimeMillis();
        animals.clear();
        elapsed = 0;

        for (var type : Animal.Type.values()) {
            if (type.isPrey()) continue;

            var animal = new Animal(type);
            if (animal.height > 150) {
                animal.scale(150.0 / animal.height);
            }
            animal.pos(rand(Main.MAIN_WIDTH), rand(Main.MAIN_HEIGHT - animal.height));
            animal.animationRate(400);
            animal.setVisible(false);
            animals.add(animal);
            add(animal);
        }

        animals.forEach(animal -> animal.setVisible(true));

        var preyList = Arrays.stream(Animal.Type.values()).filter(Animal.Type::isPrey).toList();
        prey = new Animal(preyList.get((int)(Math.random() * preyList.size())));
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