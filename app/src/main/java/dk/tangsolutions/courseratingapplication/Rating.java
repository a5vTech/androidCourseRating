package dk.tangsolutions.courseratingapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {
    private int subjectRelevans = 0;
    private int teacherPerformance = 0;
    private int teacherPreparation = 0;
    private int amountOfFeedback = 0;
    private int goodExamples = 0;
    private int jobOpportunities = 0;
    private Student student;


    public Rating() {
    }


    protected Rating(Parcel in) {
        subjectRelevans = in.readInt();
        teacherPerformance = in.readInt();
        teacherPreparation = in.readInt();
        amountOfFeedback = in.readInt();
        goodExamples = in.readInt();
        jobOpportunities = in.readInt();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public int getSubjectRelevans() {
        return subjectRelevans;
    }

    public void setSubjectRelevans(int subjectRelevans) {
        this.subjectRelevans = subjectRelevans;
    }

    public int getTeacherPerformance() {
        return teacherPerformance;
    }

    public void setTeacherPerformance(int teacherPerformance) {
        this.teacherPerformance = teacherPerformance;
    }

    public int getAmountOfFeedback() {
        return amountOfFeedback;
    }

    public void setAmountOfFeedback(int amountOfFeedback) {
        this.amountOfFeedback = amountOfFeedback;
    }

    public int getGoodExamples() {
        return goodExamples;
    }

    public void setGoodExamples(int goodExamples) {
        this.goodExamples = goodExamples;
    }

    public int getJobOpportunities() {
        return jobOpportunities;
    }

    public void setJobOpportunities(int jobOpportunities) {
        this.jobOpportunities = jobOpportunities;
    }

    public int getTeacherPreparation() {
        return teacherPreparation;
    }

    public void setTeacherPreparation(int teacherPreparation) {
        this.teacherPreparation = teacherPreparation;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subjectRelevans);
        dest.writeInt(teacherPerformance);
        dest.writeInt(teacherPreparation);
        dest.writeInt(amountOfFeedback);
        dest.writeInt(goodExamples);
        dest.writeInt(jobOpportunities);
    }
}
