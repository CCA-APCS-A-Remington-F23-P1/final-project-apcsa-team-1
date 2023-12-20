package meh.Scenes;

import meh.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameIntroScene extends Box {
    private static final String BUTTON_START = ":3";
    private static final String BUTTON_HELP = "^w^";
    private static final String BUTTON_LEADERBOARD = ":P";
    private final BufferedImage backgroundImage;
    private final int count = 0;

    public GameIntroScene() {
        System.out.println("meh.Scenes.Game Intro scene is created...");
        try {
            backgroundImage = ImageIO.read(new File("images/forest-background.png"));
            playBackground();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var startButton = new JButton("Start game");
        startButton.setBackground(Color.GREEN);
        startButton.setActionCommand(BUTTON_START);
        startButton.setFont(Main.BUTTON_FONT);
        startButton.setFocusable(false);
        startButton.addActionListener(this);
        add(startButton, centerConstraints());

        var helpButton = new JButton("Help");
        helpButton.setBackground(Color.GREEN);
        helpButton.setActionCommand(BUTTON_HELP);
        helpButton.setFont(Main.BUTTON_FONT);
        helpButton.setFocusable(false);
        helpButton.addActionListener(this);
        add(helpButton, centerConstraints());

        var leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setBackground(Color.GREEN);
        leaderboardButton.setActionCommand(BUTTON_LEADERBOARD);
        leaderboardButton.setFont(Main.BUTTON_FONT);
        leaderboardButton.setFocusable(false);
        leaderboardButton.addActionListener(this);
        add(leaderboardButton, centerConstraints());
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);
        frame.drawImage(backgroundImage, 0, 0, Main.WIDTH, Main.HEIGHT, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case BUTTON_START:
                Main.set(new Game());
                break;
            case BUTTON_HELP:
                Main.push(new Help());
                break;
            case BUTTON_LEADERBOARD:
                Main.push(new Leaderboard());
                break;
        }
    }

    private void playBackground() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        File musicPath = new File("arcade-bgm.wav");
//        if (musicPath.exists()) {
//            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInput);
//            clip.loop(100);
//            clip.start();
//        }
    }
}