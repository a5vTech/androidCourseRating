package dk.tangsolutions.courseratingapplication.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import dk.tangsolutions.courseratingapplication.Course;
import dk.tangsolutions.courseratingapplication.R;
import dk.tangsolutions.courseratingapplication.Rating;
import dk.tangsolutions.courseratingapplication.services.CourseService;
import dk.tangsolutions.courseratingapplication.services.UserService;

public class RateCourseActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private TextView courseSubject, teacherInfo;
    private SeekBar subRelBar, teachPerformanceBar, teachPrepBar, feedbackBar, goodExampleBar, jobOpportunitiesBar;
    private TextView subRelValue, teachPerformanceValue, teachPrepValue, feedbackValue, goodExampleValue, jobOpportunitiesValue;
    private Course courseToRate;
    private Boolean hasSentMail = false;

    public int getAverageScore() {
        return (subRelBar.getProgress() + teachPerformanceBar.getProgress() + teachPrepBar.getProgress() + feedbackBar.getProgress() + goodExampleBar.getProgress() + jobOpportunitiesBar.getProgress()) / 6;
    }


    public Character getGrade() {
        int score = getAverageScore();

        if (score > 90) {
            return 'A';
        } else if (score > 80) {
            return 'B';
        } else if (score > 70) {
            return 'C';
        } else if (score > 60) {
            return 'D';
        } else if (score > 50) {
            return 'E';
        } else {
            return 'F';
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_course);
        init();
        Intent intent = getIntent();
        courseToRate = intent.getParcelableExtra("Course");

        if (savedInstanceState != null) {
            this.courseToRate = savedInstanceState.getParcelable("courseToRate");
            Rating fethedRating = savedInstanceState.getParcelable("rating");
            this.subRelBar.setProgress(fethedRating.getSubjectRelevans());
            this.teachPerformanceBar.setProgress(fethedRating.getTeacherPerformance());
            this.teachPrepBar.setProgress(fethedRating.getTeacherPreparation());
            this.feedbackBar.setProgress(fethedRating.getAmountOfFeedback());
            this.goodExampleBar.setProgress(fethedRating.getGoodExamples());
            this.jobOpportunitiesBar.setProgress(fethedRating.getJobOpportunities());
        }

        loadTeacherInfo();
        courseSubject.setText(courseToRate.getSubject());


    }

    private void init() {
        initSeekBars();
        initValueFields();
        this.courseSubject = findViewById(R.id.courseSubject);
        this.teacherInfo = findViewById(R.id.teacherInfo);

    }

    private void loadTeacherInfo() {
        String text = getString(R.string.teacher_info, courseToRate.getCourseTeacher().getFirstName(), courseToRate.getCourseTeacher().getLastName(), courseToRate.getCourseTeacher().getEmail());
        this.teacherInfo.setText(text);
        this.teacherInfo.setTextSize(18);
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
        String currentUser = UserService.getCurrentUser(this);

        // Create rating object
        Rating rating = new Rating();
        rating.setSubjectRelevans(this.subRelBar.getProgress());
        rating.setTeacherPerformance(this.teachPerformanceBar.getProgress());
        rating.setTeacherPreparation(this.teachPrepBar.getProgress());
        rating.setAmountOfFeedback(this.feedbackBar.getProgress());
        rating.setGoodExamples(this.goodExampleBar.getProgress());
        rating.setJobOpportunities(this.jobOpportunitiesBar.getProgress());
        rating.setStudent(UserService.getStudentByEmail(currentUser));
        // Add rating to the course
        courseToRate.getRatings().add(rating);
        // Update course in courseService
        CourseService.updateCourse(courseToRate);

        //Send mail to teacher
        Intent intent = new Intent(getApplicationContext(), CourseListActivity.class);
        startActivity(intent);
        sendMail(courseToRate);


    }


    @SuppressLint("NewApi")
    private void sendMail(Course courseToRate) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.recipient)});
        email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_1, courseToRate.getSubject()));
        email.putExtra(Intent.EXTRA_TEXT,
                getString(R.string.mail_2, courseToRate.getSubject())
                        + getString(R.string.mail_3, getAverageScore()) + "\n\n"
                        + getString(R.string.mail_4, getGrade()));


        email.setType("text/html");

        startActivity(Intent.createChooser(email, getString(R.string.mail_client)));
        this.hasSentMail = true;

    }

    public void updateSeekBarValue(TextView viewToUpdate, int progress) {
        String text = getString(R.string.seekBarValueStr, progress);
        viewToUpdate.setText(text);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Rating rating = new Rating();
        rating.setSubjectRelevans(this.subRelBar.getProgress());
        rating.setTeacherPerformance(this.teachPerformanceBar.getProgress());
        rating.setTeacherPreparation(this.teachPrepBar.getProgress());
        rating.setAmountOfFeedback(this.feedbackBar.getProgress());
        rating.setGoodExamples(this.goodExampleBar.getProgress());
        rating.setJobOpportunities(this.jobOpportunitiesBar.getProgress());

        outState.putParcelable("rating", rating);
        outState.putParcelable("courseToRate", courseToRate);
    }


    /**
     * This method updates the seekBar textview values
     *
     * @param seekBar  seekBar
     * @param progress Progress
     * @param fromUser fromUser
     */
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
