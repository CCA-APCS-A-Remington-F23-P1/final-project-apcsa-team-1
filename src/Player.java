import java.util.List;

public class Player extends Actor {
    private static final List<Direction> WHITELIST = List.of(Direction.LEFT, Direction.RIGHT);

    /**
     * Player can only move left to right,
     * also bound player movement to window width.
     */
    @Override
    public void translate(Direction direction, int amount) {
        if (!WHITELIST.contains(direction)) return;

        super.translate(direction, amount);

        x = Math.max(0, x);
        if (x + width > Game.WIDTH) {
            x = Game.WIDTH - width;
        }
    }
}
