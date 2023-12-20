public class Score {
    public long timeTaken;
    public int highestRound;
    public long unixTime;

    public Score(int highestRound, long timeTaken, long unixTime) {
        this.highestRound = highestRound;
        this.timeTaken = timeTaken;
        this.unixTime = unixTime;
    }

    public static Score deserialize(String data) {
        String[] tokens = data.split(",");
        return new Score(Integer.parseInt(tokens[0]), Long.parseLong(tokens[1]), Long.parseLong(tokens[2]));
    }

    public String serialize() {
        return String.format("%d,%d,%d", highestRound, timeTaken, unixTime);
    }
}