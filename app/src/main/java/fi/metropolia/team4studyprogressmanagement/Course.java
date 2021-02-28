package fi.metropolia.team4studyprogressmanagement;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Course {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "course_detail")
    private String courseDetail;

    @ColumnInfo(name = "course_semester")
    private int semester;

    @ColumnInfo(name = "course_grade")
    private int grade;

    @ColumnInfo(name = "course_credit")
    private int credit;


    public Course(String courseName, int semester, int grade, int credit, String courseDetail) {
        this.courseName = courseName;
        this.courseDetail = courseDetail;
        this.grade = grade;
        this.credit = credit;
        this.semester = semester;
    }



    public String getCourseName() {
        return courseName;
    }

    public String getCourseDetail() {
        return courseDetail;
    }

    public int getGrade() {
        return grade;
    }

    public int getCredit() {
        return credit;
    }

    public int getSemester() {
        return semester;
    }

    @Override
    public String toString() {
        return "Course ID: " + id + ", Courser name: " + this.courseName ;
    }
}
