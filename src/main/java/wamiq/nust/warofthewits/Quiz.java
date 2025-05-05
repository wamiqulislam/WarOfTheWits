package wamiq.nust.warofthewits;

import java.util.List;

public class Quiz {
    private final String title;
    private final List<Question> questionList;

    // Constructor
    public Quiz(String title, List<Question> questionList) {
        this.title = title;
        this.questionList = questionList;
    }

    // Getter
    public List<Question> getQuestionList() {
        return questionList;
    }



}
