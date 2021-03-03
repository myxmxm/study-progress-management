package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CourseDetailsActivity extends AppCompatActivity {

    private EditText courseName, courseSemester, courseGrade, courseCredit, courseDetail ;
    private Button buttonUpdateCourse, buttonDeleteCourse;
    private TextView courseId;
    private CourseDatabase courseDatabase;
    private CourseDao courseDao;
    int listCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);


        courseDatabase = CourseDatabase.getInstance(this);
        courseDao = courseDatabase.getCourseDao();

        initialization();
        getCourseInfo ();

        buttonUpdateCourse.setOnClickListener(updateCourse);
        buttonDeleteCourse.setOnClickListener(deleteCourse);

    }

    private void initialization () {
        courseName = (EditText) findViewById(R.id.courseName);
        courseSemester = (EditText) findViewById(R.id.courseSemester);
        courseGrade = (EditText) findViewById(R.id.courseGrade);
        courseCredit = (EditText) findViewById(R.id.courseCredit);
        courseDetail = (EditText) findViewById(R.id.courseExtraInfo);
        courseId = (TextView) findViewById(R.id.courseId);
        buttonUpdateCourse = (Button) findViewById(R.id.buttonUpdateCourse);
        buttonDeleteCourse = (Button) findViewById(R.id.buttonDeleteCourse);
    }

    private void getCourseInfo () {
        Intent intent = getIntent();
        String listContent = intent.getStringExtra(CourseListActivity.KEY);

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(listContent);

        listCourseId = Integer.parseInt(m.replaceAll("").trim());
        String listCourseName = courseDao.getCourseNameById(listCourseId);
        String listCourseDetail = courseDao.getCourseDetailById(listCourseId);
        int semester = courseDao.getCourseSemesterById(listCourseId);
        int grade = courseDao.getCourseGradeById(listCourseId);
        int credit = courseDao.getCourseCreditById(listCourseId);

        courseName.setText(listCourseName);
        courseSemester.setText(String.valueOf(semester));
        courseGrade.setText(String.valueOf(grade));
        courseCredit.setText(String.valueOf(credit));
        courseDetail.setText(listCourseDetail);
        courseId.setText(String.valueOf(listCourseId));
    }

    private View.OnClickListener updateCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String newCourseName = courseName.getText().toString();
            String newCourseDetail = courseDetail.getText().toString();
            int newSemester = Integer.parseInt(courseSemester.getText().toString());
            int newGrade = Integer.parseInt(courseGrade.getText().toString());
            int newCredit = Integer.parseInt(courseCredit.getText().toString());

            Course course = new Course(newCourseName,newSemester,newGrade,newCredit,newCourseDetail);
            course.setId(listCourseId);
            courseDao.updateCourse(course);
            showToast("Update succeeded!");
            //CourseDetailsActivity.this.finish();
        }
    };

    private View.OnClickListener deleteCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Course course = new Course("",0,0,0,"");
            course.setId(listCourseId);
            courseDao.deleteCourse(course);
            showToast("This course has been deleted!");
            CourseDetailsActivity.this.finish();
        }
    };

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT);
        toast.show();
    }


}