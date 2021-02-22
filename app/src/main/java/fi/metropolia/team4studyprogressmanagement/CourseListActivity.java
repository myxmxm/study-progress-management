package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CourseListActivity extends AppCompatActivity {

    public static final String KEY = "index";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        listView = (ListView) findViewById(R.id.courseList);
        Collection collection = Collection.getInstance();

        listView.setAdapter(new ArrayAdapter<Course>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                collection.getAllCourse()
        ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CourseListActivity.this, CourseDetailsActivity.class);
                intent.putExtra(KEY,i);
                startActivity(intent);
            }
        });
    }
}