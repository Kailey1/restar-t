package cooldudes.restart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static cooldudes.restart.LoginActivity.appUser;
import static cooldudes.restart.LoginActivity.user;

public class OnboardingActivity2 extends AppCompatActivity {

    private Button doneBTN;
    private EditText emailET, smsET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);

        doneBTN = findViewById(R.id.onboarding2_next);
        emailET = findViewById(R.id.onboarding_email);
        smsET = findViewById(R.id.onboarding_sms);

        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                appUser.setContactEmail(String.valueOf(emailET.getText()));
                appUser.setContactSms(String.valueOf(smsET.getText()));

                Intent i = new Intent(OnboardingActivity2.this, OnboardingActivity3.class);
                startActivity(i);
            }
        });
    }
}