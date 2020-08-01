package cooldudes.restart;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;

import cooldudes.restart.model.AppUser;

public class JournalEntry extends AppCompatActivity {

    private Button yesBtn, noBtn, doneBtn;
    private TextView dayX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journalentry);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate= formatter.format(date);

        yesBtn = findViewById(R.id.yesbtn);
        noBtn = findViewById(R.id.nobtn);
        doneBtn = findViewById(R.id.done);
        dayX = findViewById(R.id.dayX);

        int days = AppUser.findDiff(, new Date().getTime())

        doneBtn.setText(strDate);
        //dayX.setText("day " + " of your journey");


        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yesBtn.setTextColor(Color.parseColor(String.valueOf(0xAAC1836)));
                yesBtn.setBackgroundColor(Color.parseColor(String.valueOf(0xAAFFFFFF)));

                noBtn.setTextColor(Color.parseColor(String.valueOf(0xAAFFFFFF)));
                noBtn.setBackgroundResource(R.drawable.border_lessround);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noBtn.setTextColor(Color.parseColor(String.valueOf(0xAAC1836)));
                noBtn.setBackgroundColor(Color.parseColor(String.valueOf(0xAAFFFFFF)));

                yesBtn.setTextColor(Color.parseColor(String.valueOf(0xAAFFFFFF)));
                yesBtn.setBackgroundResource(R.drawable.border_lessround);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JournalEntry.this, MainActivity.class);
                startActivity(i);

            }
        });
    }


}