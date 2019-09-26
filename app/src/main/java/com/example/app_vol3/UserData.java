package com.example.app_vol3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class UserData extends AppCompatActivity implements View.OnClickListener{
    Button saveBtn;
    EditText firstNameEt, lastNameEt, birthEt, peselEt, medicinesEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        firstNameEt = findViewById(R.id.firstNameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        birthEt = findViewById(R.id.birthEt);
        peselEt = findViewById(R.id.peselEt);
        medicinesEt = findViewById(R.id.medicinesEt);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                if(validate()) {
                    startActivity(new Intent(this, MainActivity.class));
                }
        }
    }

    private Boolean validate() {
        Boolean result = false;

        String firstName = firstNameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String birth = birthEt.getText().toString();
        String pesel = peselEt.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty() || birth.isEmpty() || pesel.isEmpty()) {
            Toast.makeText(UserData.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if(pesel.length() !=11 && !pesel.matches("-?\\d+(\\.\\d+)?")) {
            Toast.makeText(UserData.this, "PESEL should have 11 digits long", Toast.LENGTH_SHORT).show();
        } else if(!birth.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            Toast.makeText(UserData.this, "Date of birth is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}
