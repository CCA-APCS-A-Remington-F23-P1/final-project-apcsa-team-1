package meh;

import meh.Animal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Predator extends Animal {
    public long hoveredTime = 0;
    public boolean pressed;
    private long timer;
    private int oldWidth;
    private int oldHeight;
    private boolean isDraggable = true;
    private boolean isHoverable = true;
    private boolean isCrazy = false;
    private Point velocity = randomVelocity();

    public Predator(Type type) {
        super(type);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                timer = System.currentTimeMillis();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredTime = System.currentTimeMillis();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isActive() && isHoverable) {
                    oldWidth = width;
                    oldHeight = height;
                    scale(1.3);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isActive() && isHoverable) {
                    size(oldWidth, oldHeight);
                }
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public Actor draggable(boolean state) {
        isDraggable = state;
        return this;
    }

    public Actor hoverable(boolean state) {
        isHoverable = state;
        return this;
    }

    public Actor crazy(boolean state) {
        isCrazy = state;
        if (isCrazy) {
            velocity = randomVelocity();
        }
        return this;
    }

    private Point randomVelocity() {
        int velx = (int)(Math.random() * 20) - 10;
        int vely = (int)(Math.random() * 20) - 10;
        return new Point(velx, vely);
    }

    @Override
    public Actor pos(int ix, int iy) {
        var BOTTOM = Main.MAIN_HEIGHT;
        ix = Math.max(ix, 0);
        iy = Math.max(iy, 0);
        if (ix + width > Main.WIDTH) ix = Main.WIDTH - width;
        if (iy + height > BOTTOM) iy = BOTTOM - height;
        return super.pos(ix, iy);
    }

    @Override
    public void update() {
        super.update();

        if (isActive()) {
            var screen = MouseInfo.getPointerInfo().getLocation();
            var target = new Point(screen.x - Main.window.getLocationOnScreen().x, screen.y - Main.window.getLocationOnScreen().y);

            if (pressed && isDraggable) {
                long now = System.currentTimeMillis();
                double elapsed = (double) (now - timer) / 1000;
                timer = now;

                double lerp = 6.0 * elapsed;
                var distance = new Point(target.x - (x + width / 2), target.y - (y + height / 2));
                var move = new Point((int) (distance.x * lerp), (int) (distance.y * lerp));

                pos(x + move.x, y + move.y);
            } else if (isCrazy) {
                pos(x + velocity.x, y + velocity.y);
                if (x <= 0 || x + width >= Main.WIDTH) {
                    velocity = new Point(-velocity.x, velocity.y);
                }
                if (y <= 0 || y + height >= Main.MAIN_HEIGHT) {
                    velocity = new Point(velocity.x, -velocity.y);
                }
            }
        }
    }
}
