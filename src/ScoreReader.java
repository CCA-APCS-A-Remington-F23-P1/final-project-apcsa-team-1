import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreReader {
    public static void testScoreReader() {
        try {
            ScoreReader reader = new ScoreReader();
            List<Score> scores = reader.readScore("scores.csv");
            for (Score score : scores) {
                System.out.println(score);
            }
        } catch (Exception ex) {
            System.out.println("Error reading scores.csv");
            ex.printStackTrace();

        }
    }

    public List<Score> readScore(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        List<Score> scores = new ArrayList<Score>();
        while (line != null) {
            String[] tokens = line.split(",");
            Score score = new Score(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            scores.add(score);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        return scores;
    }
}
