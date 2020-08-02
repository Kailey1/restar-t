package cooldudes.restart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import static cooldudes.restart.LoginActivity.appUser;

public class OnboardingActivity2 extends AppCompatActivity {

    private Button doneBTN;
    private ImageButton backBTN;
    private EditText smsET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);

        // views
        doneBTN = findViewById(R.id.onboarding2_next);
        backBTN = findViewById(R.id.back_button);
        smsET = findViewById(R.id.onboarding_sms);

        // closes screen and goes to previous screen
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardingActivity2.this, OnboardingActivity.class);
                startActivity(i);
            }
        });

        // collects sms and moves to next screen
        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validation check
                if(smsET.getText().toString().length() == 12 && smsET.getText().toString().charAt(0)=='+'){
                    appUser.setContactSms(String.valueOf(smsET.getText()));
                    Intent i = new Intent(OnboardingActivity2.this, OnboardingActivity3.class);
                    startActivity(i);
                }
                else{
                    smsET.setError( "invalid phone number, make sure to use the format above!" );
                    return;
                }
            }
        });


    }
}