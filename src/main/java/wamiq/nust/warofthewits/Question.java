package wamiq.nust.warofthewits;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> answers; // Should contain exactly 4 answers.
    private int correctAnswerIndex;

    // Constructor
    public Question(String questionText, List<String> answers, int correctAnswerIndex) {
        if (answers == null || answers.size() != 4) {
            throw new IllegalArgumentException("There must be exactly 4 answers.");
        }
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }
    public void setAnswers(List<String> answers) {
        if (answers == null || answers.size() != 4) {
            throw new IllegalArgumentException("There must be exactly 4 answers.");
        }
        this.answers = answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Utility method to check an answer (0-3)
    public boolean isCorrect(int ans) {
        return ans == correctAnswerIndex;
    }
}
