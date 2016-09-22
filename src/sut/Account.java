package sut;

/**
 * Representation of an account under test.
 * Use only in applications where a user has to login at some point.
 * @author Thibault Helsmoortel
 */
public class Account {
    private String mail;
    private String password;

    /**
     * Class constructor specifying email and password.
     * @param mail the account's email address
     * @param password the account's password
     */
    public Account(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
