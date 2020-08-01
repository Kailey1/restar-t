package cooldudes.restart;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.collection.LLRBNode;

public class JournalEntry extends AppCompatActivity {

    private Button yesBtn, noBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journalentry);

        yesBtn = findViewById(R.id.yesbtn);
        noBtn = findViewById(R.id.nobtn);

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