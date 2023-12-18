import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Leaderboard extends Scene {
    String[] msgs = {
        "ONE",
        "TWO",
        "THREE"
    };

    public Leaderboard() {
        super();

        var scores = new File("scores.txt");

        var list = new JList(msgs);
        list.setBounds(0, 0, Main.WIDTH / 2, Main.HEIGHT - 200);
        list.setSelectionBackground(Color.RED);
        add(list);
    }
}
