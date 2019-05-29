package pl.edu.pw.elka.proz.bandaimbecyli.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;

public class prozQuestion implements Serializable {
    public static final int TYPE_SINGLE_CHOICE = 0;
    public static final int TYPE_MULTIPLE_CHOICE = 1;

    private int questionID = -1, type;
    private String text;
    private ArrayList<prozAnswer> answers;

    public prozQuestion() {
    }

    public prozQuestion(int id, int ty, String te)
    {
        questionID = id;
        type = ty;
        text = te;
    }

    public void setAnswers(ArrayList<prozAnswer> aList)
    {
        if (aList == null)
            answers = null;
        else
            answers = new ArrayList<>(aList);
    }

    public void initAnswers(int n)
    {
        if(answers == null)
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

    public ArrayList<prozAnswer> getAnswers() {
        return answers;
    }

    @JsonIgnore
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

    public void setQuestionID(int id)
    {
        questionID = id;
    }
}
