package pl.edu.pw.elka.proz.bandaimbecyli.models;

import java.util.List;

public class ResultsResponse {
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
