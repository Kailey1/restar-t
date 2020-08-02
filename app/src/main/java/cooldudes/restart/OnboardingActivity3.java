package cooldudes.restart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cooldudes.restart.model.Entry;

import static cooldudes.restart.AlarmReceiver.getMidnight;
import static cooldudes.restart.LoginActivity.appUser;
import static cooldudes.restart.LoginActivity.user;

public class OnboardingActivity3 extends AppCompatActivity {

    private Button doneBTN;
    private ImageButton backBTN;
    private EditText reason1ET, reason2ET, reason3ET;
    private String reason1, reason2, reason3;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding3);

        // views
        doneBTN = findViewById(R.id.onboarding_done);
        reason1ET = findViewById(R.id.reason1);
        reason2ET = findViewById(R.id.reason2);
        reason3ET = findViewById(R.id.reason3);
        backBTN = findViewById(R.id.back_button);

        // closes screen and goes back to previous screen
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardingActivity3.this, OnboardingActivity.class);
                startActivity(i);
            }
        });

        // collects 3 reasons
        doneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!reason1ET.getText().toString().isEmpty()){
                    reason1 = reason1ET.getText().toString();
                } else{
                    reason1ET.setError( "this field is required!" );
                    return;
                }
                if(!reason2ET.getText().toString().isEmpty()){
                    reason2 = reason2ET.getText().toString();
                } else{
                    reason2ET.setError( "this field is required!" );
                    return;
                }
                if(!reason3ET.getText().toString().isEmpty()){
                    reason3 = reason3ET.getText().toString();
                } else{
                    reason3ET.setError( "this field is required!" );
                    return;
                }

                // stores reasons in database
                final DatabaseReference userRef = fireRef.child("users").child(user.getUid());
                userRef.setValue(appUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userRef.child("reasons").child("1").setValue(reason1);
                        userRef.child("reasons").child("2").setValue(reason2);
                        userRef.child("reasons").child("3").setValue(reason3);
                        Toast.makeText(getApplicationContext(),"Welcome to your chance to restart!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // if failed to save
                                Toast.makeText(getApplicationContext(),"Uh-oh! something went wrong, please try again.",Toast.LENGTH_SHORT).show();
                            }
                        });

                // creates a new journal entry for this day
                DatabaseReference entryRef = fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(getMidnight()));
                entryRef.setValue(new Entry(getMidnight()));

                // starts MainActivity and clears current activity stack of onboarding screens
                Intent i = new Intent(OnboardingActivity3.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
}