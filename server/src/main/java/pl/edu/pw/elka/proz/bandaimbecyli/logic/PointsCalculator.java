package pl.edu.pw.elka.proz.bandaimbecyli.logic;

import pl.edu.pw.elka.proz.bandaimbecyli.models.prozAnswer;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;

import java.util.List;

public class PointsCalculator {
    private final prozTest test;

    private int points = 0;

    public PointsCalculator(prozTest test) {
        this.test = test;
    }

    public void addPointsForQuestion(prozQuestion question, List<prozAnswer> answers) {
        boolean correct = true;
        for(prozAnswer a : question.getAnswers())
        {
            if (a.isCorrect() != answers.contains(a))
            {
                correct = false;
                break;
            }
        }

        if (correct)
            points += 1;
    }

    public int getPoints() {
        return points;
    }
}
