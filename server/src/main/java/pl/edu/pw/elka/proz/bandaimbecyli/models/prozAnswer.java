package pl.edu.pw.elka.proz.bandaimbecyli.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class prozAnswer {
    private int answerID;
    private boolean correct;
    private String text;

    public prozAnswer() {
    }

    public prozAnswer(int id, boolean c, String t)
    {
        answerID = id;
        correct = c;
        text = t;
    }


    public int getAnswerID() {
        return answerID;
    }

    @JsonIgnore
    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }
}
