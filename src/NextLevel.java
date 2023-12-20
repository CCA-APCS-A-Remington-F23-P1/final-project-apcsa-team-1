import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NextLevel extends Box {
    private static final String NEXT_LEVEL = "uwu";

    public NextLevel() {
        super();

        setOpaque(false);

        var game = (Game) Main.current();

        var round = new JLabel(String.format("Passed round %d", game.round + 1), SwingConstants.CENTER);
        round.setFont(Main.BUTTON_FONT);
        round.setBackground(Color.GREEN);
        round.setOpaque(true);
        pad(round, new Insets(0, 16, 0, 16));
        {
            var gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(0, 0, 16, 0);
            add(round, gbc);
        }

        var next = new JButton("Next level");
        next.setFont(Main.BUTTON_FONT);
        next.setBackground(Color.GREEN);
        // when the button is pressed it emits an "Action"
        // we tell it to emit a string "i was pressed" and we listen for it in the handle function
        next.setActionCommand(NEXT_LEVEL);
        next.addActionListener(this);
        next.setFocusable(false);
        {
            var gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            add(next, gbc);
        }
    }

    @Override
    public void paintComponent(Graphics frame) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case NEXT_LEVEL: {
                Game game = (Game) Main.current();
                game.nextRound();
                Main.pop();
                break;
            }
        }
        super.actionPerformed(e);
    }
}
