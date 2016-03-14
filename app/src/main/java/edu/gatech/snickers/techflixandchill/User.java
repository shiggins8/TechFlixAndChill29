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

    public User(String name, String username, String password, String email, String securityHint,
                String major, boolean admin, int incorrectPasswordCounter, boolean blocked,
                boolean locked) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.securityHint = securityHint;
        this.major = major;
        this.admin = admin;
        this.incorrectPasswordCounter = incorrectPasswordCounter;
        this.blocked = blocked;
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityHint() {
        return securityHint;
    }

    public void setSecurityHint(String securityHint) {
        this.securityHint = securityHint;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    public int getIncorrectPasswordCounter() {
        return incorrectPasswordCounter;
    }

    public void setIncorrectPasswordCounter(int incorrectPasswordCounter) {
        this.incorrectPasswordCounter = incorrectPasswordCounter;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setIsBlocked(boolean isBlocked) {
        this.blocked = isBlocked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setIsLocked(boolean isLocked) {
        this.locked = isLocked;
    }
}
