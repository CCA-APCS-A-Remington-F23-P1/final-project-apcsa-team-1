import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;

public class Leaderboard extends Scene {
    private BufferedImage background = Main.loadImage(Paths.get("images", "forest-background.png"));

    public class Renderer extends JLabel implements ListCellRenderer<Score> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Score> list, Score score, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            var msg = String.format("On %s, %d rounds were passed in %d seconds.", new Date(score.unixTime * 1000).toString(), score.highestRound, score.timeTaken);
            setText(msg);
            setFont(new Font("Monospace", Font.PLAIN, 24));
            var border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBorder(border);

            return this;
        }
    }

    public Leaderboard() {
        super();

        var overlay = new Scene();

        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setOpaque(false);

        var scores = ScoreReader.readScore("scores.csv");
        scores.sort(Comparator.comparingInt(a -> a.highestRound));

        var label = new JLabel("Leaderboard");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setFont(new Font("Monospace", Font.PLAIN, 64));
        label.setOpaque(true);
        overlay.add(label);

        var list = new JList(scores.toArray());
        list.setCellRenderer(new Renderer());
        list.setSelectionBackground(Color.RED);
        overlay.add(list);

        overlay.setBounds(0, 0, Main.WIDTH, Main.HEIGHT);
        add(overlay);

        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawImage(background, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    }
}
