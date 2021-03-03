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


public class SearchFragment extends Fragment {

    private Button courseBySemesterBtn,creditsBySemesterBtn,courseByGradeBtn,courseByCreditsBtn;
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

        courseDatabase = CourseDatabase.getInstance(getActivity());
        courseDao = courseDatabase.getCourseDao();

        initialization ();

        courseBySemesterBtn.setOnClickListener(courseBySemester);

        courseByGradeBtn.setOnClickListener(courseByGrade);

        creditsBySemesterBtn.setOnClickListener(creditsBySemester);

        courseByCreditsBtn.setOnClickListener(courseByCredits);

    }

    private void initialization () {
        resultTextView = (TextView) getView().findViewById(R.id.resultTextView);
        semesterGroup = (RadioGroup) getActivity().findViewById(R.id.semesterGroup);
        gradeGroup = (RadioGroup) getActivity().findViewById(R.id.gradeGroup);
        CreditsEditText = (EditText) getView().findViewById(R.id.CreditsEditText);
        courseBySemesterBtn = (Button) getActivity().findViewById(R.id.courseBySemesterBtn);
        creditsBySemesterBtn = (Button) getActivity().findViewById(R.id.creditsBySemesterBtn);
        courseByGradeBtn = (Button) getActivity().findViewById(R.id.courseByGradeBtn);
        courseByCreditsBtn = (Button) getActivity().findViewById(R.id.courseByCreditsBtn);
    }

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

    private  View.OnClickListener creditsBySemester = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chosenSemester();
            if(chosenSemester != 1 && chosenSemester != 2 && chosenSemester != 3 && chosenSemester != 4){
                resultTextView.setText("Please select a semester before searching!");
                return;
            }
            resultTextView.setText("");
            int creditsSum = courseDao.getTotalCreditsBySemester(chosenSemester);
            resultTextView.setText(String.valueOf("   " + creditsSum)+" credits.");

            creditsSum = 0;
        }
    };

    private  View.OnClickListener courseByCredits = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getCoursesByCredits();

        }
    };

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

