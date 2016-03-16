package edu.gatech.snickers.techflixandchill;

/**
 * Created on 2/21/16. Last modified on 3/14/16 - Pi Day!
 *
 * The user object of the app. Allows the Firebase to be utilized fully through it's JSON
 * integration. Provides a blank constructor for Firebase integration, and a full constructor
 * in cases of more normal use within the app.
 *
 * @author Snickers
 * @Version 1.2
 */
public class User {
    private String name;
    private String username;
    private String password;
    private String email;
    private String securityHint;
    private String major;
    private boolean admin;
    private int incorrectPasswordCounter;
    private boolean blocked;
    private boolean locked;

    public User() {
        //empty constructor for Firebase
    }

    public User(String pName, String pUsername, String pPassword, String pEmail, String pSecuHint,
                String pMajor, boolean pAdmin, int pIncorrectPasswordCounter, boolean pBlocked,
                boolean pLocked) {
        this.name = pName;
        this.username = pUsername;
        this.password = pPassword;
        this.email = pEmail;
        this.securityHint = pSecuHint;
        this.major = pMajor;
        this.admin = pAdmin;
        this.incorrectPasswordCounter = pIncorrectPasswordCounter;
        this.blocked = pBlocked;
        this.locked = pLocked;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String pUsername) {
        this.username = pUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    public String getSecurityHint() {
        return securityHint;
    }

    public void setSecurityHint(String pSecuHint) {
        this.securityHint = pSecuHint;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String pMajor) {
        this.major = pMajor;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean pAdmin) {
        this.admin = pAdmin;
    }

    public int getIncorrectPasswordCounter() {
        return incorrectPasswordCounter;
    }

    public void setIncorrectPasswordCounter(int pIncorrectPasswordCounter) {
        this.incorrectPasswordCounter = pIncorrectPasswordCounter;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean pBlocked) {
        this.blocked = pBlocked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean pLocked) {
        this.locked = pLocked;
    }
}
