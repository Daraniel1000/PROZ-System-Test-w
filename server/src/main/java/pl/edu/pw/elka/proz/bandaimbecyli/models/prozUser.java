package pl.edu.pw.elka.proz.bandaimbecyli.models;

import java.io.Serializable;

public class prozUser implements Serializable {
    private int userId;
    private String login;
    private String firstName;
    private String lastName;
    private boolean admin;

    public prozUser() {
    }

    public prozUser(int userId, String login, String firstName, String lastName, boolean admin) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admin = admin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
