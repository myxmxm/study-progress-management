package fi.metropolia.team4studyprogressmanagement.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fi.metropolia.team4studyprogressmanagement.CourseDao;
import fi.metropolia.team4studyprogressmanagement.CourseDatabase;
import fi.metropolia.team4studyprogressmanagement.LogInActivity;
import fi.metropolia.team4studyprogressmanagement.R;


public class StatisticFragment extends Fragment {

    private static final String TAG = "myTag";

    private TextView completionPercent,averageGrade;
    private CourseDatabase courseDatabase;
    private CourseDao courseDao;
    int totalCredits;
    AnyChartView anyChartViewPie, anyChartViewColumn;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        courseDatabase = CourseDatabase.getInstance(getActivity());
        courseDao = courseDatabase.getCourseDao();

        //anyChartViewPie.setProgressBar(getView().findViewById(R.id.progressBar));
        anyChartViewPie = (AnyChartView) getActivity().findViewById(R.id.anyChartViewPie);
        //anyChartViewColumn = (AnyChartView) getActivity().findViewById(R.id.anyChartViewColumn);

        SharedPreferences preGet = getActivity().getSharedPreferences("USER_DATE", getActivity().MODE_PRIVATE);
        totalCredits = Integer.parseInt(preGet.getString(LogInActivity.KEY_CREDITS,""));




        pieChart();
        //columnChart();

        int numberOfCourses = courseDao.getAllCourses().size();
        int totalGrades = courseDao.getTotalGrade();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String averageGrades = decimalFormat.format((float)totalGrades/numberOfCourses);

        DecimalFormat percentFormat = new DecimalFormat("0.0%");
        int receivedCredits = courseDao.getTotalCredits();
        String completionPercents = percentFormat.format((float)receivedCredits/totalCredits);

        averageGrade = getView().findViewById(R.id.averageGrade);
        completionPercent = getView().findViewById(R.id.completionPercent);

        averageGrade.setText(String.valueOf(averageGrades));
        completionPercent.setText( String.valueOf(completionPercents));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(StatisticFragment.this).commit();
    }




    private void pieChart() {



        List<DataEntry> dataEntries = new ArrayList<>();
        List<String> course = courseDao.getAllCourseName();

        int uncompletedCredits = totalCredits - courseDao.getTotalCredits();
        dataEntries.add(new ValueDataEntry("Uncompleted studies",uncompletedCredits));
        Pie pie = AnyChart.pie();
        for (int i = 0; i < course.size(); i++ ) {
            dataEntries.add(new ValueDataEntry(course.get(i), courseDao.getCreditByCourseName(course.get(i))));
        }
        pie.data(dataEntries);
        anyChartViewPie.setChart(pie);

    }

    private void columnChart() {
        List<DataEntry> dataEntries = new ArrayList<>();
        List<Integer> credits = new ArrayList<>();
        List<Integer> semesterValues = courseDao.getEverySemesterValue();

        int number = courseDao.getAllCourses().size();
        for (int i = 1; i <= number;i++) {
            credits.add(courseDao.getTotalCreditsBySemester(i));
        }

        int maxSemesterValue = semesterValues.get(0);

        for(int i=1; i<semesterValues.size(); i++) {
            if(semesterValues.get(i) > maxSemesterValue) {
                maxSemesterValue = semesterValues.get(i);
            }
        }

        Cartesian cartesian = AnyChart.column();

        for (int i = 0; i < maxSemesterValue; i++) {
            dataEntries.add(new ValueDataEntry(i + 1,credits.get(i)));
        }

        dataEntries.add(new ValueDataEntry("Total", courseDao.getTotalCredits()));

        Column column = cartesian.column(dataEntries);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Credits per semester");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Semester");
        cartesian.yAxis(0).title("Credit");
        anyChartViewColumn.setChart(cartesian);
    }

}