package com.mander.mahfuz.bloodbank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.String;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    Button login, signup;
    EditText email, password;

    String useremail, userpassword;


    String TAG = "EmailPassword";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);


        //edittexts
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        useremail = email.getText().toString();
        userpassword = password.getText().toString();

        //firebase
        mAuth = FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signIn(useremail,userpassword);
                if(signIn(useremail, userpassword))
                {
                    Intent i = new Intent(MainActivity.this, Searchdonor.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please enter valid email and password",Toast.LENGTH_SHORT).show();
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, Signup.class);
                startActivity(i);
            }
        });
    }


    private boolean signIn(String email, String password)
    {
        Log.d(TAG, "signIn:" + email);

        if (!validateForm())
        {
            return false;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Email and Password required",Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }

        }
        );
        return true;
        // [END sign_in_with_email]
    }

    private boolean validateForm()
    {
        boolean valid = true;

        String email = MainActivity.this.email.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            MainActivity.this.email.setError("Required.");
            valid = false;
        }
        else
        {

            MainActivity.this.email.setError(null);
        }

        String password = MainActivity.this.password.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            MainActivity.this.password.setError("Required.");
            valid = false;
        }
        else
        {
            MainActivity.this.password.setError(null);
        }

        return valid;

    }


    // Check if user is signed in (non-null) and update UI accordingly.

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();

        if (i == R.id.signup)
        {
            Intent i1 = new Intent(MainActivity.this, Signup.class);
            startActivity(i1);
        }
        else if (i == R.id.login)
        {
            signIn(useremail, userpassword);
            updateUI(mAuth.getCurrentUser());
        }
    }


    private void updateUI(FirebaseUser user)
    {
        if (user != null)
        {

            findViewById(R.id.email).setVisibility(View.GONE);
            findViewById(R.id.password).setVisibility(View.GONE);

        }
        else
        {

            findViewById(R.id.email).setVisibility(View.VISIBLE);
            findViewById(R.id.password).setVisibility(View.VISIBLE);
        }
    }



}