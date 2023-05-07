package signuploginfirebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eshopapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp_Activity extends AppCompatActivity {
    public final static String TAG = "signuploginfirebase";
    private FirebaseAuth auth;
    private EditText signupUsername, signupEmail, signupPassword, signupConfirmpassword;
    private TextView redirectToLoginText;
    private Button buttonSignup;

    boolean result = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupUsername = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmpassword = findViewById(R.id.signup_confirm_password);
        redirectToLoginText = findViewById(R.id.signup_button_redirect_to_login);
        buttonSignup = findViewById(R.id.signup_button);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signupUsername.getText().toString().trim();
                String useremail = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmpassword = signupConfirmpassword.getText().toString().trim();

                if (username.isEmpty() && useremail.isEmpty() && password.isEmpty() && confirmpassword.isEmpty()){
                    signupUsername.setError("Username is required");
                    signupEmail.setError("Email  is required");
                    signupPassword.setError("Password  is required");
                    signupConfirmpassword.setError("Confirm password  is required");
                    return;
                }

                if (username.length() < 4)
                    signupUsername.setError("Username must be minimum 4 characters");

                if (useremail.length() < 9)
                    signupEmail.setError("Email must be minimum 4 characters");

                if (password.isEmpty())
                    signupPassword.setError("Password  is required");

                if (confirmpassword.isEmpty())
                    signupConfirmpassword.setError("Confirm password  is required");

                if (password.length() < 8)
                    signupPassword.setError("Password must be minimum 8 characters");

                if (confirmpassword.length() < 8)
                    signupConfirmpassword.setError("Confirm password must be minimum 8 characters");

                if (!(password.toString().equals(confirmpassword.toString()))) {
                    signupConfirmpassword.setError("Confirm password and password must be the same");
                }

                else if (!(useremail.length() > 9 && username.length() > 4)){
                    signupUsername.setError("Username must be minimum 4 characters");
                    signupEmail.setError("Email must be minimum 9 characters");
                }

                else
                    auth.createUserWithEmailAndPassword(useremail, confirmpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d(TAG, "User profile updated, displayName is set: " +username);
                                        }
                                    }
                                });
                                Toast.makeText(SignUp_Activity.this, "SignUp Succesful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp_Activity.this, LoginActivity.class));
//                                Intent intent = new Intent(SignUp_Activity.this, LoginActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("key1", "1st :- Main Activity");
//                                intent.putExtras(bundle);
//                                startActivity(intent);

                            } else {
                                Toast.makeText(SignUp_Activity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

//                result = CheckAllFields();
//
//                if(result)
//                    Toast.makeText("All Right");
//                else
//                    Toast.makeText("Negative");
//
//                private boolean CheckAllFields() {
//                    boolean turn = false;
//
//                    if (signupUsername.length() == 0) {
//                        signupUsername.setError("This field is required");
//                        turn = false;
//                    } else {
//                        turn = true;
//                    }
//
//                    if (signupEmail.length() == 0) {
//                        signupEmail.setError("This field is required");
//                        turn = false;
//                    } else {
//                        turn = true;
//                    }
//
//                    if (signupPassword.length() == 0) {
//                        signupPassword.setError("Email is required");
//                        turn = false;
//                    } else {
//                        turn = true;
//                    }
//
//                    if (signupAuthpassword.length() == 0) {
//                        signupAuthpassword.setError("Password is required");
//                        turn = false;
//                    } else if (signupAuthpassword.length() < 8) {
//                        signupAuthpassword.setError("Password must be minimum 8 characters");
//                        turn = false;
//                    } else {
//                        turn = true;
//                    }
//
//                    return turn;
//                }



                redirectToLoginText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        startActivity(new Intent(SignUp_Activity.this, LoginActivity.class));
                        Intent intent = new Intent(SignUp_Activity.this, LoginActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key1", "1st :- Main Activity");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
        }
}