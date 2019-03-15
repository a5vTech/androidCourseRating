package dk.tangsolutions.courseratingapplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Student {
    private String firstName;
    private String lastName;
    private String cpr;
    private String email;
    private String password;


    public Student() {
    }

    public Student(String firstName, String lastName, String cpr, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        this.email = email;
        this.password = password;
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

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
