package dk.tangsolutions.courseratingapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dk.tangsolutions.courseratingapplication.Course;
import dk.tangsolutions.courseratingapplication.R;
import dk.tangsolutions.courseratingapplication.Rating;
import dk.tangsolutions.courseratingapplication.services.CourseService;
import dk.tangsolutions.courseratingapplication.services.UserService;

public class RateCourseActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private TextView courseSubject, teacherInfo;
    private SeekBar subRelBar, teachPerformanceBar, teachPrepBar, feedbackBar, goodExampleBar, jobOpportunitiesBar;
    private TextView subRelValue, teachPerformanceValue, teachPrepValue, feedbackValue, goodExampleValue, jobOpportunitiesValue;
//    private TextView subRelLabel, teachPerformanceLabel, teachPrepLabel, feedbackLabel, goodExampleLabel, jobOpportunitiesLabel;

    private Course courseToRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_course);
        init();
        Intent intent = getIntent();
        courseToRate = intent.getParcelableExtra("Course");
        courseSubject.setText(courseToRate.getSubject());
        loadTeacherInfo();


    }

    private void loadTeacherInfo() {
        String text = getString(R.string.teacher_info, courseToRate.getCourseTeacher().getFirstName(), courseToRate.getCourseTeacher().getLastName(), courseToRate.getCourseTeacher().getEmail());
        this.teacherInfo.setText(text);
        this.teacherInfo.setTextSize(18);

    }

    private void init() {
        initSeekBars();
        initValueFields();
        this.courseSubject = findViewById(R.id.courseSubject);
        this.teacherInfo = findViewById(R.id.teacherInfo);

    }


    private void initValueFields() {
        this.subRelValue = findViewById(R.id.subRelValue);
        this.teachPerformanceValue = findViewById(R.id.teachPerformanceValue);
        this.teachPrepValue = findViewById(R.id.teachPrepValue);
        this.feedbackValue = findViewById(R.id.feedbackValue);
        this.goodExampleValue = findViewById(R.id.goodExampleValue);
        this.jobOpportunitiesValue = findViewById(R.id.jobOpportunitiesValue);
    }

    private void initSeekBars() {
        this.subRelBar = findViewById(R.id.subRelBar);
        this.subRelBar.setOnSeekBarChangeListener(this);
        this.teachPerformanceBar = findViewById(R.id.teachPerformanceBar);
        this.teachPerformanceBar.setOnSeekBarChangeListener(this);
        this.teachPrepBar = findViewById(R.id.teachPrepBar);
        this.teachPrepBar.setOnSeekBarChangeListener(this);
        this.feedbackBar = findViewById(R.id.feedbackBar);
        this.feedbackBar.setOnSeekBarChangeListener(this);
        this.goodExampleBar = findViewById(R.id.goodExampleBar);
        this.goodExampleBar.setOnSeekBarChangeListener(this);
        this.jobOpportunitiesBar = findViewById(R.id.jobOpportunitiesBar);
        this.jobOpportunitiesBar.setOnSeekBarChangeListener(this);
    }


    public void rateCourse(View view) {
        Rating rating = new Rating();
        rating.setSubjectRelevans(this.subRelBar.getProgress());
        rating.setTeacherPerformance(this.teachPerformanceBar.getProgress());
        rating.setTeacherPreparation(this.teachPrepBar.getProgress());
        rating.setAmountOfFeedback(this.feedbackBar.getProgress());
        rating.setGoodExamples(this.goodExampleBar.getProgress());
        rating.setJobOpportunities(this.jobOpportunitiesBar.getProgress());


        String currentUser = "";
        String filename = "currentUser";
        File file = new File(getFilesDir(), filename);
        try {
            Scanner sc = new Scanner(file);
            currentUser = sc.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        rating.setStudent(UserService.getStudentByEmail(currentUser));
        courseToRate.getRatings().add(rating);

        CourseService.updateCourse(courseToRate);


        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);

    }

    public void updateSeekBarValue(TextView viewToUpdate, int progress) {
        String text = getString(R.string.seekBarValueStr, progress);
        viewToUpdate.setText(text);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()) {
            case R.id.subRelBar:
                updateSeekBarValue(this.subRelValue, progress);
                break;
            case R.id.teachPerformanceBar:
                updateSeekBarValue(this.teachPerformanceValue, progress);

                break;

            case R.id.teachPrepBar:
                updateSeekBarValue(this.teachPrepValue, progress);
                break;

            case R.id.feedbackBar:
                updateSeekBarValue(this.feedbackValue, progress);
                break;

            case R.id.goodExampleBar:
                updateSeekBarValue(this.goodExampleValue, progress);
                break;

            case R.id.jobOpportunitiesBar:
                updateSeekBarValue(this.jobOpportunitiesValue, progress);
                break;


        }


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
