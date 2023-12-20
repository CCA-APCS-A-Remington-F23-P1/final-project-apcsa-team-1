package meh.Scenes;

import meh.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scene extends JPanel implements ActionListener {
    private boolean active = true;

    public Scene() {
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        setLayout(null);
        setBounds(0, 0, Main.WIDTH, Main.HEIGHT);
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

    public void update() {
    }

    public void reset() {
    }

    public int textWidth(Font f, String msg) {
        var metrics = getFontMetrics(f);
        return metrics.stringWidth(msg);
    }

    public int textHeight(Font f, String msg) {
        var metrics = getFontMetrics(f);
        return metrics.getHeight();
    }

    public void pad(JComponent victim, Insets insets) {
        victim.setBorder(new CompoundBorder(victim.getBorder(), new EmptyBorder(insets)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);
    }
}
