package dk.tangsolutions.courseratingapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Teacher implements Parcelable {
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private Date birthday;
    private String role = "Teacher";


    public Teacher() {
    }

    public Teacher(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }

    protected Teacher(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        role = in.readString();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(role);
    }
}
