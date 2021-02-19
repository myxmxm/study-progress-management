package fi.metropolia.team4studyprogressmanagement;

public class Course {

    private String courseName, courseDetail;
    private int grade, credit, semester;


    public Course(String courseName, int grade, int credit, int semester, String courseDetail) {
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
        return this.courseName + " semester " + this.semester;
    }
}
