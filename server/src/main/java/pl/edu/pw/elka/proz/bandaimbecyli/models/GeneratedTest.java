package pl.edu.pw.elka.proz.bandaimbecyli.models;

public class GeneratedTest {
    private int id;
    private int testId;
    private String someMsg;

    public GeneratedTest() {
    }

    public GeneratedTest(int testId, String someMsg) {
        this.testId = testId;
        this.someMsg = someMsg;
    }

    public GeneratedTest(int id, int testId, String someMsg) {
        this.id = id;
        this.testId = testId;
        this.someMsg = someMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getSomeMsg() {
        return someMsg;
    }

    public void setSomeMsg(String someMsg) {
        this.someMsg = someMsg;
    }
}
