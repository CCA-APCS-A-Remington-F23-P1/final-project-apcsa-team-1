import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreReader {
    public static List<Score> readScore(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            List<Score> scores = new ArrayList<Score>();
            while (line != null) {
                scores.add(Score.deserialize(line));
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            return scores;
        } catch (IOException e) {
            return List.of();
        }
    }
}
