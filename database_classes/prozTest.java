package database_classes;

import java.util.ArrayList;

public class prozTest {
    private int testID, type;
    private String title, startDate, endDate;
    private ArrayList<prozQuestion> questions;

    public prozTest(int id, String ti, String sd, String ed, int ty, int questionAmount)
    {
        testID = id;
        title = ti;
        startDate = sd;
        endDate = ed;
        type = ty;
        questions = new ArrayList<>(questionAmount);
    }

    public prozTest(int id, String ti, String sd, String ed, int ty)
    {
        testID = id;
        title = ti;
        startDate = sd;
        endDate = ed;
        type = ty;
    }

    public void initQuestions(int i)
    {
        if(!questions.isEmpty()) return;
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

    public int getTestID() {
        return testID;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getType() {
        return type;
    }

}
