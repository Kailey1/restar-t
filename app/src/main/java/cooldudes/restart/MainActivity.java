package cooldudes.restart;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    final static String TAG = MainActivity.class.getSimpleName();

    // Firebase
    public static DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets up nav bar and fragments
        createNotificationChannel();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        final DashboardFragment mission = new DashboardFragment();
        loadFragment(mission);

        // make sure to put country code in front
//        sendText("hello", "+16139810982");
//        sendEmail("hello", "hello", "ggaoww@gmail.com");

        startService(new Intent(MainActivity.this,ShakeService.class));

            // testing to see if notification worked
            //addNotification("hi","poop");

    }

    // to send texts
    private void sendText(String message, String toNumber){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toNumber, null, message, null, null);
        }
    }

    // TODO: do we want to send emails automatically?
    // to send emails
    private void sendEmail(String message, String subject, String toAddress){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{toAddress});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            startActivity(Intent.createChooser(i, "Let someone know..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
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
    /*
    // set up listener for when data changed --> sends relevant notifications to user
    public void setUpNotifs(){
        DatabaseReference requestRef = fireRef.child("requests");
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                reqs = getArrayList(REQS, MainActivity.this);

                // clears the list to fetch new data
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Request req = itemSnapshot.getValue(Request.class);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        int d = RequestFragment.findDistance(RequestFragment.location, req.getLat(), req.getLng());
                        int wait = Request.secAgo((long) req.getTimestamp());
                        // sends a notification to alert user of a nearby request posted
                        if (reqs != null){
                            boolean mine = reqs.contains(req.getId());
                            // if pending request, within 200m and 30 seconds
                            if (req.getStatus() == 0 && d < 200 && wait <= 30 && !mine) {
                                String title = "Do you have a " + (Request.products[req.getProduct()]).toLowerCase() + " ?";
                                String message = "Help out a sister in need! (" + d + " m away)";
                                addNotification(title, message, req.getProduct());
                            }
                        }
                    }
                    // if the request is the user's own pending request that has been answered
                    if (reqs != null && reqs.contains(req.getId()) && req.getStatus()==1){
                        String title = "Help is on the way!";
                        String message = "Your code word is " + req.getCode();
                        // removes from pending list stored in phone
                        reqs.remove(req.getId());
                        saveArrayList(reqs, REQS, MainActivity.this);
                        // shows notification that help is coming
                        addNotification(title, message, req.getProduct());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }*/

    private void addNotification(String title, String message) {

        // builds notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "restar-t")
                .setSmallIcon(R.drawable.icon)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);

        // creates the intent needed to show the notification
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // add as notification
        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(0,builder.build());

    }
    private void createNotificationChannel() {
        // create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "restar-tchannel";
            String description = "Channel for restar-t notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("restar-t", name, importance);
            channel.setDescription(description);
            // register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}