package dk.tangsolutions.courseratingapplication.activities;

import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import dk.tangsolutions.courseratingapplication.Adapter;
import dk.tangsolutions.courseratingapplication.Course;
import dk.tangsolutions.courseratingapplication.R;
import dk.tangsolutions.courseratingapplication.Rating;
import dk.tangsolutions.courseratingapplication.Student;
import dk.tangsolutions.courseratingapplication.services.CourseService;
import dk.tangsolutions.courseratingapplication.services.UserService;

public class CourseListActivity extends AppCompatActivity {
    //    private LinearLayout rateLayout;
    private CourseService courseService = new CourseService();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        init();
//        Window w = getWindow();
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


    }

    private void init() {
        this.recyclerView = findViewById(R.id.rv_list);
        Set<Course> unAnsweredCourses = new HashSet<>();
        ArrayList<Course> allCourses = CourseService.getCourses();
        for (Course course : allCourses) {
            if (course.getRatings().size() > 0) {
                for (int i = 0; i < course.getRatings().size(); i++) {
                    if (!course.getRatings().get(i).getStudent().getEmail().equals(UserService.getCurrentUser(this))) {
                        unAnsweredCourses.add(course);
                    }
                }
            } else {
                unAnsweredCourses.add(course);
            }
        }


        Adapter adapter = new Adapter(this, new ArrayList<>(unAnsweredCourses));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
