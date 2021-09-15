package com.miniprojet.miniprojet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText mEmailEt,mPasswordEt;
    TextView notHave_AccountTv;
    Button mLoginBtn;

    private FirebaseAuth mAuth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // action et its titre
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Login");
        // enable back button
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth=FirebaseAuth.getInstance();

        mEmailEt=findViewById(R.id.emailEt);
        mPasswordEt=findViewById(R.id.passwordEt);
        notHave_AccountTv=findViewById(R.id.nothave_accountTv);
        mLoginBtn=findViewById(R.id.loginBtn);

        // login button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // input data
                String email =mEmailEt.getText().toString();
                String passw =mPasswordEt.getText().toString().trim();
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        mEmailEt.setError("Invalid Email ?");
                        mEmailEt.setFocusable(true);
                        loginUser(email,passw);

                    }

            }
        });

        // not have account click
        notHave_AccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        pd =new ProgressDialog(this);
        pd.setMessage("Logging In ...");

    }

    private void loginUser(String email, String passw) {
        pd.show();
        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            pd.dismiss();

                            FirebaseUser user =mAuth.getCurrentUser();


                            String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String> hashMap =new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("phone","");
                            hashMap.put("image","");

                            //firebase database isntance
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            // path to store user data named "users"
                            DatabaseReference reference= database.getReference("Users");
                            // put data within hashmap in database
                            reference.child(uid).setValue(hashMap);

                            Intent i=new Intent(LoginActivity.this,DashboardActivity.class);
                            startActivity(i);

                            finish();


                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this,"Authentication failed .",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {

                Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed(); // go previous activity
        return super.onSupportNavigateUp();
    }
}