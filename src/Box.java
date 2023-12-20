import java.awt.*;

public class Box extends Scene {
    public Box() {
        super();

        setLayout(new GridBagLayout());
    }

    public GridBagConstraints centerConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    public GridBagConstraints topConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        return gbc;
    }
}
