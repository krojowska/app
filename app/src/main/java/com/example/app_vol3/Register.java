package com.example.app_vol3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{
    Button registerBtn, backBtn;
    EditText emailEt, passwordEt, confirmPasswordEt;
    private FirebaseAuth firebaseAuth; //wifi must be on

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        confirmPasswordEt = findViewById(R.id.confirmPasswordEt);
        registerBtn = findViewById(R.id.registerBtn);
        backBtn = findViewById(R.id.backBtn);

        registerBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.registerBtn:
                if(validate()){
                    firebaseAuth.createUserWithEmailAndPassword(emailEt.getText().toString(), passwordEt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Register.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.backBtn:
                startActivity(new Intent(Register.this, UserData.class));
                break;
        }
    }

    private Boolean validate() {
        Boolean result = false;

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        String confirmPassword = confirmPasswordEt.getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(confirmPassword)) {
            Toast.makeText(Register.this, "Both passwords must be the same", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }
}
