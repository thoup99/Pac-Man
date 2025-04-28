package server;

import java.io.*;

public class ScoreSaver {
    static int highScore = 0;
    static File scoreFile;

    public static void init() {
        scoreFile = new File("./highScore.txt");
        if (scoreFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(scoreFile));) {
                highScore = Integer.parseInt(br.readLine());

            } catch (IOException e) {
                System.out.println("An IOException Occurred When Loading\n" + e);
            }


        } else {
            saveScore();
        }
    }

    private static void saveScore() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(scoreFile))) {
            bufferedWriter.write(String.valueOf(highScore));

        } catch (IOException e) {
            System.out.println("An IOException Occurred When Saving\n" + e);
        }
    }

    public static void checkScore(int newScore) {
        synchronized (scoreFile) {
            if (newScore > highScore) {
                highScore = newScore;
                saveScore();
            }
        }
    }

    public static int getHighScore() {
        return highScore;
    }
}
