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

        courseDatabase = CourseDatabase.getInstance(this);
        courseDao = courseDatabase.getCourseDao();

        initialization();
        updateList();

        insertBtn.setOnClickListener(insertCourse);

        clearBtn.setOnClickListener(clearCourses);

        demoBtn.setOnClickListener(demoCourse);


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    void initialization(){
        insertBtn = (Button) findViewById(R.id.insertBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        demoBtn = (Button) findViewById(R.id.demoBtn);
    }

    void updateList(){
        listView = (ListView) findViewById(R.id.courseList);

        listView.setAdapter(new ArrayAdapter<Course>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                courseDao.getAllCourses()
        ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Course course = (Course) listView.getItemAtPosition(position);
                Intent intent = new Intent(CourseListActivity.this, CourseDetailsActivity.class);
                intent.putExtra(KEY,String.valueOf(course));
                startActivity(intent);
            }
        });
    }

    private void insertDialog(){

        layoutInflater = (LayoutInflater) LayoutInflater.from(this);
        insertView = (View) layoutInflater.inflate(R.layout.activity_course_detials_insert_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert new course");
        builder.setView(insertView);
        insertDialog = builder.create();
        insertDialog.show();

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
                String newCourseName = inputCourseName.getText().toString();
                int newSemester = Integer.parseInt(inputCourseSemester.getText().toString());
                int newGrade = Integer.parseInt(inputCourseGrade.getText().toString());
                int newCredit = Integer.parseInt(inputCourseCredit.getText().toString());
                String newCourseDetail = inputCourseDetails.getText().toString();

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

    private View.OnClickListener demoCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Course math1= new Course("Math(first period)",1,4,3, "very hard");
            Course physics1 = new Course("Physics(first period)",1,3,3, "very hard");
            Course java1 = new Course("Java(first period)",1,4,10, "very hard");
            Course finnish1 = new Course("Finnish(first period)",1,1,5, "very hard");
            Course english = new Course("English(first period)",1,2,5, "very hard");
            Course math2 = new Course("Math(second period)",2,3,3, "very hard");
            Course physics2 = new Course("Physics(second period)",2,5,3, "very hard");
            Course finnish2 = new Course("Finnish(second period)",2,5,10, "very hard");
            Course linux = new Course("Linux",1,2,10, "very hard");
            Course python = new Course("Python",1,5,10, "very hard");
            Course workPlacement1 = new Course("Work placement(first)",4,4,15, "very hard");
            courseDao.insertCourse(math1,physics1,java1,finnish1,english,math2,physics2,finnish2,linux,python,workPlacement1);
            updateList();
        }
    };

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT);
        toast.show();
    }
}