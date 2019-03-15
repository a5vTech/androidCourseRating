package dk.tangsolutions.courseratingapplication.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import dk.tangsolutions.courseratingapplication.R;
import dk.tangsolutions.courseratingapplication.services.CourseService;
import dk.tangsolutions.courseratingapplication.services.UserService;

public class MainActivity extends AppCompatActivity {
    private UserService userService = new UserService();
    private CourseService courseService = new CourseService();
    private ConstraintLayout loginLayout;
    private TextView title;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    /**
     * Initialize views to java code from xml
     */
    private void init() {
        this.loginLayout = findViewById(R.id.loginLayout);
        this.password = findViewById(R.id.password);
        this.username = findViewById(R.id.userName);
        userService.loadStudents();
        userService.loadTeachers();
        courseService.loadCourses();


    }

    public void login(View view) {
        if (userService.login(username.getText().toString(), password.getText().toString())) {
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this,"Username or password is incorrect",Toast.LENGTH_LONG).show();
        }

        FileOutputStream outputStream;
        String filename = "currentUser";
//        File file = new File(getFilesDir(), filename);
        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE);
            outputStream.write(username.getText().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
