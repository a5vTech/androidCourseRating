package dk.tangsolutions.courseratingapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import dk.tangsolutions.courseratingapplication.Course;
import dk.tangsolutions.courseratingapplication.R;
import dk.tangsolutions.courseratingapplication.Rating;
import dk.tangsolutions.courseratingapplication.Student;
import dk.tangsolutions.courseratingapplication.services.CourseService;

public class CourseListActivity extends AppCompatActivity {
    private LinearLayout rateLayout;
    private CourseService courseService = new CourseService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        init();


    }

    private void init() {
        this.rateLayout = findViewById(R.id.rateLayout);
        String currentUser = "";
        String filename = "currentUser";
        File file = new File(getFilesDir(), filename);
        try {
            Scanner sc = new Scanner(file);
            currentUser = sc.nextLine();
            Log.d("DEBUG", currentUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Course course : CourseService.getCourses()) {
            Log.d("DEBUGRATING: " , "Course name: "+ course.getSubject() + "\n"+course.getRatings().size());

            if (course.getRatings().size() > 0) {
                for (int i = 0; i < course.getRatings().size(); i++) {
                    if (!course.getRatings().get(i).getStudent().getEmail().equals(currentUser)) {
                        addCourseView(course);
                    }
                }

            } else {
                addCourseView(course);
            }


        }

    }


    public void addCourseView(Course course) {
        TextView textView = new TextView(this);
        textView.setText(course.getSubject());
        textView.setTextSize(25);

        textView.setBackgroundColor(4444);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView coursePressed = (TextView) v;
                Intent rateCourseIntent = new Intent(CourseListActivity.this, RateCourseActivity.class);
                Course courseToRate = new Course();
                for (Course course : CourseService.getCourses()) {
                    if (course.getSubject().equals(coursePressed.getText().toString())) {
                        courseToRate = course;
                    }
                }
                rateCourseIntent.putExtra("Course", courseToRate);
                startActivity(rateCourseIntent);


            }
        });
        rateLayout.addView(textView);
    }
}
