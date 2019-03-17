package dk.tangsolutions.courseratingapplication.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import dk.tangsolutions.courseratingapplication.Student;
import dk.tangsolutions.courseratingapplication.Teacher;

public class UserService {
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Teacher> teachers = new ArrayList<>();


    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String SHARED_PREFS = "SHAREDPREFS";
    private static final String LOGINSTATUS = "loggedIn";

    public UserService() {
    }

    public static Student getStudentByEmail(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }


        return null;
    }


    public ArrayList<Student> loadStudents() {
        for (int i = 0; i < 10; i++) {
            students.add(new Student("jesper" + i, "petersen" + i, "11111111" + i, "mail@mail.com" + 1, "" + i));
        }
        return students;
    }

    public ArrayList<Teacher> loadTeachers() {
        for (int i = 0; i < 10; i++) {
            teachers.add(new Teacher("Faisal" + i, "mail@kea.dk"));
        }
        return teachers;
    }


    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static void setStudents(ArrayList<Student> students) {
        UserService.students = students;
    }

    public void validateLogin(String username, String password) {


    }

    public static ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public static void setTeachers(ArrayList<Teacher> teachers) {
        UserService.teachers = teachers;
    }

    public boolean login(String username, String password) {
        for (Student student : students) {
            if (student.getEmail().equals(username) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


    public static String getCurrentUser(Context context) {
        String filename = "currentUser";
        File file = new File(context.getFilesDir(), filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            return sc.nextLine();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error, user not signed in?", Toast.LENGTH_LONG).show();
            return "";
        }

    }


}
