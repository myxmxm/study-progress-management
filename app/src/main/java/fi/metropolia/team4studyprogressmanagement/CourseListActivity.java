package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * From this activity, user are able to edit their course list, such as insert new course
 * or remove all courses by on click. all course will be display as singleton listView
 * when they click one list item, they will be taken to CourseDetailActivity to view more
 * detailed information about specific course they have chosen.
 */

public class CourseListActivity extends AppCompatActivity {

    public static final String KEY = "index";
    private Button insertBtn, demoBtn, clearBtn, okInputBtn, cancelInputBtn;
    private EditText inputCourseName, inputCourseSemester, inputCourseGrade, inputCourseCredit, inputCourseDetails;
    private ListView listView;
    private AlertDialog insertDialog;
    private View insertView;
    private LayoutInflater layoutInflater;
    private CourseDatabase courseDatabase;
    private CourseDao courseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        initialization();
        updateList();

        insertBtn.setOnClickListener(insertCourse);

        clearBtn.setOnClickListener(clearCourses);

        demoBtn.setOnClickListener(demoCourse);


    }
//update the course info when switch back to this activity if that course info has been edited
// from CourseListActivity by user
    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }
//Initialize widgets and build database access
    void initialization(){
        insertBtn = (Button) findViewById(R.id.insertBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        demoBtn = (Button) findViewById(R.id.demoBtn);

        courseDatabase = CourseDatabase.getInstance(this);
        courseDao = courseDatabase.getCourseDao();
    }

    void updateList(){

        listView = (ListView) findViewById(R.id.courseList);
//setup singleton listView
        listView.setAdapter(new ArrayAdapter<Course>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                courseDao.getAllCourses()
        ));

//this part actually gave me some trouble, because the list index number is no longer useful since
//all data are save on database, I need to pass either course ID or course name to CourseDetailsActivity
//to query other corresponding information, finally, I figured out how to pass whole list content to
//CourseDetailsActivity.

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//get the list content of list item which has been click by user
                Course course = (Course) listView.getItemAtPosition(position);
                Intent intent = new Intent(CourseListActivity.this, CourseDetailsActivity.class);
//pass list content(include course ID and course name) to CourseDetailsActivity, from there the course
//ID will be extracted, and query other corresponding course information by using that course ID
                intent.putExtra(KEY,String.valueOf(course));
                startActivity(intent);
            }
        });
    }
//create insert AlertDialog
    private void insertDialog(){
//create the layout for this AlertDialog
        layoutInflater = (LayoutInflater) LayoutInflater.from(this);
        insertView = (View) layoutInflater.inflate(R.layout.activity_course_detials_insert_dialog, null);
//build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert new course");
        builder.setView(insertView);
        insertDialog = builder.create();
        insertDialog.show();
//initialize widgets and two buttons on this AlertDialog and set onClickListener for them
        okInputBtn = (Button) insertView.findViewById(R.id.okInputBtn);
        cancelInputBtn = (Button) insertView.findViewById(R.id.cancelInputBtn);
        inputCourseName = (EditText) insertView.findViewById(R.id.inputCourseName);
        inputCourseSemester = (EditText) insertView.findViewById(R.id.inputCourseSemester);
        inputCourseGrade = (EditText) insertView.findViewById(R.id.inputCourseGrade);
        inputCourseCredit = (EditText) insertView.findViewById(R.id.inputCourseCredit);
        inputCourseDetails = (EditText) insertView.findViewById(R.id.inputCourseDetails);

        okInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//get the user input
                String newCourseName = inputCourseName.getText().toString();
                int newSemester = Integer.parseInt(inputCourseSemester.getText().toString());
                int newGrade = Integer.parseInt(inputCourseGrade.getText().toString());
                int newCredit = Integer.parseInt(inputCourseCredit.getText().toString());
                String newCourseDetail = inputCourseDetails.getText().toString();
//create Course instance, and set user input as parameters
                Course newCourse = new Course(newCourseName,newSemester,newGrade,newCredit,newCourseDetail);
                courseDao.insertCourse(newCourse);
                showToast("New course has been added!");
                updateList();
                insertDialog.dismiss();
            }
        });

        cancelInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDialog.dismiss();
            }
        });
    }

    private View.OnClickListener insertCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            insertDialog();

        }
    };

    private View.OnClickListener clearCourses = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            courseDao.deleteAllCourse();
            showToast("All courses have been removed!");
            updateList();
        }
    };
//create demonstration button for the purpose of video recording of this app, so I could quickly
//add dozen of course information by one click instead of taking 10 mins to insert them one by one
    private View.OnClickListener demoCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Course math1= new Course("Math(first period)",1,4,3, "very hard");
            Course physics1 = new Course("Physics(first period)",1,3,3, "very hard");
            Course java1 = new Course("Java(first period)",1,4,10, "very hard");
            Course finnish1 = new Course("Finnish(first period)",1,1,5, "very hard");
            Course english = new Course("English(first period)",1,2,5, "very hard");
            Course math2 = new Course("Math(second period)",2,2,3, "very hard");
            Course physics2 = new Course("Physics(second period)",2,1,3, "very hard");
            Course finnish2 = new Course("Finnish(second period)",3,5,10, "very hard");
            Course linux = new Course("Linux",3,5,10, "very hard");
            Course python = new Course("Python",4,5,10, "very hard");
            Course workPlacement1 = new Course("Work placement(first)",4,4,15, "very hard");

            courseDao.insertCourse(math1,physics1,java1,finnish1,english,math2,physics2,finnish2,linux,python,workPlacement1);
            updateList();
        }
    };
//method for using toast message more conveniently
    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT);
        toast.show();
    }
}