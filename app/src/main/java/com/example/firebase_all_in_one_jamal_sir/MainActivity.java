package com.example.firebase_all_in_one_jamal_sir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout email_id,password_id;
    private TextView singIn;
    private Button submit;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email_id = (TextInputLayout)findViewById(R.id.email_id);
        password_id = (TextInputLayout)findViewById(R.id.password_id);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        submit = (Button)findViewById(R.id.button_id);
        singIn = (TextView)findViewById(R.id.singIn);

        submit.setOnClickListener(this);
        singIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_id:
                signUp();
                break;
            case R.id.singIn:
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                break;
        }
    }

    private void signUp() {


        String email = email_id.getEditText().getText().toString();
        String password = password_id.getEditText().getText().toString();


        if (email.isEmpty()){
            email_id.setError("Insert Email");
            email_id.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_id.setError("Email is not Valid");
            email_id.requestFocus();
            return;
        }else if (password.isEmpty()){
            password_id.setError("Password is needed !");
            password_id.requestFocus();
            return;
        }else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        kayBoardHide();

                        email_id.setError("");
                        email_id.clearFocus();

                        password_id.setError("");
                        password_id.clearFocus();

                        progressBar.setVisibility(View.GONE);
                        email_id.getEditText().setText("");
                        password_id.getEditText().setText("");
                        Toast.makeText(getApplicationContext(),"User Created Successfully !",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,SignIn.class));
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            progressBar.setVisibility(View.GONE);

                            email_id.setError("");
                            email_id.requestFocus(View.GONE);
                            password_id.setError("");
                            password_id.requestFocus(View.GONE);

                            Toast.makeText(getApplicationContext(),"User Already created !",Toast.LENGTH_SHORT).show();
                        }else {
                            progressBar.setVisibility(View.GONE);                                kayBoardHide();
                            Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

    }

    private void kayBoardHide() {
        //hide keyboard after finish input
        InputMethodManager imm = (InputMethodManager)getSystemService(
                getApplicationContext().INPUT_METHOD_SERVICE);
        View view = MainActivity.this.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //hide keyboard after finish input
        InputMethodManager imm = (InputMethodManager)getSystemService(
                getApplicationContext().INPUT_METHOD_SERVICE);
        View view = MainActivity.this.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return super.onTouchEvent(event);
    }

}