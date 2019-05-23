package pl.edu.pw.elka.proz.bandaimbecyli.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultsResponse implements Serializable {
    private prozResults results;
    private ArrayList<Integer> correctAnswers;

    public ResultsResponse() {
    }

    public ResultsResponse(prozResults results, ArrayList<Integer> correctAnswers) {
        this.results = results;
        this.correctAnswers = correctAnswers;
    }

    public prozResults getResults() {
        return results;
    }

    public ArrayList<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public static ArrayList<Integer> correctAnswersForTest(prozTest test)
    {
        ArrayList<Integer> correctAnswers = new ArrayList<>();
        for(prozQuestion question : test.getQuestions()) {
            for(prozAnswer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    correctAnswers.add(answer.getAnswerID());
                }
            }
        }
        return correctAnswers;
    }
}
