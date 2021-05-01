package com.example.firebase_all_in_one_jamal_sir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Account extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout first_name,last_name,number;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        first_name = (TextInputLayout)findViewById(R.id.name_textInputLayout);
        last_name = (TextInputLayout)findViewById(R.id.last_name_textInputLayout);
        number = (TextInputLayout)findViewById(R.id.number_textInputLayout);
        send = (Button)findViewById(R.id.submit);

        send.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                insertData();
                break;
        }
    }

    public void insertData(){
        String firstName = first_name.getEditText().getText().toString().trim();
        String s = String.valueOf(firstName.charAt(0));
        String lastName = last_name.getEditText().getText().toString().trim();

        if (firstName.isEmpty()){
            first_name.setError("Insert First Part of Your name !");
            first_name.requestFocus();
            return;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        return super.onTouchEvent(event);


    }
}