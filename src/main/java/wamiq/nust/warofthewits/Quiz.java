package wamiq.nust.warofthewits;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Quiz {
    private String title;
    private List<Question> questionList;

    // Constructor
    public Quiz(String title, List<Question> questionList) {
        this.title = title;
        this.questionList = questionList;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Question> getQuestionList() {
        return questionList;
    }
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }



}
