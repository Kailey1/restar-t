package cooldudes.restart;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.restart.model.AppUser;

import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.MainActivity.callContact;

public class ShakeActivity extends AppCompatActivity {

    private TextView reason1TV, reason2TV, reason3TV, counttime;
    private String reason1, reason2, reason3;
    private Button talkBTN, journalBTN, noThankYou;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    private CountDownTimer countDownTimer;
    private long timeLeftInMiliseconds = 300000; // 5 minutes
    private boolean timerRunning;
    public ShakeActivity shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        counttime = findViewById(R.id.counttime);
        // sends an automated sms

        fireRef.child("users").child(user.getUid()).child("contactSms").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override

                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      String phone = dataSnapshot.getValue(String.class);
                      String msg = "Hey, this is an automated text message sent from 'restart' to let you know that I'm feeling tempted to drink again.";
//                      String msg = "heyyy, this is an automated text message sent from your secret lover to let you know that I'm madly in love with you (obviously) - hehe text me back ;)";
                      MainActivity.sendText(msg, phone, ShakeActivity.this);
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              }
        );

        reason1TV = findViewById(R.id.reason1);
        reason2TV = findViewById(R.id.reason2);
        reason3TV = findViewById(R.id.reason3);
        talkBTN = findViewById(R.id.talk_button);
        journalBTN = findViewById(R.id.journal_button);
        noThankYou = findViewById(R.id.good);

        fireRef.child("users").child(user.getUid()).child("reasons").child("1").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   reason1TV.setText(dataSnapshot.getValue(String.class));
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           }
        );

        fireRef.child("users").child(user.getUid()).child("reasons").child("2").addValueEventListener(new ValueEventListener() {
                                                                                                          @Override
                                                                                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                              reason2TV.setText(dataSnapshot.getValue(String.class));
                                                                                                          }

                                                                                                          @Override
                                                                                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                          }
                                                                                                      }
        );

        fireRef.child("users").child(user.getUid()).child("reasons").child("3").addValueEventListener(new ValueEventListener() {
                                                                                                          @Override
                                                                                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                              reason3TV.setText(dataSnapshot.getValue(String.class));
                                                                                                          }

                                                                                                          @Override
                                                                                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                          }
                                                                                                      }
        );

        talkBTN.setOnClickListener(

            fireRef.child("users").child(user.getUid()).child("contactSms").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    final AppUser u = dataSnapshot.getValue(AppUser.class);
                    new AlertDialog.Builder(shake)
                            .setTitle("Caller selection")
                            .setMessage("Choose who you'd like to call")
                            .setPositiveButton("Call Hotline", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    callContact("18773032642", ShakeActivity.this);
                                }
                            })
                            .setNegativeButton("Call Trusted Person", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    callContact(u.getContactSms(), ShakeActivity.this);
                                }
                            })
                            .show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                )};




        journalBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opens journal entry for that day
                Intent i = new Intent(ShakeActivity.this, JournalEntry.class);
                i.putExtra("ENTRY_TIME", AlarmReceiver.getMidnight());
                startActivity(i);
            }
        });

        noThankYou.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Timer();

    }
    public void Timer() {
        if (timerRunning){
            stopTimer();
        }
        else {
            startTimer();
        }
    }
    public void  startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMiliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMiliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }
    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) timeLeftInMiliseconds / 60000;
        int seconds = (int) timeLeftInMiliseconds % 60000 /1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        counttime.setText(timeLeftText);
    }
}
