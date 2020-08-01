package cooldudes.restart;

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

import static cooldudes.restart.LoginActivity.user;

public class ShakeActivity extends AppCompatActivity {

    private TextView reason1TV, reason2TV, reason3TV;
    private Button talkBTN, journalBTN;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        // sends an automated sms
        fireRef.child("users").child(user.getUid()).child("contactSms").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      String phone = dataSnapshot.getValue(String.class);
                      String msg = "Hey, this is an automated text message sent from 'restart' to let you know that I'm feeling tempted to drink again.";
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

        talkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open link to online help
            }
        });

        journalBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open journal entry
            }
        });

    }

}