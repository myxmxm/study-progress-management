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

public class MainActivity extends AppCompatActivity {
    private Boolean firstTime;
    private SharedPreferences sharedPreferences;
    private EditText signUpName, signUpPassword, loginName, loginPassword;
    private Button okSignUpBtn, cancelSignUpBtn, okLoginBtn, cancelLoginBtn;
    private AlertDialog signUpDialog, loginDialog;
    private View signUpView, loginView;
    private LayoutInflater signUpLayoutInflater, loginLayoutInflater;

    public static final String KEY_FIRST = "first";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("USER_DATE", Context.MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean(KEY_FIRST,true);


        if(firstTime){
            registrationDialog();
            return;
        }else{
            loginDialog();
            return;
        }

    }


    private void registrationDialog(){

        signUpLayoutInflater = (LayoutInflater) LayoutInflater.from(this);
        signUpView = (View) signUpLayoutInflater.inflate(R.layout.activity_sign_up_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please register");
        builder.setView(signUpView);
        signUpDialog = builder.create();
        signUpDialog.show();


        okSignUpBtn = (Button) signUpView.findViewById(R.id.ok);
        cancelSignUpBtn = (Button) signUpView.findViewById(R.id.cancle);
        signUpName = (EditText) signUpView.findViewById(R.id.userName);
        signUpPassword = (EditText) signUpView.findViewById(R.id.userPassword);


        okSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signUpUserName = signUpName.getText().toString();
                String signUpUserPassword = signUpPassword.getText().toString();

                if(signUpUserName.trim().equals("") || signUpUserPassword.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Username and Password can't be empty!", Toast.LENGTH_LONG).show();

                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME,signUpUserName);
                editor.putString(KEY_PASSWORD, signUpUserPassword);
                editor.putBoolean(KEY_FIRST, false);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class) ;
                String message = "Hello " + sharedPreferences.getString(KEY_NAME,"");
                intent.putExtra(KEY_MESSAGE,message);
                startActivity(intent);
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
        loginView = (View) loginLayoutInflater.inflate(R.layout.activity_sign_up_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please login");
        builder.setView(loginView);
        loginDialog = builder.create();
        loginDialog.show();

        okLoginBtn = (Button) loginView.findViewById(R.id.ok);
        cancelLoginBtn = (Button) loginView.findViewById(R.id.cancle);
        loginName = (EditText) loginView.findViewById(R.id.userName);
        loginPassword = (EditText) loginView.findViewById(R.id.userPassword);

        okLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginUserName = loginName.getText().toString();
                String loginUserPassword = loginPassword.getText().toString();

                if (loginUserName.trim().equals("") || loginUserPassword.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Username and Password can't be empty!", Toast.LENGTH_LONG).show();
                    return;
                }

                String savedUserName = sharedPreferences.getString(KEY_NAME,"");
                String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

                if (loginUserName.trim().equals(savedUserName) && loginUserPassword.trim().equals(savedPassword)){
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class) ;
                    String message = "Hello " + sharedPreferences.getString(KEY_NAME,"");
                    intent.putExtra(KEY_MESSAGE,message);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Username or Password wrong!", Toast.LENGTH_LONG).show();
                    return;
                }

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
}