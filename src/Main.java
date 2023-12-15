import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static javax.swing.JLayeredPane.*;

public class Main {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int TRACK_HEIGHT = 200;
    public static final int MAIN_HEIGHT = HEIGHT - TRACK_HEIGHT;
    public static final int MAIN_WIDTH = WIDTH;
    public static final int FRAMERATE = 30;
    public static final State state = State.RUNNING;
    public static JFrame window;
    public static Path IMAGE_DIR = Paths.get("images");

    private static Scene currentScene;
    private static final ArrayList<Scene> popups = new ArrayList<>();
    private static JLayeredPane layer;

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::init);
    }

    public static BufferedImage loadImage(Path path) {
        try {
            return ImageIO.read(IMAGE_DIR.resolve(path).toFile());
        } catch (IOException e) {
            System.out.printf("[+] WARNING: failed to load image %s\n", path.toAbsolutePath().toString());
        }
        return null;
    }

    public static void push(Scene scene) {
        popups.add(scene);
        layer.add(scene, 0);
    }

    public static void pop() {
        layer.remove(popups.remove(popups.size() - 1));
    }

    public static void set(Scene scene) {
        if (currentScene != null) {
            layer.remove(currentScene);
        }
        currentScene = scene;
        layer.add(currentScene, 10);
    }

    private static void init() {
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

        set(new Game());

        // don't allow the user to resize the window
        window.setResizable(false);
        // fit the window size around the components (just our jpanel).
        // pack() should be called after setResizable() to avoid issues on some platforms
        window.pack();
        // open window in the center of the screen
        window.setLocationRelativeTo(null);
        // display the window
        window.setVisible(true);
        window.createBufferStrategy(2);

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
