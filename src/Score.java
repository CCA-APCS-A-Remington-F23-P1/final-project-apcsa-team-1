import java.util.Date;

public class Score {
    public int timeTaken;
    public int highestRound;
    public long unixTime;

    public Score(int highestRound, int timeTaken, long unixTime) {
        this.highestRound = highestRound;
        this.timeTaken = timeTaken;
        this.unixTime = unixTime;
    }

    public String serialize() {
        return String.format("%d,%d,%d", highestRound, timeTaken, unixTime);
    }

    public static Score deserialize(String data) {
        String[] tokens = data.split(",");
        return new Score(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Long.parseLong(tokens[2]));
    }
}