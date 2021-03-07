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
import android.widget.Toast;

import fi.metropolia.team4studyprogressmanagement.CourseListActivity;
import fi.metropolia.team4studyprogressmanagement.LogInActivity;
import fi.metropolia.team4studyprogressmanagement.R;
import fi.metropolia.team4studyprogressmanagement.TabActivity;

/**
 * The first main purpose of this fragment is to allow user to edit the information they have provided
 * during registration process, such as username, password.
 * The second purpose of this fragment is to give user accessibility to edit their course list,
 * when user click the corresponding button, they will be taken to course list activity.
 *
 */


public class EditFragment extends Fragment {

    private Button updateInfo, courseList;
    private EditText editUserName, editPassword, editSchoolName, editDegreeProgramme, editTotalCredits;
    private SharedPreferences preGet;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialization();

        getUserInfo();

        updateInfo.setOnClickListener(updateUserInfo);

        courseList.setOnClickListener(checkCourseList);

    }
// initialize all widgets and access to sharedPreference
    private void initialization(){
        updateInfo = (Button) getActivity().findViewById(R.id.updateInfo);
        courseList = (Button) getActivity().findViewById(R.id.courseList);
        editUserName = (EditText) getView().findViewById(R.id.editUserName);
        editPassword = (EditText) getView().findViewById(R.id.editPassword);
        editSchoolName = (EditText) getView().findViewById(R.id.editSchoolName);
        editDegreeProgramme = (EditText) getView().findViewById(R.id.editDegreeProgramme);
        editTotalCredits = (EditText) getView().findViewById(R.id.editTotalCredits);

        preGet = getActivity().getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
    }
//read information from sharedPreference which provided by user during registration.
    private void getUserInfo(){
        String username = preGet.getString(LogInActivity.KEY_USERNAME,"");
        String password = preGet.getString(LogInActivity.KEY_PASSWORD,"");
        String schoolName = preGet.getString(LogInActivity.KEY_SCHOOL_NAME,"");
        String degreeProgramme = preGet.getString(LogInActivity.KEY_DEGREE_PROGRAMME,"");
        String totalCredits = preGet.getString(LogInActivity.KEY_CREDITS,"");

        editUserName.setText(username);
        editPassword.setText(password);
        editSchoolName.setText(schoolName);
        editDegreeProgramme.setText(degreeProgramme);
        editTotalCredits.setText(totalCredits);

    }
//create onClickListener for updateUserInfo button
    private View.OnClickListener updateUserInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//save updated information to sharedPreference
            SharedPreferences.Editor editor = preGet.edit();

            String newUserName = editUserName.getText().toString();
            String newPassword = editPassword.getText().toString();
            String newSchoolName = editSchoolName.getText().toString();
            String newDegreeProgramme = editDegreeProgramme.getText().toString();
            String newTotalCredits = editTotalCredits.getText().toString();

            editor.putString(LogInActivity.KEY_USERNAME,newUserName);
            editor.putString(LogInActivity.KEY_PASSWORD,newPassword);
            editor.putString(LogInActivity.KEY_SCHOOL_NAME,newSchoolName);
            editor.putString(LogInActivity.KEY_DEGREE_PROGRAMME,newDegreeProgramme);
            editor.putString(LogInActivity.KEY_CREDITS,newTotalCredits);

            editor.commit();

            showToast("Your information has been updated!");

        }
    };
//create onClickListener for checkCourseList button
    private View.OnClickListener checkCourseList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CourseListActivity.class);
            startActivity(intent);

        }
    };
//method for using toast message more conveniently
    private void showToast(String message){
        Toast toast = Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT);
        toast.show();
    }
}