package fi.metropolia.team4studyprogressmanagement;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CourseDao {
    @Insert
    void insertCourse(Course... courses);

    @Update
    void updateCourse(Course course);

    @Delete
    void  deleteCourse(Course course);

    @Query("DELETE FROM Course")
    void deleteAllCourse();

    @Query("SELECT * FROM Course ORDER BY id ASC")
    List<Course> getAllCourses();

    @Query("SELECT course_name  FROM Course WHERE id = (:uid)  "  )
    String getCourseNameById(int uid);

    @Query("SELECT course_detail  FROM Course WHERE id = (:uid)  "  )
    String getCourseDetailById(int uid);

    @Query("SELECT course_semester  FROM Course WHERE id = (:uid)  "  )
    int getCourseSemesterById(int uid);

    @Query("SELECT course_grade  FROM Course WHERE id = (:uid)  "  )
    int getCourseGradeById(int uid);

    @Query("SELECT course_credit  FROM Course WHERE id = (:uid)  "  )
    int getCourseCreditById(int uid);

    @Query("SELECT SUM(course_credit) FROM Course")
    int getTotalCredits();

    @Query("SELECT SUM(course_grade) FROM Course")
    int getTotalGrade();

    @Query("SELECT SUM(course_credit) FROM Course WHERE course_semester = (:semester)")
    int getTotalCreditsBySemester(int semester);

    @Query("SELECT course_name FROM Course WHERE course_semester = (:semester)")
    List<String> getCourseNameBySemester(int semester);

    @Query("SELECT course_name FROM Course WHERE course_grade = (:grade)")
    List<String> getCourseNameByGrade(int grade);

    @Query("SELECT course_name FROM Course WHERE course_credit = (:credit)")
    List<String> getCourseNameByCredit(int credit);

    @Query("SELECT id FROM Course WHERE course_name = (:CourseName)")
    int getCourseIdByCourseName(String CourseName);

    @Query("SELECT course_name FROM Course")
    List<String> getAllCourseName();

    @Query("SELECT course_credit FROM Course WHERE course_name = (:CourseName)")
    int getCreditByCourseName(String CourseName);

    @Query("SELECT course_semester FROM Course")
    List<Integer> getEverySemesterValue();


}
