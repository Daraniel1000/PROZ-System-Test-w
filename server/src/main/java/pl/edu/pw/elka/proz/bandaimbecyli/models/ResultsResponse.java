package pl.edu.pw.elka.proz.bandaimbecyli.models;

import java.io.Serializable;
import java.util.List;

public class ResultsResponse implements Serializable {
    private prozResults results;
    private List<Integer> correctAnswers;

    public ResultsResponse() {
    }

    public ResultsResponse(prozResults results, List<Integer> correctAnswers) {
        this.results = results;
        this.correctAnswers = correctAnswers;
    }

    public prozResults getResults() {
        return results;
    }

    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }
}
