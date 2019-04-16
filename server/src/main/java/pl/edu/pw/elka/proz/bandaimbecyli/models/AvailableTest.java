package pl.edu.pw.elka.proz.bandaimbecyli.models;

//TODO
public class AvailableTest {
    private int id;
    private String name;

    public AvailableTest() {
    }

    public AvailableTest(String name) {
        this.name = name;
    }

    public AvailableTest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
