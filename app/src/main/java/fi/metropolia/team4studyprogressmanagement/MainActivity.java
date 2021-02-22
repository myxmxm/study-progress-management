package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Boolean firstTimeOpenApp;
    private SharedPreferences sharedPreferences;
    private EditText signUpName, signUpPassword, userSchoolName, userDegreeProgramme, userTotalCredits, loginName, loginPassword;
    private Button okSignUpBtn, cancelSignUpBtn, okLoginBtn, cancelLoginBtn;
    private AlertDialog signUpDialog, loginDialog;
    private View signUpView, loginView;
    private LayoutInflater signUpLayoutInflater, loginLayoutInflater;

    public static final String KEY_FIRST = "firstTime";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_SCHOOL_NAME = "schoolName";
    public static final String KEY_DEGREE_PROGRAMME = "degreeProgramme";
    public static final String KEY_CREDITS = "credits";

    public static final String TAG = "mytag";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
        firstTimeOpenApp = sharedPreferences.getBoolean(KEY_FIRST,true);

        if(firstTimeOpenApp){
            signUpDialog();
            return;
        }else{
            loginDialog();
            return;
        }

    }


    private void signUpDialog(){

        signUpLayoutInflater = (LayoutInflater) LayoutInflater.from(this);
        signUpView = (View) signUpLayoutInflater.inflate(R.layout.activity_sign_up_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please register");
        builder.setView(signUpView);
        signUpDialog = builder.create();
        signUpDialog.show();


        okSignUpBtn = (Button) signUpView.findViewById(R.id.ok);
        cancelSignUpBtn = (Button) signUpView.findViewById(R.id.cancle);


        okSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });

        cancelSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpDialog.dismiss();
                MainActivity.this.finish();

            }
        });
    }



    private void loginDialog(){

        loginLayoutInflater = (LayoutInflater) LayoutInflater.from(this);
        loginView = (View) loginLayoutInflater.inflate(R.layout.activity_login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please login");
        builder.setView(loginView);
        loginDialog = builder.create();
        loginDialog.show();

        okLoginBtn = (Button) loginView.findViewById(R.id.ok);
        cancelLoginBtn = (Button) loginView.findViewById(R.id.cancle);

        okLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification();
            }
        });

        cancelLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpDialog.dismiss();
                MainActivity.this.finish();
            }
        });

    }


    private void registration(){

        signUpName = (EditText) signUpView.findViewById(R.id.userName);
        signUpPassword = (EditText) signUpView.findViewById(R.id.userPassword);
        userSchoolName = (EditText) signUpView.findViewById(R.id.schoolName);
        userDegreeProgramme = (EditText) signUpView.findViewById(R.id.degreeProgramme);
        userTotalCredits = (EditText) signUpView.findViewById((R.id.totalCredits));

        String signUpUserName = signUpName.getText().toString();
        String signUpUserPassword = signUpPassword.getText().toString();
        String schoolName = userSchoolName.getText().toString();
        String degreeProgramme = userDegreeProgramme.getText().toString();
        String totalCredits = userTotalCredits.getText().toString();

        if(signUpUserName.trim().equals("") || signUpUserPassword.trim().equals("") || schoolName.trim().equals("") || degreeProgramme.trim().equals("") || totalCredits.trim().equals("")){
            showToast("All registration fields have to be filled!");
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME,signUpUserName);
        editor.putString(KEY_PASSWORD, signUpUserPassword);
        editor.putString(KEY_SCHOOL_NAME, schoolName);
        editor.putString(KEY_DEGREE_PROGRAMME, degreeProgramme);
        editor.putString(KEY_CREDITS, totalCredits);
        editor.putBoolean(KEY_FIRST, false);
        editor.commit();

        showToast("You have successfully registered ");
        signUpDialog.dismiss();
        loginDialog();

    }



    private void verification(){

        loginName = (EditText) loginView.findViewById(R.id.userName);
        loginPassword = (EditText) loginView.findViewById(R.id.userPassword);

        String loginUserName = loginName.getText().toString();
        String loginUserPassword = loginPassword.getText().toString();

        if (loginUserName.trim().equals("") || loginUserPassword.trim().equals("")){
            showToast("Username and Password can't be empty!");
            return;
        }

        String savedUserName = sharedPreferences.getString(KEY_USERNAME,"");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        if (loginUserName.trim().equals(savedUserName) && loginUserPassword.trim().equals(savedPassword)){
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class) ;
            startActivity(intent);
        }else{
            showToast("Username or Password wrong");
            return;
        }
    }



    public void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT);
        toast.show();
    }
}