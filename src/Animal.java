import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.List;

public class Animal extends Actor {
    public final Type type;
    public long hoveredTime = 0;
    public boolean pressed;
    private long timer;
    private int oldWidth;
    private int oldHeight;

    public Animal(Type type) {
        super();

        this.type = type;
        frames(Paths.get("actors").resolve(type.name().toLowerCase()));
        scale(type.customScaling);
        flip(type.flip);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                timer = System.currentTimeMillis();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                flip();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredTime = System.currentTimeMillis();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                oldWidth = width;
                oldHeight = height;
                scale(1.3);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                size(oldWidth, oldHeight);
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    @Override
    public Actor pos(int ix, int iy) {
        var TOP = Main.HEIGHT;
        ix = Math.max(ix, 0);
        iy = Math.max(iy, 0);
        if (ix + width > Main.WIDTH) ix = Main.WIDTH - width;
        if (iy + height > TOP) iy = TOP - height;
        return super.pos(ix, iy);
    }

    @Override
    public void update() {
        super.update();

        if (isActive()) {
            if (pressed) {
                Point screen = MouseInfo.getPointerInfo().getLocation();
                Point target = new Point(screen.x - Main.window.getLocationOnScreen().x, screen.y - Main.window.getLocationOnScreen().y);

                long now = System.currentTimeMillis();
                double elapsed = (double) (now - timer) / 1000;
                timer = now;

                double lerp = 6.0 * elapsed;
                var distance = new Point(target.x - (x + width / 2), target.y - (y + height / 2));
                var velocity = new Point((int) (distance.x * lerp), (int) (distance.y * lerp));

                pos(x + velocity.x, y + velocity.y);
            }
        }
    }

    public enum Type {
        BRACHIOSAURUS(.2),
        CATEPILLAR(.1),
        CLOWNFISH(.3),
        COBRA,
        COW(.7),
        DEER(.3),
        DONKEY(.2),
        DUCK(.2),
        EAGLE(.5),
        FROG(.4),
        GRASSHOPPER(.3),
        HORSE,
        LADYBUG(.1),
        LION(.3),
        MOOSE,
        MOUSE(0.5),
        OCTOPUS(.4),
        PARROT(0.1),
        PIG(.5),
        PTERODACTYL(.3),
        PUFFERFISH(.5),
        REINDEER(.1),
        RAT(.3),
        SHARK(.3),
        SHEEP(.3),
        SQUIRREL(.2),
        TIGER(.3),
        TOUCAN(0.35),
        TREX(0.05),
        TRICERATOPS(.3),
        WOLF(.2),
        ZEBRA(.4);

        public static final List<Type> PREDATORS = List.of(Type.EAGLE, Type.LION, Type.SHARK, Type.TIGER, Type.TREX, Type.WOLF, Type.FROG, Type.COBRA);
        public double customScaling = 1.0;
        public boolean flip = false;

        Type() {
        }

        Type(double scaleFactor) {
            this.customScaling = scaleFactor;
        }

        Type(boolean flip) {
            this.flip = flip;
        }

        Type(double scaleFactor, boolean flip) {
            this.customScaling = scaleFactor;
            this.flip = flip;
        }

        public boolean isPredator() {
            return PREDATORS.contains(this);
        }

        public boolean eats(Type other) {
            switch (other) {
                case DUCK:
                case PARROT:
                case TOUCAN:
                    return this == Type.EAGLE;
                case CATEPILLAR:
                case LADYBUG:
                case GRASSHOPPER:
                    return this == Type.FROG;
                case PUFFERFISH:
                case CLOWNFISH:
                case OCTOPUS:
                    return this == Type.SHARK;
                case PIG:
                case SHEEP:
                case COW:
                    return this == Type.WOLF;
                case DEER:
                case REINDEER:
                case MOOSE:
                    return this == Type.TIGER;
                case MOUSE:
                case RAT:
                case SQUIRREL:
                    return this == Type.COBRA;
                case HORSE:
                case DONKEY:
                case ZEBRA:
                    return this == Type.LION;
                case TRICERATOPS:
                case PTERODACTYL:
                case BRACHIOSAURUS:
                    return this == Type.TREX;
                default:
                    return false;
            }
        }

        public boolean isPrey() {
            return !isPredator();
        }
    }
}