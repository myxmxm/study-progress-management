package fi.metropolia.team4studyprogressmanagement.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fi.metropolia.team4studyprogressmanagement.CourseListActivity;
import fi.metropolia.team4studyprogressmanagement.MainActivity;
import fi.metropolia.team4studyprogressmanagement.R;


public class CourseFragment extends Fragment {

    private Button updateInfo, courseList;
    private EditText editUserName, editPassword, editSchoolName, editDegreeProgramme, editTotalCredits;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialization();

        getUserInfo();

        updateInfo.setOnClickListener(updateUserInfo);

        courseList.setOnClickListener(checkCourseList);

    }

    private void initialization(){
        updateInfo = (Button) getActivity().findViewById(R.id.updateInfo);
        courseList = (Button) getActivity().findViewById(R.id.courseList);
        editUserName = (EditText) getView().findViewById(R.id.editUserName);
        editPassword = (EditText) getView().findViewById(R.id.editPassword);
        editSchoolName = (EditText) getView().findViewById(R.id.editSchoolName);
        editDegreeProgramme = (EditText) getView().findViewById(R.id.editDegreeProgramme);
        editTotalCredits = (EditText) getView().findViewById(R.id.editTotalCredits);
    }

    private void getUserInfo(){
        SharedPreferences preGet = getActivity().getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
        String username = preGet.getString(MainActivity.KEY_USERNAME,"");
        String password = preGet.getString(MainActivity.KEY_PASSWORD,"");
        String schoolName = preGet.getString(MainActivity.KEY_SCHOOL_NAME,"");
        String degreeProgramme = preGet.getString(MainActivity.KEY_DEGREE_PROGRAMME,"");
        String totalCredits = preGet.getString(MainActivity.KEY_CREDITS,"");

        editUserName.setText(username);
        editPassword.setText(password);
        editSchoolName.setText(schoolName);
        editDegreeProgramme.setText(degreeProgramme);
        editTotalCredits.setText(totalCredits);

    }

    private View.OnClickListener updateUserInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences preGet = getActivity().getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preGet.edit();

            String newUserName = editUserName.getText().toString();
            String newPassword = editPassword.getText().toString();
            String newSchoolName = editSchoolName.getText().toString();
            String newDegreeProgramme = editDegreeProgramme.getText().toString();
            String newTotalCredits = editTotalCredits.getText().toString();

            editor.putString(MainActivity.KEY_USERNAME,newUserName);
            editor.putString(MainActivity.KEY_PASSWORD,newPassword);
            editor.putString(MainActivity.KEY_SCHOOL_NAME,newSchoolName);
            editor.putString(MainActivity.KEY_DEGREE_PROGRAMME,newDegreeProgramme);
            editor.putString(MainActivity.KEY_CREDITS,newTotalCredits);

            editor.commit();

        }
    };

    private View.OnClickListener checkCourseList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CourseListActivity.class);
            startActivity(intent);

        }
    };
}