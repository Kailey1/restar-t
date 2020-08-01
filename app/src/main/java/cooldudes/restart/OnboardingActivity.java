package cooldudes.restart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static cooldudes.restart.LoginActivity.appUser;

public class OnboardingActivity extends AppCompatActivity {

    private Button nextBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        nextBTN = findViewById(R.id.onboarding_next);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OnboardingActivity.this, OnboardingActivity2.class);
                startActivity(i);
            }
        });
    }




}