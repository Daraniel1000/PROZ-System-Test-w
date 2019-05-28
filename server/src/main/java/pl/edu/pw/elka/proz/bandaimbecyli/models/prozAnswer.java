package pl.edu.pw.elka.proz.bandaimbecyli.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class prozAnswer implements Serializable {
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

    // @JsonIgnore // TODO: ukryć przy wysyłaniu testu
    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }
}
