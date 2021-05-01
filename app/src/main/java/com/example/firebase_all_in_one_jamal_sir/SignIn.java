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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout email,password;
    private Button signIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email = (TextInputLayout)findViewById(R.id.email_id);
        password = (TextInputLayout)findViewById(R.id.password_id);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        signIn = (Button)findViewById(R.id.button_id);
        signIn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_id:
                    signIn();
                break;
        }
    }

    private void signIn() {



        String email_value = email.getEditText().getText().toString().trim();
        String password_value = password.getEditText().getText().toString().trim();


        if (email_value.isEmpty()){
            email.setError("Insert Email");
            email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email_value).matches()) {
            email.setError("Email is not Valid");
            email.requestFocus();
            return;
        }else if (password_value.isEmpty()){
            password.setError("Password is needed !");
            password.requestFocus();
            return;
        }else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email_value,password_value).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);

                        email.getEditText().setText("");
                        email.clearFocus();

                        password.clearFocus();
                        password.getEditText().setText("");

                        Toast.makeText(getApplicationContext(),"Successfully login !",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),DashBoard.class);
                        intent.putExtra("email",mAuth.getCurrentUser().getEmail().toString());
                        intent.putExtra("id",mAuth.getCurrentUser().getUid().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();


                    } else {

                        email.getEditText().setText("");
                        email.clearFocus();

                        password.clearFocus();
                        password.getEditText().setText("");



                        Toast.makeText(getApplicationContext(),"LogIn Failed ! Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }


    private void kayBoardHide() {
        //hide keyboard after finish input
        InputMethodManager imm = (InputMethodManager)getSystemService(
                getApplicationContext().INPUT_METHOD_SERVICE);
        View view = SignIn.this.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //hide keyboard after finish input
        InputMethodManager imm = (InputMethodManager)getSystemService(
                getApplicationContext().INPUT_METHOD_SERVICE);
        View view = SignIn.this.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return super.onTouchEvent(event);
    }
}