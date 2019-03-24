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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.tangsolutions.courseratingapplication.R;
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
            students.add(new Student("jesper" + i, "petersen" + i, "11111111" + i, i+"mail@kea.dk", "" + i));
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
//        String filename = "currentUser";
//        File file = new File(context.getFilesDir(), filename);
//        Scanner sc = null;
//        try {
//            sc = new Scanner(file);
//            return sc.nextLine();
//        } catch (FileNotFoundException e) {
//            Toast.makeText(context, "Error, user not signed in?", Toast.LENGTH_LONG).show();
//            return "";
//        }

        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_pref), Context.MODE_PRIVATE);
        String currentUser = preferences.getString("current_user", null);
        if (currentUser != null) {
            return currentUser;
        } else {
            return "";
        }


    }

    public boolean validateEmail(String email) {
        final String EMAIL_PATTERN= "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-]+)*@[A-Za-z0-9]+(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
