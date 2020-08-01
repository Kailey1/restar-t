package cooldudes.restart;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.collection.LLRBNode;

import java.util.Calendar;

import static cooldudes.restart.LoginActivity.user;

public class JournalEntry extends AppCompatActivity {

    private Button yesBtn, noBtn, doneBtn;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journalentry);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("ENTRY_ID");

        yesBtn = findViewById(R.id.yesbtn);
        noBtn = findViewById(R.id.nobtn);
        doneBtn = findViewById(R.id.donebtn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                yesBtn.setBackgroundColor(ContextCompat.getColor(JournalEntry.this, R.color.white));

                noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                noBtn.setBackgroundResource(R.drawable.border_lessround);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                noBtn.setBackgroundColor(ContextCompat.getColor(JournalEntry.this, R.color.white));

                yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                yesBtn.setBackgroundResource(R.drawable.border_lessround);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fireRef.child("users").child(user.getUid()).child("entries").push();

                finish();

            }
        });
    }


}