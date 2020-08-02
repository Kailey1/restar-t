package cooldudes.restart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;
import static cooldudes.restart.LoginActivity.appUser;

public class OnboardingActivity extends AppCompatActivity {

    private Button nextBTN;
    private EditText beerET, wineET, whiskeyET, liquorET, otherET, concET;
    private int beer, wine, whiskey, liquor, other;
    private float total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // views
        beerET = findViewById(R.id.beer_amt);
        wineET = findViewById(R.id.wine_amt);
        whiskeyET = findViewById(R.id.whiskey_amt);
        liquorET = findViewById(R.id.liquor_amt);
        otherET = findViewById(R.id.other_amt);
        concET = findViewById(R.id.other_conc);
        nextBTN = findViewById(R.id.onboarding_next);

        // calculates total ABV per day of each drink based on alcohol content
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(beerET.getText().toString())){
                    beer = Integer.parseInt(beerET.getText().toString());
                    total += beer*0.06;
                }
                if (!isEmpty(wineET.getText().toString())){
                    wine = Integer.parseInt(wineET.getText().toString());
                    total += wine*0.18;
                }
                if (!isEmpty(whiskeyET.getText().toString())){
                    whiskey = Integer.parseInt(whiskeyET.getText().toString());
                    total += whiskey*0.43;
                }
                if (!isEmpty(liquorET.getText().toString())){
                    liquor = Integer.parseInt(liquorET.getText().toString());
                    total += liquor*0.5;
                }
                if (!isEmpty(otherET.getText().toString()) || !isEmpty(concET.getText().toString())){
                    if ((!isEmpty(otherET.getText().toString()) && !isEmpty(concET.getText().toString()))){
                        other = Integer.parseInt(otherET.getText().toString());
                        float conc = Float.parseFloat(concET.getText().toString());
                        total += other*(conc /100);
                    } else {
                        Toast.makeText(OnboardingActivity.this, "Make sure you fill out both the amount and concentration!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // calculates standard drinks per day from total daily ABV
                int standard = (int) Math.round(total / 17.7);
                appUser.setStartAmt(standard);

                // starting point for taper schedule
                if (standard > 20) {
                    appUser.setDailyLimit(16);
                } else {
                    if (standard-2 < 0){
                        appUser.setDailyLimit(0);
                    } else {
                        appUser.setDailyLimit(standard-2);
                    }
                }

                // moves to next screen
                Intent i = new Intent(OnboardingActivity.this, OnboardingActivity2.class);
                startActivity(i);
            }
        });

    }




}