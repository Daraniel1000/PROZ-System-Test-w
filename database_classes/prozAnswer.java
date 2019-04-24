package database_classes;

public class prozAnswer {
    private int answerID;
    private boolean correct;
    private String text;

    public prozAnswer(int id, boolean c, String t)
    {
        answerID = id;
        correct = c;
        text = t;
    }


    public int getAnswerID() {
        return answerID;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }
}
