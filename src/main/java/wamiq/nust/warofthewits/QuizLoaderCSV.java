package wamiq.nust.warofthewits;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizLoaderCSV {

    public static Quiz loadQuizFromCSV(String filePath, String quizTitle) {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                if (columns.length < 6) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String questionText = columns[0];
                List<String> answers = List.of(columns[1], columns[2], columns[3], columns[4]);
                int correctAnswerIndex = Integer.parseInt(columns[5]);

                Question question = new Question(questionText, answers, correctAnswerIndex);
                questions.add(question);
                System.out.println(questionText);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new Quiz(quizTitle, questions);
    }
}
