package edu.gatech.snickers.techflixandchill;

/**
 * Created on 2/21/16. Last modified on 3/14/16 - Pi Day!
 *
 * The user object of the app. Allows the Firebase to be utilized fully through it's JSON
 * integration. Provides a blank constructor for Firebase integration, and a full constructor
 * in cases of more normal use within the app.
 *
 * @author Snickers
 * @version 1.2
 */
public class User {
    /**
     * Name of the user.
     */
    private String name;
    /**
     * Username the user has selected.
     */
    private String username;
    /**
     * Password the user has selected.
     */
    private String password;
    /**
     * Email of the user.
     */
    private String email;
    /**
     * Security hint that the user has chosen.
     */
    private String securityHint;
    /**
     * Georgia Tech major of the student.
     */
    private String major;
    /**
     * Boolean to determine admin status. True means they are an admin, false means they are not.
     */
    private boolean admin;
    /**
     * An int counter that allows the system to check how many times the user has entered the
     * incorrect password. After 3 incorrect attempts, a user will have their account locked.
     */
    private int incorrectPasswordCounter;
    /**
     * Boolean to determine blocked status. True means blocked, false means active.
     */
    private boolean blocked;
    /**
     * Boolean to determine locked status. True means locked, false means active.
     */
    private boolean locked;

    /**
     * Empty base constructor used by Firebase to populate User objects from JSON objects.
     */
    public User() {
        //empty constructor for Firebase
    }

    /**
     * Basic constructor to create a User object.
     *
     * @param pName name of the user
     * @param pUsername username of the user
     * @param pPassword password of the user
     * @param pEmail email of the user
     * @param pSecuHint security hint the user entered
     * @param pMajor major of the user
     * @param pAdmin boolean of whether or not user is admin
     * @param pIncorrectPasswordCounter number of incorrect password attempts, default is 0
     * @param pBlocked boolean of whether or not user is blocked
     * @param pLocked boolean of whether or not user is locked
     */
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

    /**
     * Get the name of the user.
     *
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the user.
     *
     * @param pName name to be set
     */
    public void setName(String pName) {
        this.name = pName;
    }

    /**
     * Get the username of the user.
     *
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param pUsername username of the user
     */
    public void setUsername(String pUsername) {
        this.username = pUsername;
    }

    /**
     * Get the password of the user.
     *
     * @return String password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password for the user.
     *
     * @param pPassword password of the user
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    /**
     * Get the email of the user.
     *
     * @return email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user.
     *
     * @param pEmail email of the user
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    /**
     * Get the security hint for the user.
     *
     * @return security hint of the user
     */
    public String getSecurityHint() {
        return securityHint;
    }

    /**
     * Set the security hint for the user.
     *
     * @param pSecuHint security hint for the user
     */
    public void setSecurityHint(String pSecuHint) {
        this.securityHint = pSecuHint;
    }

    /**
     * Get the official Georgia Tech major of the user.
     *
     * @return major of the user
     */
    public String getMajor() {
        return major;
    }

    /**
     * Set the official Georgia Tech major of the user.
     *
     * @param pMajor major of the user
     */
    public void setMajor(String pMajor) {
        this.major = pMajor;
    }

    /**
     * Get admin status. True means they are an admin, false means they are not.
     *
     * @return boolean of admin status
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Set admin status for the user.
     *
     * @param pAdmin boolean status of true for admin, false for not
     */
    public void setAdmin(boolean pAdmin) {
        this.admin = pAdmin;
    }

    /**
     * Return the number of incorrect password attempts in int format.
     *
     * @return int number of incorrect password guesses
     */
    public int getIncorrectPasswordCounter() {
        return incorrectPasswordCounter;
    }

    /**
     * Set the number of incorrect password attempts. Most typically used by app and system to
     * reset a user to 0 incorrect attempts.
     *
     * @param pIncorrectPasswordCounter number of incorrect attempts to be set to user's counter
     */
    public void setIncorrectPasswordCounter(int pIncorrectPasswordCounter) {
        this.incorrectPasswordCounter = pIncorrectPasswordCounter;
    }

    /**
     * Get the blocked status of a user.
     *
     * @return boolean status, true means blocked, false means active
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Set the blocked status of a user utilizing the boolean parameter. Used by admins to change
     * the blocked status of other users.
     *
     * @param pBlocked boolean, true means blocked, false means active
     */
    public void setBlocked(boolean pBlocked) {
        this.blocked = pBlocked;
    }

    /**
     * Get the locked status of a user.
     *
     * @return boolean status, true means locked, false means active
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Set the locked status of a user utilizing the boolean parameter. Used by admins to change
     * the locked status of other users.
     *
     * @param pLocked boolean, true means locked, false means active
     */
    public void setLocked(boolean pLocked) {
        this.locked = pLocked;
    }
}
