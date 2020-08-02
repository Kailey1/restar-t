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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.restart.model.Entry;

import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.model.AppUser.findDiff;

import cooldudes.restart.model.AppUser;

public class JournalEntry extends AppCompatActivity {

    private Button yesBtn, noBtn, doneBtn;
    private EditText triggersET, anythingET;
    private Entry e;
    private TextView dayX, dateText;
    private ImageView alien1, alien2, alien3, alien4, alien5;
    private Long entryTime;

    public static int[] aliens = new int[]{R.drawable.horrible, R.drawable.sad, R.drawable.meh, R.drawable.happy, R.drawable.wohoo};
    public static int[] aliensFilled = new int[]{R.drawable.horriblefilled, R.drawable.sadfilled, R.drawable.mehfilled, R.drawable.happyfilled,R.drawable.wohoofilled};

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journalentry);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");

        yesBtn = findViewById(R.id.yesbtn);
        noBtn = findViewById(R.id.nobtn);
        doneBtn = findViewById(R.id.donebtn);
        triggersET = findViewById(R.id.goal_explanation);
        anythingET = findViewById(R.id.anything_else);
        dayX = findViewById(R.id.dayX);
        dateText = findViewById(R.id.date);
        alien1 = findViewById(R.id.alien_1);
        alien2 = findViewById(R.id.alien_2);
        alien3 = findViewById(R.id.alien_3);
        alien4 = findViewById(R.id.alien_4);
        alien5 = findViewById(R.id.alien_5);

        Bundle extras = getIntent().getExtras();
        entryTime = extras.getLong("ENTRY_TIME");
        String strDate = new java.text.SimpleDateFormat("MMMM dd yyyy").format(entryTime);
        dateText.setText(strDate);

        // TODO: change day system  here


        long dayNum = (findDiff(entryTime, new Date().getTime())+1);
        dayX.setText("day " + dayNum + " of your journey");

        // populates the views with what they already filled out previously
        fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(entryTime)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                e = dataSnapshot.getValue(Entry.class);

                int m = e.getMood();
                clickAlien(m);

                triggersET.setText(e.getTriggers());
                anythingET.setText(e.getAnything());

                if (e.isGoalMet()){
                    yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                    yesBtn.setBackgroundResource(R.drawable.whiteback);
                    noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                    noBtn.setBackgroundResource(R.drawable.border_lessround);
                } else {
                    noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                    noBtn.setBackgroundResource(R.drawable.whiteback);
                    yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                    yesBtn.setBackgroundResource(R.drawable.border_lessround);
                }

                alien1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAlien(0);
                    }
                });
                alien2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAlien(1);
                    }
                });
                alien3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAlien(2);
                    }
                });
                alien4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAlien(3);
                    }
                });
                alien5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAlien(4);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yesBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                yesBtn.setBackgroundResource(R.drawable.whiteback);

                noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.white));
                noBtn.setBackgroundResource(R.drawable.border_lessround);

                e.setGoalMet(true);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noBtn.setTextColor(ContextCompat.getColor(JournalEntry.this, R.color.darkblue));
                noBtn.setBackgroundResource(R.drawable.whiteback);

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
                e.setFilled(true);

                fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(entryTime)).setValue(e);

                finish();
            }
        });
    }

    private void clickAlien(int i){

        e.setMood(i);

        // updates views
        switch (i){
            case 0:
                alien1.setImageResource(aliensFilled[0]);
                alien2.setImageResource(aliens[1]);
                alien3.setImageResource(aliens[2]);
                alien4.setImageResource(aliens[3]);
                alien5.setImageResource(aliens[4]);
                break;
            case 1:
                alien1.setImageResource(aliens[0]);
                alien2.setImageResource(aliensFilled[1]);
                alien3.setImageResource(aliens[2]);
                alien4.setImageResource(aliens[3]);
                alien5.setImageResource(aliens[4]);
                break;
            case 2:
                alien1.setImageResource(aliens[0]);
                alien2.setImageResource(aliens[1]);
                alien3.setImageResource(aliensFilled[2]);
                alien4.setImageResource(aliens[3]);
                alien5.setImageResource(aliens[4]);
                break;
            case 3:
                alien1.setImageResource(aliens[0]);
                alien2.setImageResource(aliens[1]);
                alien3.setImageResource(aliens[2]);
                alien4.setImageResource(aliensFilled[3]);
                alien5.setImageResource(aliens[4]);
                break;
            case 4:
                alien1.setImageResource(aliens[0]);
                alien2.setImageResource(aliens[1]);
                alien3.setImageResource(aliens[2]);
                alien4.setImageResource(aliens[3]);
                alien5.setImageResource(aliensFilled[4]);
                break;
        }
    }


}
