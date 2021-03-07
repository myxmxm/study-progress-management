package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Matrix;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows a column chart about user's GPA in each semester and the overall GPA.
 */

public class GpaPerSemesterChartActivity extends AppCompatActivity {
    private CourseDatabase courseDatabase;
    private CourseDao courseDao;
    private AnyChartView gpaPerSemesterChart;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_per_semester_chart);

        initialization();

        gpaPerSemesterChart();

    }
//initialize widgets and build database access.
    private void initialization(){
        gpaPerSemesterChart = (AnyChartView) findViewById(R.id.gpaPerSemesterChart);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        gpaPerSemesterChart.setProgressBar(progressBar);

        courseDatabase = CourseDatabase.getInstance(this);
        courseDao = courseDatabase.getCourseDao();
    }
//create a column chart of GPA per semester, which using AnyChart Android Charts library.
    private void gpaPerSemesterChart(){

//get user's overall GPA
        int receivedCredits = courseDao.getTotalCredits();
        int numberOfCourses = courseDao.getAllCourses().size();
        int CreditsPlusGrades = 0;
        List<Integer> eachCourseCredits = courseDao.getAllCourseCredit();
        List<Integer> eachCourseGrade = courseDao.getAllCourseGrade();

        for(int i = 0 ; i < numberOfCourses; i++ ){
            CreditsPlusGrades += eachCourseCredits.get(i) * eachCourseGrade.get(i);
        }

        int overAllGpa = Math.round(CreditsPlusGrades/(float)receivedCredits);

//get the number, which indicates how many semester user has already spent in this study program.
        List<Integer> semesterValues = courseDao.getEverySemesterValue();
        int maxSemesterValue = semesterValues.get(0);

        for(int i=0; i<semesterValues.size(); i++) {
            if(semesterValues.get(i) > maxSemesterValue) {
                maxSemesterValue = semesterValues.get(i);
            }
        }
//get the list of GPA per each semester (this small part took me 2 hours to finally get the right value of GPA list)
        List<Integer>  gpaPerSemester = new ArrayList<>();

        for (int semester = 1; semester <= maxSemesterValue; semester++ ){
            List<Integer> everyCreditInEachSemester = courseDao.getEveryCreditBySemester(semester);
            List<Integer> everyGradeInSemester = courseDao.getEveryGradeBySemester(semester);
            int SumOfCreditPlusGrade = 0;
            int sizeOfEachSemester = everyCreditInEachSemester.size();
            for (int i = 0;i < sizeOfEachSemester; i++){
                SumOfCreditPlusGrade += everyCreditInEachSemester.get(i) * everyGradeInSemester.get(i);
            }
            gpaPerSemester.add(Math.round(SumOfCreditPlusGrade/(float)courseDao.getTotalCreditsBySemester(semester)));
        }

//create column chart instance
        Cartesian cartesian = AnyChart.column();
//create dataEntries list for add data to column chart.
        List<DataEntry> dataEntries = new ArrayList<>();
//add each semester's GPA to DataEntry list
        for (int i = 0; i < maxSemesterValue; i++) {
            dataEntries.add(new ValueDataEntry(i + 1,gpaPerSemester.get(i)));
        }
//add overall GPA to DataEntry list
        dataEntries.add(new ValueDataEntry("Overall",overAllGpa));

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
        cartesian.yAxis(0).title("GPA");
//allow using animation effect
        cartesian.animation(true);
//set column title
        cartesian.title("GPA per semester");
        gpaPerSemesterChart.setChart(cartesian);

    }
}