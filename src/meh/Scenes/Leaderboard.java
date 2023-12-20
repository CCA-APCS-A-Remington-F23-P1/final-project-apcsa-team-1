package meh.Scenes;

import meh.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Leaderboard extends Box {
    private final BufferedImage background = Main.loadImage(Paths.get("images", "forest-background.png"));

    public Leaderboard() {
        super();

        var scores = ScoreReader.readScore();
        scores.sort((a, b) -> Integer.compare(b.highestRound, a.highestRound));

        var label = new JLabel("Leaderboard");
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setFont(new Font("Monospace", Font.PLAIN, 64));
        label.setOpaque(true);
        {
            var gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(32, 0, 0, 0);
            add(label, gbc);
        }

        if (!scores.isEmpty()) {
            var list = new JList<>(scores.toArray(Score[]::new));
            list.setCellRenderer(new Renderer());
            list.setSelectionBackground(Color.RED);
            list.setLayoutOrientation(JList.VERTICAL);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(list);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            {
                var gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weighty = 1.0;
                add(scrollPane, gbc);
            }
        } else {
            var filler = new JPanel();
            filler.setOpaque(false);
            {
                var gbc = new GridBagConstraints();
                gbc.weighty = 1.0;
                add(filler, gbc);
            }
        }

        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.drawImage(background, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    }

    public class Renderer extends JLabel implements ListCellRenderer<Score> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Score> list, Score score, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Date date = new Date(score.unixTime * 1000);
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            var msg = String.format("On %s, %d rounds were passed in %.1f seconds.", simpleDateFormat.format(date), score.highestRound, (double) score.timeTaken / 1000.0);
            setText(msg);
            setFont(new Font("Monospace", Font.PLAIN, 24));
            var border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBorder(border);

            return this;
        }
    }
}
