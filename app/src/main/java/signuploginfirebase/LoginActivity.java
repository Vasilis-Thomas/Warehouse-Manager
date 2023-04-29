package signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "com.example.applicationwithmenu.loginActivity";
    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;
    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView redirectToSignupText, loginHiddentext;
    private Button loginButton;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            String restoreEmail = savedInstanceState.getString("String_Email");
            String restorePassword = savedInstanceState.getString("String_Password");
            loginEmail.setText(restoreEmail);
            loginPassword.setText(restorePassword);
        }
        setContentView(R.layout.activity_login);

//        Bundle bundl = getIntent().getExtras();
//        String title = bundl.getString("key1");

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginHiddentext = findViewById(R.id.login_hiddentext);
        redirectToSignupText = findViewById(R.id.login_button_redirect_to_signup);
        loginButton = findViewById(R.id.login_button);

        sharedPreferenceConfig = new sharedPreferenceConfig(getApplicationContext());
        if (sharedPreferenceConfig.readLoginStatus()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if(!password.isEmpty()){
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        sharedPreferenceConfig.writeLoginStatus(true);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                        loginHiddentext.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                    else{
                        loginPassword.setError("Password incorrect, please try again");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Email must not be empty");
                }
                else{
                    loginEmail.setError("Please enter a valid email");
                }
            }
        });


        redirectToSignupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUp_Activity.class));
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG,"onRestart callback method");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG,"onStart callback method");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG,"onResume callback method");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG,"onPause callback method");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        String storeEmail = loginEmail.getText().toString();
        String storePass  = loginPassword.getText().toString();
        outState.putString("String_Email", storeEmail);
        outState.putString("String_Password", storePass);
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState callback method");
    }


//    @Override
//    protected  void onRestoreInstanceState(Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
//        if(savedInstanceState == null) return;
//        String restoreEmail = savedInstanceState.getString("String_Email");
//        String restorePassword = savedInstanceState.getString("String_Password");
//        loginEmail.setText(restoreEmail.toString());
//        loginPassword.setText(restorePassword.toString());
//        Log.i(TAG,"onRestoreInstanceState callback method");
//    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG,"onStop callback method");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"onDestroy callback method");
    }

}