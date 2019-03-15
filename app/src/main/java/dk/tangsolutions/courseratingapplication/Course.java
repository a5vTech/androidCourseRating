package dk.tangsolutions.courseratingapplication;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Course implements Parcelable {
    private Teacher courseTeacher;
    private String subject;
    private ArrayList<Rating> ratings = new ArrayList<>();

    public Course(String subject) {
        this.subject = subject;
    }

    public Course() {
    }

    public Course(Teacher courseTeacher, String subject) {
        this.courseTeacher = courseTeacher;
        this.subject = subject;
    }

    protected Course(Parcel in) {
        courseTeacher = in.readParcelable(Teacher.class.getClassLoader());
        subject = in.readString();
        ratings = in.createTypedArrayList(Rating.CREATOR);
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(courseTeacher, flags);
        dest.writeString(subject);
        dest.writeTypedList(ratings);
    }


    public Teacher getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(Teacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }
}
