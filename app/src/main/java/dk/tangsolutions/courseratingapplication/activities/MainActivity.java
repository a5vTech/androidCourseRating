package dk.tangsolutions.courseratingapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.security.PrivateKey;

import dk.tangsolutions.courseratingapplication.R;
import dk.tangsolutions.courseratingapplication.services.CourseService;
import dk.tangsolutions.courseratingapplication.services.UserService;

public class MainActivity extends AppCompatActivity {
    private UserService userService = new UserService();
    private CourseService courseService = new CourseService();
    private ConstraintLayout loginLayout;
    private EditText username, password;
    private CheckBox cb_remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // Handle screen orientation changes
        if (savedInstanceState != null) {
            this.username.setText(savedInstanceState.getString("email"));
            this.password.setText(savedInstanceState.getString("password"));
        }


    }


    /**
     * Initialize views to java code from xml
     * and loads in users
     */
    private void init() {
        this.loginLayout = findViewById(R.id.loginLayout);
        this.password = findViewById(R.id.password);
        this.username = findViewById(R.id.userName);
        this.cb_remember = findViewById(R.id.cb_remember);
        userService.loadStudents();
        userService.loadTeachers();
        courseService.loadCourses();
        loadSharedPrefData();

    }

    private void loadSharedPrefData() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        String username = preferences.getString("email", null);
        String password = preferences.getString("password", null);
        boolean remember = preferences.getBoolean("remember", false);
        if (username != null) {
            this.username.setText(username);
            this.password.setText(password);
            this.cb_remember.setChecked(remember);
        }


    }

    public void login(View view) {
        String email = username.getText().toString();
        if (userService.validateEmail(email)) {
            if (email.endsWith("@kea.dk")) {
                if (userService.login(email, password.getText().toString())) {
                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_pref), MODE_PRIVATE).edit();
                    String user = username.getText().toString();
                    editor.putBoolean("remember", this.cb_remember.isChecked());
                    editor.putString("email", user);
                    editor.putString("current_user", user);
                    editor.putString("password", password.getText().toString());

                    if (!this.cb_remember.isChecked()) {
                        editor.putString("email", null);
                        editor.putString("password", null);
                    }
                    editor.apply();

                    Intent intent = new Intent(this, CourseListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.use_kea_mail_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.valid_email_error), Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * This method handles changes in screen orientation
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", username.getText().toString());
        outState.putString("password", password.getText().toString());
    }
}
