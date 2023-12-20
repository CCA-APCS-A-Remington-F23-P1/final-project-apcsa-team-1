package meh;

import meh.Scenes.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import static javax.swing.JLayeredPane.POPUP_LAYER;

public class Main {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int TRACK_HEIGHT = 200;
    public static final int MAIN_HEIGHT = HEIGHT - TRACK_HEIGHT;
    public static final int MAIN_WIDTH = WIDTH;
    public static final int FRAMERATE = 15;
    public static final int DEFAULT_GAME_SECONDS = 10;
    public static JFrame window;
    public static Path IMAGE_DIR = Paths.get("images");
    public static Font BUTTON_FONT = new Font("MONOSPACE", Font.PLAIN, 64);
    public static int screenMinX;
    public static int screenMaxX;
    public static int screenMinY;
    public static int screenMaxY;
    private static final ArrayList<Scene> popups = new ArrayList<>();
    private static Scene currentScene;
    private static JLayeredPane layer;

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::init);
    }

    public static BufferedImage loadImage(Path path) {
        try {
            return ImageIO.read(path.toFile());
        } catch (IOException e) {
            System.out.printf("[+] WARNING: failed to load image %s\n", path.toAbsolutePath());
        }
        return null;
    }

    public static BufferedImage[] loadImages(Path path) {
        File file = IMAGE_DIR.resolve(path).toFile();
        if (file.isDirectory()) {
            var files = file.listFiles();
            return Stream.of(files)
                    .map(f -> Main.loadImage(f.toPath()))
                    .toArray(BufferedImage[]::new);
        } else {
            return new BufferedImage[]{Main.loadImage(file.toPath())};
        }
    }

    public static void push(Scene scene) {
        push(scene, true, null);
    }

    public static void push(Scene scene, boolean hasBack) {
        push(scene, hasBack, null);
    }

    public static void push(Scene scene, ActionListener listener) {
        push(scene, true, listener);
    }

    public static void push(Scene scene, boolean backButton, ActionListener listener) {
        popups.add(scene);
        layer.add(scene, POPUP_LAYER);
        if (backButton) {
            var button = new JButton("Back");
            button.setOpaque(true);
            button.setFont(BUTTON_FONT);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            var width = scene.textWidth(BUTTON_FONT, button.getText()) + 64;
            var height = scene.textHeight(BUTTON_FONT, button.getText());
            button.setBounds(0, HEIGHT - height, width, height);
            scene.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    pop();
                }
            });
            if (listener != null) {
                button.addActionListener(listener);
            }
        }
    }

    public static void pop() {
        layer.remove(popups.remove(popups.size() - 1));
    }

    public static Scene current() {
        return currentScene;
    }

    public static void set(Scene scene) {
        if (currentScene != null) {
            layer.remove(currentScene);
        }
        currentScene = scene;
        layer.add(currentScene, 10);
    }

    private static void init() {
        {
            var dyn = new JFrame("meh");
            dyn.setPreferredSize(new Dimension(10, 10));
            dyn.setResizable(false);
            // fit the window size around the components (just our jpanel).
            // pack() should be called after setResizable() to avoid issues on some platforms
            dyn.pack();
            // open window in the center of the screen
            dyn.setLocation(0, 0);
            dyn.setVisible(true);

            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            var defaultScreen = ge.getDefaultScreenDevice();
            var rect = defaultScreen.getDefaultConfiguration().getBounds();

            screenMinX = dyn.getLocationOnScreen().x;
            screenMinY = dyn.getLocationOnScreen().y + 20;
            screenMaxX = (int) rect.getMaxX();
            screenMaxY = (int) rect.getMaxY();

            dyn.dispatchEvent(new WindowEvent(dyn, WindowEvent.WINDOW_CLOSING));
        }

        window = new JFrame("Testing, testing...");
        // when we close the window, stop the app
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        layer = new JLayeredPane() {
            @Override
            public void paintComponent(Graphics frame) {
                super.paintComponent(frame);
                Toolkit.getDefaultToolkit().sync();
            }
        };
        layer.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        layer.setLayout(null);
        layer.setBounds(0, 0, WIDTH, HEIGHT);
        window.add(layer);

        set(new GameIntroScene());
        // push(new meh.Scenes.Leaderboard());

        // don't allow the user to resize the window
        window.setResizable(false);
        // fit the window size around the components (just our jpanel).
        // pack() should be called after setResizable() to avoid issues on some platforms
        window.pack();
        // open window in the center of the screen
        window.setLocationRelativeTo(null);
        // display the window
        window.setVisible(true);

        Timer timer = new Timer(1000 / FRAMERATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentScene.update();
                popups.forEach(Scene::update);
                window.repaint();
            }
        });
        timer.start();
    }
}
