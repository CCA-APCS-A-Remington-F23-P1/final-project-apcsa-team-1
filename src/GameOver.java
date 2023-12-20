import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameOver extends Box {
    // private final BufferedImage backgroundImage;
    private static final String BUTTON_ACTION_ONE = "i was pressed";
    private static final String BUTTON_ACTION_TWO = "i was pressed as well";

    JButton reset = new JButton("Restart");
    JButton menu = new JButton("Main Menu");
    JTextArea text = new JTextArea(20, 64);

    public GameOver() {
        super();

        var game = (Game) Main.current();

        var over = new JLabel("GAME OVER", SwingConstants.CENTER);
        over.setBackground(Color.GREEN);
        over.setFont(Main.BUTTON_FONT);
        over.setOpaque(true);
        add(over, centerConstraints());

        var score = new JLabel(String.format("Round: %d", game.round), SwingConstants.CENTER);
        score.setBackground(Color.GREEN);
        score.setFont(Main.BUTTON_FONT);
        score.setOpaque(true);
        add(score, centerConstraints());

        reset.setActionCommand(BUTTON_ACTION_ONE);
        reset.addActionListener(this);
        reset.setText("Restart");
        reset.setFocusable(false);
        reset.setFont(Main.BUTTON_FONT);
        reset.setBackground(Color.GREEN);
        add(reset, centerConstraints());

        menu.setActionCommand(BUTTON_ACTION_TWO);
        menu.addActionListener(this);
        menu.setText("Main Menu");
        menu.setFocusable(false);
        menu.setFont(Main.BUTTON_FONT);
        menu.setBackground(Color.GREEN);
        add(menu, centerConstraints());

        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case BUTTON_ACTION_ONE:
                Main.set(new Game());
                Main.pop();
                break;
            case BUTTON_ACTION_TWO:
                Main.set(new GameIntroScene());
                Main.pop();
                break;
        }
    }
}