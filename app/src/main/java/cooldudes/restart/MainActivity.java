package cooldudes.restart;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import cooldudes.restart.model.Entry;

import static cooldudes.restart.AlarmReceiver.getMidnight;
import static cooldudes.restart.LoginActivity.user;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    final static String TAG = MainActivity.class.getSimpleName();

    public static MainActivity m;

    // Firebase
    public static DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m = this;

        // sets up nav bar and fragments
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        final DashboardFragment mission = new DashboardFragment();
        loadFragment(mission);

        DatabaseReference entryRef = fireRef.child("users").child(user.getUid()).child("journal").child(String.valueOf(getMidnight()));
        entryRef.setValue(new Entry(getMidnight()));
        AlarmReceiver.setReset(this);

        startService(new Intent(MainActivity.this,ShakeService.class));

    }

    // to send texts
    public static void sendText(String message, String toNumber, Activity a){
        if (ActivityCompat.checkSelfPermission(a, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(a,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toNumber, null, message, null, null);
        }
    }

    // TODO: do we want to send emails automatically?
    // to send emails
    public static void sendEmail(String message, String subject, String toAddress, Activity a){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{toAddress});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            a.startActivity(Intent.createChooser(i, "Let someone know..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(a, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    public void setUpNotifs(){

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.navigation_progress:
                fragment = new ProgressFragment();
                break;
            case R.id.navigation_resources:
                fragment = new ResourcesFragment();
                break;
        }
        return loadFragment(fragment);
    }

}