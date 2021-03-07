package fi.metropolia.team4studyprogressmanagement;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * create entity class for room database
 */

@Entity
public class Course {
//set auto generated course id as primary key
    @PrimaryKey (autoGenerate = true)
    private int id;
//set course name as column
    @ColumnInfo(name = "course_name")
    private String courseName;
//set course detail as column
    @ColumnInfo(name = "course_detail")
    private String courseDetail;
//set semester as column
    @ColumnInfo(name = "course_semester")
    private int semester;
//set grade as column
    @ColumnInfo(name = "course_grade")
    private int grade;
//set credit as column
    @ColumnInfo(name = "course_credit")
    private int credit;
//create constructor
    public Course(String courseName, int semester, int grade, int credit,String courseDetail) {
        this.courseName = courseName;
        this.courseDetail = courseDetail;
        this.semester = semester;
        this.grade = grade;
        this.credit = credit;
    }
// create set and get methods
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
//create toString method
    @Override
    public String toString() {
        return "ID: " + id + ", " + this.courseName ;
    }
}
