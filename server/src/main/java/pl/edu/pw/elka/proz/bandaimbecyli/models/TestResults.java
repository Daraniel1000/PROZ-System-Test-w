package pl.edu.pw.elka.proz.bandaimbecyli.models;

public class TestResults {
    private int generatedTestId;
    private String someMsg; // TODO

    public TestResults() {
    }

    public TestResults(int generatedTestId, String someMsg) {
        this.generatedTestId = generatedTestId;
        this.someMsg = someMsg;
    }

    public int getGeneratedTestId() {
        return generatedTestId;
    }

    public void setGeneratedTestId(int generatedTestId) {
        this.generatedTestId = generatedTestId;
    }

    public String getSomeMsg() {
        return someMsg;
    }

    public void setSomeMsg(String someMsg) {
        this.someMsg = someMsg;
    }
}
