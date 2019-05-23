package pl.edu.pw.elka.proz.bandaimbecyli.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class prozTest implements Serializable {
    public static final int TYPE_TEST = 0; // TODO: ankiety czy cokolwiek to miało być?

    private int testID, type;
    private String title;
    private Timestamp startDate, endDate;
    private ArrayList<prozQuestion> questions;

    public prozTest() {
    }

    public prozTest(int id, String ti, Timestamp sd, Timestamp ed, int ty)
    {
        testID = id;
        title = ti;
        startDate = sd;
        endDate = ed;
        type = ty;
    }

    public void setQuestions(ArrayList<prozQuestion> qList)
    {
        if (qList == null)
            questions = null;
        else
            questions = new ArrayList<>(qList);
    }

    public void initQuestions(int i)
    {
        if(questions == null)
            questions = new ArrayList<>(i);
    }

    public boolean addQuestion(prozQuestion q)
    {
        return questions.add(q);
    }

    public prozQuestion getQuestion(int i)
    {
        return questions.get(i);
    }

    @JsonIgnore
    public int getQuestionsSize()
    {
        return questions.size();
    }

    public ArrayList<prozQuestion> getQuestions() {
        return questions;
    }

    public int getTestID() {
        return testID;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public int getType() {
        return type;
    }

}
