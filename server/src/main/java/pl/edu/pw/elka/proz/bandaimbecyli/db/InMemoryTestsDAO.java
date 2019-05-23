package pl.edu.pw.elka.proz.bandaimbecyli.db;

import pl.edu.pw.elka.proz.bandaimbecyli.models.prozAnswer;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozResults;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;

import java.sql.SQLException;
import java.util.*;

public class InMemoryTestsDAO implements TestsDAO {
    private static final Map<Integer, prozTest> tests = new HashMap<>();

    public InMemoryTestsDAO() {
    }

    public InMemoryTestsDAO(Collection<prozTest> tsts) {
        setTests(tsts);
    }

    private void setTests(Collection<prozTest> tsts)
    {
        tests.clear();
        for (prozTest t : tsts) {
            tests.put(t.getTestID(), t);
        }
    }

    @Override
    public int CheckUserLogin(String username, String password) throws SQLException {
        return username.equals("test") && password.equals("123") ? 1 : -1;
    }

    @Override
    public List<prozTest> GetTestsAvailableForUser(int uID) throws SQLException {
        List<prozTest> ret = new ArrayList<>();
        if (uID != 1)
            return ret;
        for(prozTest test : tests.values())
        {
            ret.add(new prozTest(
                    test.getTestID(),
                    test.getTitle(),
                    test.getStartDate(),
                    test.getEndDate(),
                    test.getType()
            ));
        }
        return ret;
    }

    @Override
    public prozTest GetTest(int tID) throws SQLException {
        prozTest test = tests.get(tID);
        if (test == null)
            return null;
        return new prozTest(
                test.getTestID(),
                test.getTitle(),
                test.getStartDate(),
                test.getEndDate(),
                test.getType()
        );
    }

    @Override
    public void FillTestQuestions(prozTest test) throws SQLException {
        List<prozQuestion> questions = tests.get(test.getTestID()).getQuestions();
        test.initQuestions(questions.size());
        for(prozQuestion question : questions)
        {
            test.addQuestion(new prozQuestion(question.getQuestionID(), question.getType(), question.getText()));
        }
    }

    @Override
    public void FillQuestionAnswers(prozQuestion question) throws SQLException {
        prozQuestion q = null;
        for(prozTest t : tests.values()) {
            for(prozQuestion q2 : t.getQuestions()) {
                if (q2.getQuestionID() == question.getQuestionID()) {
                    q = q2;
                    break;
                }
            }
        }
        List<prozAnswer> answers = q.getAnswers();
        question.initAnswers(answers.size());
        for(prozAnswer answer : answers)
        {
            question.addAnswer(new prozAnswer(answer.getAnswerID(), answer.isCorrect(), answer.getText()));
        }
    }

    @Override
    public void SendResults(prozResults Results) throws SQLException {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public prozResults GetResults(int tID, int uID) throws SQLException {
        throw new UnsupportedOperationException(); // TODO
    }
}
