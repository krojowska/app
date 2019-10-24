package com.example.app_vol3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserData extends AppCompatActivity implements View.OnClickListener {
    public static final String FIRST_NAME_KEY = "firstName";
    public static final String LAST_NAME_KEY = "lastName";
    public static final String BIRTH_KEY = "birth";
    public static final String PESEL_KEY = "pesel";
    public static final String MEDICINES_KEY = "medicines";
    public static final String TAG = "UserDetails";
    //private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("myData/childData");
    private FirebaseAuth firebaseAuth;

    Button saveBtn, mainPageBtn;
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
        mainPageBtn = findViewById(R.id.mainPageBtn);

        saveBtn.setOnClickListener(this);
        mainPageBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.getUid();
        DocumentReference mDocRef = FirebaseFirestore.getInstance().document("myData/"+user.getUid());

        switch (v.getId()) {
            case R.id.saveBtn:
                if (validate()) {
                    Map<String, Object> dataToSave = new HashMap<String, Object>();
                    dataToSave.put(FIRST_NAME_KEY, firstNameEt.getText().toString());
                    dataToSave.put(LAST_NAME_KEY, lastNameEt.getText().toString());
                    dataToSave.put(BIRTH_KEY, birthEt.getText().toString());
                    dataToSave.put(PESEL_KEY, peselEt.getText().toString());
                    dataToSave.put(MEDICINES_KEY, medicinesEt.getText().toString());
                    mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Document has been saved");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Document was not saved!", e);
                        }
                    });
                }
                break;
            case R.id.mainPageBtn:
                startActivity(new Intent(this, MainActivity.class));
        }
    }

    private Boolean validate() {
        String firstName = firstNameEt.getText().toString();
        String lastName = lastNameEt.getText().toString();
        String birth = birthEt.getText().toString();
        String pesel = peselEt.getText().toString();
        Boolean result = false;

        if (firstName.isEmpty() || lastName.isEmpty() || birth.isEmpty() || pesel.isEmpty()) {
            Toast.makeText(UserData.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else if (pesel.length() != 11 || !pesel.matches("-?\\d+(\\.\\d+)?")) {
            Toast.makeText(UserData.this, "PESEL should have 11 digits long", Toast.LENGTH_SHORT).show();
        } else if (!birth.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            Toast.makeText(UserData.this, "Date of birth is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}
