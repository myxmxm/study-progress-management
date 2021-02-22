package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class CourseDetailsActivity extends AppCompatActivity {

    private EditText courseName, courseSemester, courseGrade, courseCredit, courseExtraInfo ;
    private Button buttonUpdateCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        initialization();

        getCourseInfo ();




    }

    private void initialization () {
        courseName = (EditText) findViewById(R.id.courseName);
        courseSemester = (EditText) findViewById(R.id.courseSemester);
        courseGrade = (EditText) findViewById(R.id.courseGrade);
        courseCredit = (EditText) findViewById(R.id.courseCredit);
        courseExtraInfo = (EditText) findViewById(R.id.courseExtraInfo);

    }

    private void getCourseInfo () {
        Collection collection = Collection.getInstance();

        Intent intent = getIntent();
        int indexOfCourse = intent.getIntExtra(CourseListActivity.KEY,0);

        int semester = collection.getCourse(indexOfCourse).getSemester();
        int grade = collection.getCourse(indexOfCourse).getGrade();
        int credit = collection.getCourse(indexOfCourse).getCredit();

        courseName.setText(collection.getCourse(indexOfCourse).getCourseName());
        courseSemester.setText(String.valueOf(semester));
        courseGrade.setText(String.valueOf(grade));
        courseCredit.setText(String.valueOf(credit));
        courseExtraInfo.setText(collection.getCourse(indexOfCourse).getCourseDetail());

    }
}