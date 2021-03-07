package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows a column chart about user's total credits in each semester
 * and an overall credits.
 */

public class CreditsPerSemesterChartActivity extends AppCompatActivity {

    private CourseDatabase courseDatabase;
    private CourseDao courseDao;
    private AnyChartView creditsPerSemesterChart;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_per_semester_chart);

        initialization();

        creditsPerSemesterChart();
    }
//initialize widgets and build database access.
    private void initialization(){
        creditsPerSemesterChart = (AnyChartView) findViewById(R.id.creditsPerSemesterChart);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        creditsPerSemesterChart.setProgressBar(progressBar);

        courseDatabase = CourseDatabase.getInstance(this);
        courseDao = courseDatabase.getCourseDao();
    }

    private void creditsPerSemesterChart() {

//get the number, which indicates how many semester user has already spent in this study program.
        List<Integer> semesterValues = courseDao.getEverySemesterValue();
        int maxSemesterValue = semesterValues.get(0);

        for(int i=0; i<semesterValues.size(); i++) {
            if(semesterValues.get(i) > maxSemesterValue) {
                maxSemesterValue = semesterValues.get(i);
            }
        }
//get the list of total credits per semester

        List<Integer> credits = new ArrayList<>();
        for (int i = 0; i < maxSemesterValue;i++) {
            credits.add(courseDao.getTotalCreditsBySemester(i+1));
        }
//create column chart instance
        Cartesian cartesian = AnyChart.column();
//create dataEntries list for add data to column chart.
        List<DataEntry> dataEntries = new ArrayList<>();
//add each semester's GPA to DataEntry list
        for (int i = 0; i < maxSemesterValue; i++) {
            dataEntries.add(new ValueDataEntry(i + 1,credits.get(i)));
        }
//add overall number of credits to DataEntry list
        dataEntries.add(new ValueDataEntry("Overall", courseDao.getTotalCredits()));

        Column column = cartesian.column(dataEntries);
//set layout of column
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Semester");
        cartesian.yAxis(0).title("Credit");
//allow using animation effect
        cartesian.animation(true);
//set column title
        cartesian.title("Credits per semester");
        creditsPerSemesterChart.setChart(cartesian);
    }
}