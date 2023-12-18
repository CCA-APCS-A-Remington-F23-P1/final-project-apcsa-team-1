import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public void update() {}

    public void reset() {}

    public int textWidth(Font f, String msg) {
        var metrics = getFontMetrics(f);
        return metrics.stringWidth(msg);
    }

    public int textHeight(Font f, String msg) {
        var metrics = getFontMetrics(f);
        return metrics.getHeight();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);
    }
}
