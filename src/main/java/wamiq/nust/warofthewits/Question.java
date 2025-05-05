package wamiq.nust.warofthewits;

import java.util.List;

public class Question {
    private final String questionText;
    private final List<String> answers; // Should contain exactly 4 answers.
    private final int correctAnswerIndex;

    // Constructor
    public Question(String questionText, List<String> answers, int correctAnswerIndex) {
        if (answers == null || answers.size() != 4) {
            throw new IllegalArgumentException("There must be exactly 4 answers.");
        }
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }
    public List<String> getAnswers() {
        return answers;
    }

    //check an answer
    public boolean isCorrect(int ans) {
        return ans == correctAnswerIndex;
    }
}
