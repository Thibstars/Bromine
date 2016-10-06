package sut;

import org.openqa.selenium.security.UserAndPassword;

/**
 * Representation of an account under test.
 * Use only in applications where a user has to login at some point.
 * @author Thibault Helsmoortel
 */
public class Account extends UserAndPassword {
    /**
     * Class constructor specifying email and password.
     * @param mail the account's email address
     * @param password the account's password
     */
    public Account(String mail, String password) {
        super(mail, password);
    }

    public String getMail() {
        return super.getUsername();
    }

    public String getPassword() {
        return super.getPassword();
    }
}
