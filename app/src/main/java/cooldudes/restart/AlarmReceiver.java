package cooldudes.restart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import cooldudes.restart.model.Entry;

import static android.content.Context.ALARM_SERVICE;
import static cooldudes.restart.LoginActivity.user;

public class AlarmReceiver extends BroadcastReceiver {

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    // called when the AlarmReceiver reaches the scheduled time
    @Override
    public void onReceive(Context context, Intent i) {

        long yesterday = getMidnight() - (24*3600*1000);

        DatabaseReference entryRef = fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(getMidnight()));
        entryRef.setValue(new Entry(getMidnight()));

        fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(yesterday)).child("goalMet").addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  boolean metGoal = (boolean) dataSnapshot.getValue();
                  if (metGoal){
                      // reduces the current goal by 2 standard drinks
                      fireRef.child("users").child(user.getUid()).child("dailyLimit").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot drinkSnapshot) {
                                int prevGoal = (int) drinkSnapshot.getValue();
                                int newGoal = prevGoal - 2;
                                fireRef.child("users").child(user.getUid()).child("dailyLimit").setValue(newGoal);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                      );
                  } else {
                      // breaks the streak and keeps the goal the same
                      fireRef.child("users").child(user.getUid()).child("streakStart").setValue(String.valueOf(new Date().getTime()));
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          }
        );
        entryRef.setValue(new Entry(getMidnight()));

    }

    public static void setReset(Context context){

        // Intent to execute
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long start = getMidnight();

        // Starts the AlarmManager service to repeat at midnight every day
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(
                AlarmManager.RTC,
                start,
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }

    public static long getMidnight(){
        // finds midnight of current day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        long midnight = cal.getTimeInMillis();
        return midnight;
    }
}
