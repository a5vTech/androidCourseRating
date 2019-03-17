package dk.tangsolutions.courseratingapplication.services;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import dk.tangsolutions.courseratingapplication.Course;
import dk.tangsolutions.courseratingapplication.Rating;
import dk.tangsolutions.courseratingapplication.Student;

public class CourseService {
    private static ArrayList<Course> courses = new ArrayList<>();

    public CourseService() {
    }

    public static void updateCourse(Course courseToRate) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getSubject().equals(courseToRate.getSubject())) {
                courses.set(i, courseToRate);
            }
        }


    }


    public void loadCourses() {
        ArrayList<Course> courseSet = new ArrayList<>();
        courseSet.add(new Course(UserService.getTeachers().get(0), "Android App"));
        courseSet.add(new Course(UserService.getTeachers().get(1), "Angular"));
        courseSet.add(new Course(UserService.getTeachers().get(2), "Python"));
        courseSet.add(new Course(UserService.getTeachers().get(3), "Python 2"));
        courses = courseSet;
    }


    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static void setCourses(ArrayList<Course> courses) {
        CourseService.courses = courses;
    }
}
