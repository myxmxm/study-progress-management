package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This will be the first activity to executed. This activity contains two AlertDialog, which is
 * sign up AlertDialog, and login AlertDialog, user will only be able to see sign up AlertDialog
 * once after they have successfully registered.
 * @param <staticActivity>
 */

public class LogInActivity<staticActivity> extends AppCompatActivity {
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
        setContentView(R.layout.activity_login);
//create sharedPreferences
        sharedPreferences = getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
//create boolean variable firstTimeOpenApp, and assign its values to a boolean value that
//does not exist yet, so it will take default value true for this time, during registration process
//this boolean value will be set to false.
        Boolean firstTimeOpenApp = sharedPreferences.getBoolean(KEY_FIRST,true);

        if(firstTimeOpenApp){
//signUpDialog()will be call when user first time open this app
            signUpDialog();
            return;
        }else{
//during registration process this boolean value firstTimeOpenApp will be set to false, so only
//loginDialog() will be called after first time to prevent overwriting the registration info.
            loginDialog();
            return;
        }

    }

//create sign up AlertDialog
    private void signUpDialog(){
//create the layout for sign up AlertDialog
        signUpLayoutInflater = (LayoutInflater) LayoutInflater.from(this);
        signUpView = (View) signUpLayoutInflater.inflate(R.layout.activity_sign_up_dialog, null);
//build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please register");
        builder.setView(signUpView);
        signUpDialog = builder.create();
        signUpDialog.show();

//initialize two buttons on this AlertDialog and set onClickListener for them
        okSignUpBtn = (Button) signUpView.findViewById(R.id.okInputBtn);
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
//dismiss the AlertDialog window
                signUpDialog.dismiss();
//finish LogInActivity
                LogInActivity.this.finish();

            }
        });
    }


//create loginDialog AlertDialog
    private void loginDialog(){
//create the layout for this AlertDialog
        loginLayoutInflater = (LayoutInflater) LayoutInflater.from(this);
        loginView = (View) loginLayoutInflater.inflate(R.layout.activity_login_dialog, null);
//build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please login");
        builder.setView(loginView);
        loginDialog = builder.create();
        loginDialog.show();
//initialize two buttons on this AlertDialog and set onClickListener for them
        okLoginBtn = (Button) loginView.findViewById(R.id.okInputBtn);
        cancelLoginBtn = (Button) loginView.findViewById(R.id.cancle);

        okLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification();
//if user successfully log in, show toast message"Welcome back + username"
                SharedPreferences preGet = getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
                String toast = "Welcome back " + preGet.getString(KEY_USERNAME,"") + "!";
                showToast(toast);
            }
        });

        cancelLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//dismiss the AlertDialog window
                signUpDialog.dismiss();
//finish LogInActivity
                LogInActivity.this.finish();
            }
        });

    }

//create registration method
    private void registration(){
//initialize widget on sign up AlertDialog
        signUpName = (EditText) signUpView.findViewById(R.id.userName);
        signUpPassword = (EditText) signUpView.findViewById(R.id.userPassword);
        userSchoolName = (EditText) signUpView.findViewById(R.id.schoolName);
        userDegreeProgramme = (EditText) signUpView.findViewById(R.id.degreeProgramme);
        userTotalCredits = (EditText) signUpView.findViewById((R.id.totalCredits));
//get the user input
        String signUpUserName = signUpName.getText().toString();
        String signUpUserPassword = signUpPassword.getText().toString();
        String schoolName = userSchoolName.getText().toString();
        String degreeProgramme = userDegreeProgramme.getText().toString();
        String totalCredits = userTotalCredits.getText().toString();
//make sure all registration fields have been filled before click ok button,otherwise give user
//remanding message
        if(signUpUserName.trim().equals("") || signUpUserPassword.trim().equals("") || schoolName.trim().equals("") || degreeProgramme.trim().equals("") || totalCredits.trim().equals("")){
            showToast("All registration fields have to be filled!");
            return;
        }
//save data to sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME,signUpUserName);
        editor.putString(KEY_PASSWORD, signUpUserPassword);
        editor.putString(KEY_SCHOOL_NAME, schoolName);
        editor.putString(KEY_DEGREE_PROGRAMME, degreeProgramme);
        editor.putString(KEY_CREDITS, totalCredits);
        editor.putBoolean(KEY_FIRST, false);
        editor.commit();

        showToast("You have successfully registered ");
//dismiss sign up AlertDialog window
        signUpDialog.dismiss();
//show login AlertDialog window
        loginDialog();

    }


//create verification method
    private void verification(){

        loginName = (EditText) loginView.findViewById(R.id.userName);
        loginPassword = (EditText) loginView.findViewById(R.id.userPassword);
//get the user input
        String loginUserName = loginName.getText().toString();
        String loginUserPassword = loginPassword.getText().toString();
//make sure all log in fields have been filled before click ok button,otherwise give user
//remanding message
        if (loginUserName.trim().equals("") || loginUserPassword.trim().equals("")){
            showToast("Username and Password can't be empty!");
            return;
        }
//get username and password which saved on sharedPreferences
        String savedUserName = sharedPreferences.getString(KEY_USERNAME,"");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
//compare user's input with correct username and password,if they are math, switch to TabActivity,
//if they don't match, sent toast message"Username or Password wrong"
        if (loginUserName.trim().equals(savedUserName) && loginUserPassword.trim().equals(savedPassword)){
            Intent intent = new Intent(LogInActivity.this, TabActivity.class) ;
            startActivity(intent);
        }else{
            showToast("Username or Password wrong");
            return;
        }
    }


//method for using toast message more conveniently
    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT);
        toast.show();
    }
}