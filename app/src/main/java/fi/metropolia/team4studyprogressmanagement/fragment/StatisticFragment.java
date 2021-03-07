package fi.metropolia.team4studyprogressmanagement.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import fi.metropolia.team4studyprogressmanagement.CreditsPerSemesterChartActivity;
import fi.metropolia.team4studyprogressmanagement.CourseDao;
import fi.metropolia.team4studyprogressmanagement.CourseDatabase;
import fi.metropolia.team4studyprogressmanagement.GpaPerSemesterChartActivity;
import fi.metropolia.team4studyprogressmanagement.LogInActivity;
import fi.metropolia.team4studyprogressmanagement.StudyCompletionRateChartActivity;
import fi.metropolia.team4studyprogressmanagement.R;

/**
 * This fragment will shows 3 critical statistic data such as study completion rate, total credits,
 * and GPA, and there is one button under each statistic data, when user click each
 * button, user will get more detailed information about corresponding statistic data in a diagram form.
 */


public class StatisticFragment extends Fragment {

    private static final String TAG = "myTag";
    private TextView studyCompletionRate,averageGrade,totalCredit;
    private Button studyCompletionRateChartBtn, creditsPerSemesterChartBtn,gpaPerSemesterChartBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialization();

        showStatisticResult();

        studyCompletionRateChartBtn.setOnClickListener(studyCompletionRateChart);

        creditsPerSemesterChartBtn.setOnClickListener(creditsPerSemesterChart);

        gpaPerSemesterChartBtn.setOnClickListener(averageGradeChart);

    }
//Initialize widgets
    private void initialization(){
        studyCompletionRate = (TextView) getView().findViewById(R.id.studyCompletionRate);
        totalCredit = (TextView) getView().findViewById(R.id.totalCredit);
        averageGrade = (TextView) getView().findViewById(R.id.averageGrade);
        studyCompletionRateChartBtn = (Button) getActivity().findViewById(R.id.studyCompletionRateChartBtn);
        creditsPerSemesterChartBtn = (Button) getActivity().findViewById(R.id.creditsPerSemesterChartBtn);
        gpaPerSemesterChartBtn = (Button) getActivity().findViewById(R.id.gpaPerSemesterChartBtn);

    }

    private void showStatisticResult(){
//built database access
        CourseDatabase courseDatabase = CourseDatabase.getInstance(getActivity());
        CourseDao courseDao = courseDatabase.getCourseDao();
//get the total credits of user's study program inputted by user during registration process
        SharedPreferences preGet = getActivity().getSharedPreferences("USER_DATE", getActivity().MODE_PRIVATE);
        int totalCredits = Integer.parseInt(preGet.getString(LogInActivity.KEY_CREDITS,""));
//calculate the study completion percent
        DecimalFormat percentFormat = new DecimalFormat("0.0%");
        int receivedCredits = courseDao.getTotalCredits();
        String completionPercents = percentFormat.format((float)receivedCredits/totalCredits);
//calculate GPA by using the GPA formula GPA = (grades * credits)/credits
        int numberOfCourses = courseDao.getAllCourses().size();
        int CreditsPlusGrades = 0;
        List<Integer> eachCourseCredits = courseDao.getAllCourseCredit();
        List<Integer> eachCourseGrade = courseDao.getAllCourseGrade();

        for(int i = 0 ; i < numberOfCourses;i++ ){
            CreditsPlusGrades += eachCourseCredits.get(i) * eachCourseGrade.get(i);
        }

        //DecimalFormat decimalFormat = new DecimalFormat("0.0");
        //String averageGrades = decimalFormat.format((float)CreditsPlusGrades/receivedCredits);




        studyCompletionRate.setText(completionPercents);

        totalCredit.setText(String.valueOf(receivedCredits));

        averageGrade.setText(String.valueOf(Math.round(CreditsPlusGrades/(float)receivedCredits)));

    }
//create 3 OnClickListeners for all three buttons
    private View.OnClickListener studyCompletionRateChart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent studyCompletionRateChart = new Intent(getActivity(), StudyCompletionRateChartActivity.class);
            startActivity(studyCompletionRateChart);
        }
    };

    private View.OnClickListener creditsPerSemesterChart =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent creditsPerSemesterChart = new Intent(getActivity(), CreditsPerSemesterChartActivity.class);
            startActivity(creditsPerSemesterChart);
        }
    };

    private View.OnClickListener averageGradeChart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gpaPerSemesterChart = new Intent(getActivity(), GpaPerSemesterChartActivity.class);
            startActivity(gpaPerSemesterChart);
        }
    };

}