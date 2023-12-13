import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameIntroScene extends Scene {
    private final BufferedImage backgroundImage;
    private final int count = 0;

    public GameIntroScene(String filename) {
        System.out.println("Game Intro scene is created...");
        try {
            backgroundImage = ImageIO.read(new File(filename));
            playBackground();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics frame) {
        frame.drawImage(backgroundImage, 0, 0, Game.WIDTH, Game.HEIGHT, null);
        frame.setColor(Color.WHITE);
        frame.drawString("PRESS SPACE TO START", Game.WIDTH / 2 - 100, Game.HEIGHT / 2);
    }

    private void playBackground() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File musicPath = new File("arcade-bgm.wav");
        if (musicPath.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(100);
            clip.start();
        }
    }
}