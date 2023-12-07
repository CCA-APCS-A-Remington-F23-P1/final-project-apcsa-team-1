import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.stream.*;
import java.util.List;
import java.awt.*;

public class Actor {
    private BufferedImage[] images;
    private int animationFrame = 0;
    private int animationRate = 100;
    private long animationTimer = System.currentTimeMillis();

    public static final File IMAGES = new File("images");
    public int width;
    public int height;
    public int x;
    public int y;
    public double scale = 1.0;

    public Actor() {}

    /**
     * Takes a path and appends it to the `images` folder, takes all images from the
     * result path and uses those as the animation images for this actor
     */
    public Actor frames(String dir) {
        File[] files = new File(IMAGES, dir).listFiles();
        images = Stream.of(files)
                .map(file -> {
                    try {
                        return ImageIO.read(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(BufferedImage[]::new);

        if (images.length == 0) {
            throw new IllegalArgumentException("frame directory cannot be empty");
        }

        var first = images[0];
        width = first.getWidth();
        height = first.getHeight();

        for (int i = 0; i < files.length; i++) {
            var image = images[i];
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            var msg = String.format("%s is a %dx%d image, expected %dx%d", files[i].getAbsolutePath(), imageWidth, imageHeight, width, height);
            if (imageWidth != width || imageHeight != height) {
                throw new IllegalStateException(msg);
            }
        }
        return this;
    }

    public Actor size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Actor pos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Actor scale(double scale) {
        this.scale = scale;
        return this;
    }

    public Actor animationRate(int millis) {
        this.animationRate = millis;
        return this;
    }

    public void draw(Graphics frame) {
        int imageWidth = (int)(scale * width);
        int imageHeight = (int)(scale * height);
        frame.drawImage(images[animationFrame], x,  y, imageWidth, imageHeight, null);
    }

    public void update() {
        long now = System.currentTimeMillis();
        long elapsed = now - animationTimer;
        if (elapsed > animationRate) {
            animationTimer = now;
            animationFrame = (animationFrame + 1) % images.length;
        }
    }

    /**
     * The `translate` method is provided for basic movement across the screen,
     * override the `move` method to implement appropriate movement with speed,
     * velocity, etc...
     */
    public void translate(Direction direction, int amount) {
        switch (direction) {
            case UP -> this.y -= amount;
            case DOWN -> this.y += amount;
            case LEFT -> this.x -= amount;
            case RIGHT -> this.x += amount;
        }
    }

    public boolean isColliding(Actor other) {
        if (x + width < other.x) return false;
        if (other.x + other.width < x) return false;
        if (y + height < other.y) return false;
        if (other.y + other.height < y) return false;

        onCollision(other);
        other.onCollision(this);

        return true;
    }

    /**
     * Override this to implement any logic that relies on collisions
     */
    public void onCollision(Actor other) {}
}
