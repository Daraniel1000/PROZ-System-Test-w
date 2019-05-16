package database_classes;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static database_classes.prozDatabaseConnection.GetTestQuestions;

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

    public void setQuestions(ArrayList<prozQuestion> qList)
    {
        questions = new ArrayList<>(qList);
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

    public int getQuestionsSize()
    {
        return questions.size();
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
