package cooldudes.restart;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //After a specified delay, the screen will change to the MainActivity (Requests for You Page)
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the MainActivity  */
                Intent mainIntent = new Intent(LauncherActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);


    }
}
