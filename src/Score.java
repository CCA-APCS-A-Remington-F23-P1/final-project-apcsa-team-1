public class Score {
    String playerName;
    int highestLevel;
    int highestScore;
    int currentGameScore;

    public Score(String playerName, int highestLevel, int highestScore) {
        this.playerName = playerName;
        this.highestLevel = highestLevel;
        this.highestScore = highestScore;
        this.currentGameScore = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentGameScore() {
        return this.currentGameScore;
    }

    public void setCurrentGameScore(int currentGameScore) {
        this.currentGameScore = currentGameScore;
    }

    @Override
    public String toString() {
        return "Player: " + playerName + " Highest Level: " + highestLevel + " Highest Score: " + highestScore;
    }
}