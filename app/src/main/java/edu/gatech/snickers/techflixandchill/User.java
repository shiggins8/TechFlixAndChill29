package edu.gatech.snickers.techflixandchill;

/**
 * Created by Scottie on 2/7/16.
 *
 * Basic creation of a user.
 */
public class User {
    String name, username, password;
    int age;

    public User (String name, int age, String username, String password) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.age = 21;
        this.name = "John Doe";
    }
}