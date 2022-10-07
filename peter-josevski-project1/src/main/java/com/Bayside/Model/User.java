package com.Bayside.Model;

import java.util.Objects;

public class User {

    private String username;
    private String password;
    private  String role;



    public User() {

    }

    public User( String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String toString() {
        return   "Username: " + username + ' ' +
                "Password: " + password + ' ' + "Role: " + role + " ";

    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return role == user.role && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }


    public int hashCode() {
        return Objects.hash(username, password, role);
    }


}
