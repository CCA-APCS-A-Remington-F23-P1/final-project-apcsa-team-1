import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class Actor extends JLabel {
    public static final File IMAGES = new File("images/actors");
    public BufferedImage[] images;
    public int width;
    public int height;
    public int x;
    public int y;
    public boolean flip = false;
    private int animationFrame = 0;
    private int animationRate = 100;
    private long animationTimer = System.currentTimeMillis();
    private boolean rebound = true;
    private boolean active = true;

    public Actor() {
    }

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
            size(0, 0);
            return this;
        }

        var first = images[0];
        size(first.getWidth(), first.getHeight());

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

    public Actor size(int w, int h) {
        width = w;
        height = h;
        rebound = true;
        return this;
    }

    public Actor pos(int x, int y) {
        this.x = x;
        this.y = y;
        rebound = true;
        return this;
    }

    public Actor scale(double newScale) {
        size((int)(width * newScale), (int)(height * newScale));
        return this;
    }

    public Actor animationRate(int millis) {
        this.animationRate = millis;
        return this;
    }

    public Actor flip() {
        this.flip = !this.flip;
        return this;
    }

    public Actor flip(boolean flipState) {
        this.flip = flipState;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);
        BufferedImage img = images[animationFrame];
        if (flip) {
            frame.drawImage(img, 0, 0, width, height, img.getWidth(), 0, 0, img.getHeight(), null);
        } else {
            frame.drawImage(img, 0, 0, width, height, null);
        }
    }

    public void update() {
        if (isActive()) {
            if (rebound) {
                setBounds(x, y, width, height);
                revalidate();
                rebound = false;
            }

            long now = System.currentTimeMillis();
            long elapsed = now - animationTimer;
            if (elapsed > animationRate && images.length > 0) {
                animationTimer = now;
                animationFrame = (animationFrame + 1) % images.length;
            }
        }
    }

    /**
     * The `translate` method is provided for basic movement across the screen,
     * override the `move` method to implement appropriate movement with speed,
     * velocity, etc...
     */
    public void translate(Direction direction, int amount) {
        switch (direction) {
            case UP: this.y -= amount; break;
            case DOWN: this.y += amount; break;
            case LEFT: this.x -= amount; break;
            case RIGHT: this.x += amount; break;
        }
        pos(x, y);
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
    public void onCollision(Actor other) {
    }
}
