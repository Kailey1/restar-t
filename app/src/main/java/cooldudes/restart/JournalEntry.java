package cooldudes.restart;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.restart.model.Entry;

import static cooldudes.restart.LoginActivity.user;

import cooldudes.restart.model.AppUser;

public class JournalEntry extends AppCompatActivity {

    private Button yesBtn, noBtn, doneBtn;
    private EditText triggersET, anythingET;
    private Entry e;
    private TextView dayX;
    private Long entryTime;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journalentry);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate= formatter.format(date);

        yesBtn = findViewById(R.id.yesbtn);
        noBtn = findViewById(R.id.nobtn);
        doneBtn = findViewById(R.id.donebtn);
        triggersET = findViewById(R.id.goal_explanation);
        anythingET = findViewById(R.id.anything_else);
        dayX = findViewById(R.id.dayX);

//        int days = AppUser.findDiff(, new Date().getTime())

        doneBtn.setText(strDate);

        Bundle extras = getIntent().getExtras();
        entryTime = extras.getLong("ENTRY_TIME");

        // populates the views with what they already filled out previously
        fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(entryTime)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                e = dataSnapshot.getValue(Entry.class);
                // TODO: add alien face
                int m = e.getMood();
                triggersET.setText(e.getTriggers());
                anythingET.setText(e.getAnything());

                if (e.isGoalMet()){
                    yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                    yesBtn.setBackgroundColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                    noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                    noBtn.setBackgroundResource(R.drawable.border_lessround);
                } else {
                    noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                    noBtn.setBackgroundColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                    yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                    yesBtn.setBackgroundResource(R.drawable.border_lessround);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                yesBtn.setBackgroundColor(ContextCompat.getColor(JournalEntry.this, R.color.white));

                noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                noBtn.setBackgroundResource(R.drawable.border_lessround);

                e.setGoalMet(true);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                noBtn.setBackgroundColor(ContextCompat.getColor(JournalEntry.this, R.color.white));

                yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                yesBtn.setBackgroundResource(R.drawable.border_lessround);

                e.setGoalMet(false);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                e.setAnything(anythingET.getText().toString());
                e.setTriggers(triggersET.getText().toString());
                int m = 0;
                e.setMood(m);

                fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(entryTime)).setValue(e);

                finish();
            }
        });
    }


}
