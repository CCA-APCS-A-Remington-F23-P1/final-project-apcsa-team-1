import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NextLevel extends Scene {
    private static final String NEXT_LEVEL = "uwu";

    public NextLevel() {
        super();

        setOpaque(false);

        JButton testing = new JButton("Next level");
        testing.setFont(Main.BUTTON_FONT);
        testing.setBackground(Color.GREEN);
        // when the button is pressed it emits an "Action"
        // we tell it to emit a string "i was pressed" and we listen for it in the handle function
        testing.setActionCommand(NEXT_LEVEL);
        testing.addActionListener(this);
        testing.setFocusable(false);
        // set the button location
        // setBounds(int x, int y, int width, int height);
        int width = textWidth(testing.getFont(), testing.getText()) + 64;
        int height = textHeight(testing.getFont(), testing.getText());
        testing.setBounds((Main.WIDTH - width) / 2, (Main.HEIGHT - height) / 2, width, height);

        // add the button to the game
        add(testing);
    }

    @Override
    public void paintComponent(Graphics frame) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch(action) {
            case NEXT_LEVEL: {
                Game game = (Game)Main.current();
                game.nextRound();
                Main.pop();
                break;
            }
        }
        super.actionPerformed(e);
    }
}
