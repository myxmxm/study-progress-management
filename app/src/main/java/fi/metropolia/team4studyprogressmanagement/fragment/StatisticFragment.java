package fi.metropolia.team4studyprogressmanagement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

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
    AnyChartView anyChartView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        courseDatabase = CourseDatabase.getInstance(getActivity());
        courseDao = courseDatabase.getCourseDao();

        SharedPreferences preGet = getActivity().getSharedPreferences("USER_DATE", getActivity().MODE_PRIVATE);
        totalCredits = Integer.parseInt(preGet.getString(LogInActivity.KEY_CREDITS,""));

        anyChartView = (AnyChartView) getActivity().findViewById(R.id.anyChartView);

        setupPieChart();

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
        completionPercent.setText(String.valueOf(completionPercents));
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(StatisticFragment.this).commit();
    }




    public void setupPieChart() {

        List<DataEntry> dataEntries = new ArrayList<>();
        List<String> course = courseDao.getAllCourseName();

        int uncompletedCredits = totalCredits - courseDao.getTotalCredits();
        dataEntries.add(new ValueDataEntry("Uncompleted studies",uncompletedCredits));
        Pie pie = AnyChart.pie();
        for (int i = 0; i < course.size(); i++ ) {
            dataEntries.add(new ValueDataEntry(course.get(i), courseDao.getCreditByCourseName(course.get(i))));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }
}