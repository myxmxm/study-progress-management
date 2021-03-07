package fi.metropolia.team4studyprogressmanagement.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fi.metropolia.team4studyprogressmanagement.CourseDao;
import fi.metropolia.team4studyprogressmanagement.CourseDatabase;
import fi.metropolia.team4studyprogressmanagement.R;

/**
 * From this fragment we give user several option to query their course related data, for instance,
 * user will be able to find the course name by specific grade, credit, and semester.
 */


public class SearchFragment extends Fragment {

    private Button courseBySemesterBtn,courseByGradeBtn,courseByCreditsBtn;
    private EditText CreditsEditText;
    private TextView resultTextView;
    private RadioGroup semesterGroup, gradeGroup;
    private int chosenSemester, chosenGrade;
    private CourseDatabase courseDatabase;
    private CourseDao courseDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialization ();

        courseBySemesterBtn.setOnClickListener(courseBySemester);

        courseByGradeBtn.setOnClickListener(courseByGrade);

        courseByCreditsBtn.setOnClickListener(courseByCredits);

    }
//initialize the widgets and build database access
    private void initialization () {
        resultTextView = (TextView) getView().findViewById(R.id.resultTextView);
        semesterGroup = (RadioGroup) getActivity().findViewById(R.id.semesterGroup);
        gradeGroup = (RadioGroup) getActivity().findViewById(R.id.gradeGroup);
        CreditsEditText = (EditText) getView().findViewById(R.id.CreditsEditText);
        courseBySemesterBtn = (Button) getActivity().findViewById(R.id.courseBySemesterBtn);
        courseByGradeBtn = (Button) getActivity().findViewById(R.id.courseByGradeBtn);
        courseByCreditsBtn = (Button) getActivity().findViewById(R.id.courseByCreditsBtn);

        courseDatabase = CourseDatabase.getInstance(getActivity());
        courseDao = courseDatabase.getCourseDao();
    }
//set OnClickListener for all three query buttons
    private  View.OnClickListener courseByGrade = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chosenGrade();
            getCoursesByGrade();

        }
    };

    private View.OnClickListener courseBySemester = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chosenSemester();
            getCoursesBySemester();

        }
    };


    private  View.OnClickListener courseByCredits = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCoursesByCredits();

        }
    };
//query a list of course name according to integer number chosenSemester from method chosenSemester()
    private void getCoursesBySemester(){
        if(chosenSemester != 1 && chosenSemester != 2 && chosenSemester != 3 && chosenSemester != 4){
            resultTextView.setText("Please select a semester before searching!");
            return;
        }

        List<String> courseBySemesterList = courseDao.getCourseNameBySemester(chosenSemester);
        String text = "";

        for (int i = 0; i < courseBySemesterList.size();i++){
            String string = courseBySemesterList.get(i);
            text += "   " + string + "\n";
        }

        resultTextView.setText(text);

        text = "";
    }
//query a list of course name according to integer number chosenGrade from method chosenGrade()
    private void getCoursesByGrade(){

        if(chosenGrade != 1 && chosenGrade != 2 && chosenGrade != 3 && chosenGrade != 4 && chosenGrade != 5){
            resultTextView.setText("Please select a grade before searching!");
            return;
        }
        List<String> courseByGradeList = courseDao.getCourseNameByGrade(chosenGrade);
        String text = "";

        for (int i = 0; i < courseByGradeList.size();i++){
            String string = courseByGradeList.get(i);
            text += "   " + string + "\n";
        }

        resultTextView.setText(text);

        text = "";
    }
//query a list of course name according to the credit number read from EditText CreditsEditText
    private void getCoursesByCredits(){
        String credit = CreditsEditText.getText().toString();
        if(credit.trim().equals("")){
            resultTextView.setText("Please enter a valid credit before searching!");
            return;
        }
        int credits = Integer.parseInt(CreditsEditText.getText().toString());
        List<String> courseByCreditsList = courseDao.getCourseNameByCredit(credits);
        String text = "";
        for (int i = 0; i < courseByCreditsList.size();i++){
            String string = courseByCreditsList.get(i);
            text += "   " + string + "\n";
        }

        resultTextView.setText(text);

        text = "";
    }
//return the integer number chosenSemester when using clicks corresponding radioButton
    private int chosenSemester(){
        int checkId = semesterGroup.getCheckedRadioButtonId();
        switch (checkId){
            case R.id.semesterRadioButton1:
                chosenSemester=1;
                break;
        }
        switch (checkId){
            case R.id.semesterRadioButton2:
                chosenSemester=2;
                break;
        }
        switch (checkId){
            case R.id.semesterRadioButton3:
                chosenSemester=3;
                break;
        }
        switch (checkId){
            case R.id.semesterRadioButton4:
                chosenSemester=4;
        }

        return chosenSemester;
    }
//return the integer number chosenGrade when using clicks corresponding radioButton
    private int chosenGrade(){
        int checkId = gradeGroup.getCheckedRadioButtonId();
        switch (checkId){
            case R.id.gradeRadioButton1:
                chosenGrade=1;
                break;
        }
        switch (checkId){
            case R.id.gradeRadioButton2:
                chosenGrade=2;
                break;
        }
        switch (checkId){
            case R.id.gradeRadioButton3:
                chosenGrade=3;
                break;
        }
        switch (checkId){
            case R.id.gradeRadioButton4:
                chosenGrade=4;
                break;
        }
        switch (checkId){
            case R.id.gradeRadioButton5:
                chosenGrade=5;
        }


        return chosenGrade;
    }

}

