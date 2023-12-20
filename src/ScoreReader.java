import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreReader {
    private static final File scores = new File("scores.csv");

    static {
        if (!scores.exists()) {
            try {
                scores.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Score> readScore() {
        try {
            FileReader reader = new FileReader(scores);
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
