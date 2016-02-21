package edu.gatech.snickers.techflixandchill;

/**
 * Created by nirajsuresh on 2/21/16.
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String securityHint;
    private String major;

    public User(String username, String password, String name, String email, String securityHint, String major) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.securityHint = securityHint;
        this.major = major;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityHint() {
        return securityHint;
    }

    public String getMajor() {
        return major;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurityHint(String securityHint) {
        this.securityHint = securityHint;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
