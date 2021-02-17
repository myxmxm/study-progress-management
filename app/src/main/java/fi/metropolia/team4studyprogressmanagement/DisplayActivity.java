package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class DisplayActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        /*TextView textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String message1 = intent.getStringExtra(MainActivity.KEY_MESSAGE);
        textView.setText(message1);*/
    }
}