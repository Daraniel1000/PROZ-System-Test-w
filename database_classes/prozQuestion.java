package database_classes;

import java.util.ArrayList;

public class prozQuestion {
    private int questionID, type;
    private String text;
    private java.util.ArrayList<prozAnswer> answers;

    public prozQuestion(int id, int ty, String te)
    {
        questionID = id;
        type = ty;
        text = te;
    }

    public prozQuestion(int id, int ty, String te, int answerAmount)
    {
        questionID = id;
        type = ty;
        text = te;
        answers = new ArrayList<>(answerAmount);
    }

    public void setAnswers(ArrayList<prozAnswer> aList)
    {
        answers = new ArrayList<>(aList);
    }

    public void initAnswers(int n)
    {
        if(!answers.isEmpty()) return;
        answers = new ArrayList<>(n);
    }

    public boolean addAnswer(prozAnswer ans)
    {
        return answers.add(ans);
    }

    public prozAnswer getAnswer(int i)
    {
        return answers.get(i);
    }

    public int getAnswersSize(){return answers.size();}

    public int getQuestionID() {
        return questionID;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
