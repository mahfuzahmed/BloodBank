package com.mander.mahfuz.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    String TAG = "EmailPassword";

    Button signup;
    EditText setfname, setlname, setmobile, setemail, setpassword, cpassword;
    Spinner setbloodgroup, setlocation;

    user u1 = new user();

    final FirebaseDatabase userdatabase = FirebaseDatabase.getInstance();

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();


        //buttons
        signup = findViewById(R.id.signup);

        //edittexts
        setfname = findViewById(R.id.setfname);
        setlname = findViewById(R.id.setlname);
        setmobile = findViewById(R.id.setmobile);
        setemail = findViewById(R.id.setemail);
        setpassword = findViewById(R.id.setpassword);
        cpassword = findViewById(R.id.cpassword);

        //spinners
        setbloodgroup = findViewById(R.id.setbloodgroup);
        setlocation = findViewById(R.id.setlocation);


        List<String> bloodgroups = new ArrayList<String>();
        bloodgroups.add("A+");
        bloodgroups.add("A-");
        bloodgroups.add("B+");
        bloodgroups.add("B-");
        bloodgroups.add("O+");
        bloodgroups.add("O-");
        bloodgroups.add("AB+");
        bloodgroups.add("AB-");

        List<String> locations = new ArrayList<String>();
        locations.add("Dhanmondi");
        locations.add("Gulshan");
        locations.add("Uttara");
        locations.add("Banani");
        locations.add("Mirpur");
        locations.add("Motijheel");
        locations.add("Badda");
        locations.add("Lalmatia");
        locations.add("Baridhara");

        //arrayadapters
        ArrayAdapter<String> bgadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodgroups);
        ArrayAdapter<String> locadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);



        bgadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setbloodgroup.setAdapter(bgadapter);
        setlocation.setAdapter(locadapter);


        setfname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                u1.setFirstname(setfname.getText().toString());
            }
        });

        setlname.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                u1.setLastname(setlname.getText().toString());
            }
        });

        setmobile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                u1.setCell(setmobile.getText().toString());
            }
        });

        setemail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                u1.setE_mail(setemail.getText().toString());
            }
        });

        setpassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String s1, s2;

                s1 = setpassword.getText().toString();
                s2 = cpassword.getText().toString();

                if (s1 == s2)
                {
                    u1.setPass_word(s1);
                }
            }
        });

        setbloodgroup.setOnItemSelectedListener(this);
        setlocation.setOnItemSelectedListener(this);



        u1.setBlood_group(setbloodgroup.getSelectedItem().toString());
        u1.setLocation(setlocation.getSelectedItem().toString());



        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createaccount(u1.getemail(), u1.getpassword(),u1);

                if(createaccount(u1.getemail(),u1.getpassword(),u1))
                {
                    Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Signup.this, Searchdonor.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Signup.this, "Please enter your details", Toast.LENGTH_SHORT).show();
                }
            }

        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {

    }


    private boolean createaccount(String email, String password, user u)
    {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm())
        {
            return false;
        }

        DatabaseReference ref =userdatabase.getReference("users");

        String node_name = u.firstname+u.lastname;

        ref.child(node_name).setValue(u);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
        });
        return true;
        // [END create_user_with_email]
    }
    private boolean validateForm()
    {
        boolean valid = true;

        String email = Signup.this.setemail.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            Signup.this.setemail.setError("Required.");
            valid = false;
        }
        else
        {
            Signup.this.setemail.setError(null);
        }

        String password = Signup.this.setpassword.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            Signup.this.setpassword.setError("Required.");
            valid = false;
        }
        else
        {
            Signup.this.setpassword.setError(null);
        }

        return valid;
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
