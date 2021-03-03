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

    public Course(String courseName, int semester, int grade, int credit,String courseDetail) {
        this.courseName = courseName;
        this.courseDetail = courseDetail;
        this.semester = semester;
        this.grade = grade;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(String courseDetail) {
        this.courseDetail = courseDetail;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + this.courseName ;
    }
}
