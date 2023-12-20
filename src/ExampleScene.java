import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ExampleScene extends Scene {
    private static final String BUTTON_ACTION = "i was pressed";

    public ExampleScene() {
        super();

        setOpaque(false);

        JButton testing = new JButton("Hey press me!");
        // when the button is pressed it emits an "Action"
        // we tell it to emit a string "i was pressed" and we listen for it in the handle function
        testing.setActionCommand(BUTTON_ACTION);
        testing.addActionListener(this);
        testing.setFocusable(false);
        // set the button location
        // setBounds(int x, int y, int width, int height);
        testing.setBounds(Main.WIDTH / 2, Main.HEIGHT / 2, 100, 50);

        // add the button to the game
        add(testing);
    }

    @Override
    public void paintComponent(Graphics frame) {
        super.paintComponent(frame);

        frame.setColor(Color.RED);
        frame.fillRect(0, 0, 64, 64);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var action = e.getActionCommand();
        if (action != null) switch (action) {
            case BUTTON_ACTION: {
                System.out.println("I was pressed");
                break;
            }
        }
        super.actionPerformed(e);
    }
}
