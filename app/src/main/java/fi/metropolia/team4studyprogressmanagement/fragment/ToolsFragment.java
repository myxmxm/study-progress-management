package fi.metropolia.team4studyprogressmanagement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fi.metropolia.team4studyprogressmanagement.Collection;
import fi.metropolia.team4studyprogressmanagement.Course;
import fi.metropolia.team4studyprogressmanagement.MainActivity;
import fi.metropolia.team4studyprogressmanagement.R;


public class ToolsFragment extends Fragment {

    private Button buttonAdd, buttonDelete;
    private EditText courseName, semester, credit, grade, courseDetails, deleteCourseIndix;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tools, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialization ();

        buttonAdd.setOnClickListener(updateCourseInfo);

        buttonDelete.setOnClickListener(deleteCourse);

    }

    private void initialization () {
        buttonAdd = (Button) getActivity().findViewById(R.id.buttonAdd);
        buttonDelete = (Button) getActivity().findViewById(R.id.buttonDelete);
        courseName = (EditText) getView().findViewById(R.id.courseName);
        semester = (EditText) getView().findViewById(R.id.semester);
        credit = (EditText) getView().findViewById(R.id.credit);
        grade = (EditText) getView().findViewById(R.id.grade);
        courseDetails = (EditText) getView().findViewById(R.id.courseDetails);
        deleteCourseIndix = (EditText) getView().findViewById(R.id.deleteCourseIndix);
    }

    private View.OnClickListener updateCourseInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String newCourseName = courseName.getText().toString();
            int newSemester = Integer.parseInt(semester.getText().toString());
            int newCredit = Integer.parseInt(credit.getText().toString());
            int newGrade = Integer.parseInt(grade.getText().toString());
            String newCourseDetails = courseDetails.getText().toString();

            Collection collection = Collection.getInstance();
            collection.addCourse(new Course(newCourseName, newSemester,newCredit,newGrade,newCourseDetails));
        }
    };

    private  View.OnClickListener deleteCourse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int index = Integer.parseInt(deleteCourseIndix.getText().toString());
            Collection collection = Collection.getInstance();
            collection.removeCourse(index-1);
        }
    };

}

