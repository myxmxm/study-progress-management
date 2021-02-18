package fi.metropolia.team4studyprogressmanagement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fi.metropolia.team4studyprogressmanagement.MainActivity;
import fi.metropolia.team4studyprogressmanagement.PagerAdapter;
import fi.metropolia.team4studyprogressmanagement.R;


public class CourseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.TAG,"clicked");
            }
        });

        TextView textView = getView().findViewById(R.id.textView);
        //String message = "hello xiaoming";

        SharedPreferences preGet = getActivity().getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
        String message = preGet.getString(MainActivity.KEY_USERNAME,"");
        textView.setText(message);


    }
}