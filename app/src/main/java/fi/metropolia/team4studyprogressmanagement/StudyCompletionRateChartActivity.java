package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows a pie chart about user's study progress, which indicates how many
 * percent dose each course account for the total program study, and it also shows how
 * many percent of study has not yet completed.
 *
 */

public class StudyCompletionRateChartActivity extends AppCompatActivity {

    private CourseDatabase courseDatabase;
    private CourseDao courseDao;
    int totalCredits;
    private AnyChartView studyCompletionRateChart;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_completion_rate_chart);

        initialization();

        studyCompletionRateChart();
    }

//initialize widgets and build database access.
    private void initialization(){
        studyCompletionRateChart = (AnyChartView) findViewById(R.id.studyCompletionRateChart);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//set progress bar for pie chart
        studyCompletionRateChart.setProgressBar(progressBar);

        courseDatabase = CourseDatabase.getInstance(this);
        courseDao = courseDatabase.getCourseDao();
    }



    private void studyCompletionRateChart() {
//get total credits of user's study program which inputted by uses during registration process.
        SharedPreferences preGet = getSharedPreferences("USER_DATE", MODE_PRIVATE);
        totalCredits = Integer.parseInt(preGet.getString(LogInActivity.KEY_CREDITS, ""));

        List<DataEntry> dataEntries = new ArrayList<>();
//put all course name to the list course
        List<String> course = courseDao.getAllCourseName();
//add uncompleted study to the list dataEntries
        int uncompletedCredits = totalCredits - courseDao.getTotalCredits();
        dataEntries.add(new ValueDataEntry("Uncompleted studies", uncompletedCredits));
//create a pie chart instance
        Pie pie = AnyChart.pie();
//put every name and  its corresponding credit to the list dataEntries
        for (int i = 0; i < course.size(); i++) {
            dataEntries.add(new ValueDataEntry(course.get(i), courseDao.getCreditByCourseName(course.get(i))));
        }
//pie chart will get data from the list dataEntries
        pie.data(dataEntries);
//set animation effect
        pie.animation(true);
//set title for pie chart
        pie.title("Study completion rate");
        studyCompletionRateChart.setChart(pie);

    }
}