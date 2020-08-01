package cooldudes.restart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import cooldudes.restart.model.AppUser;

import static cooldudes.restart.LoginActivity.appUser;
import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.MainActivity.fireRef;
import static cooldudes.restart.model.AppUser.findDiff;

public class DashboardFragment extends Fragment {

    private TextView goalTV, streakTV, tMinusTV, progressTV, motivationTV;
    private ProgressBar progressBar;
    private ImageButton alienface;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //progress bar
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, null);
        goalTV = v.findViewById(R.id.daily_goal);
        streakTV = v.findViewById(R.id.streak);
        tMinusTV = v.findViewById(R.id.tminus);
        progressTV = v.findViewById(R.id.percent);
        motivationTV = v.findViewById(R.id.motivation_msg);
        progressBar = v.findViewById(R.id.vertical_progressbar);
        alienface = v.findViewById(R.id.face);


        fireRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AppUser u = dataSnapshot.getValue(AppUser.class);

                // fills views with info
                streakTV.setText(findDiff(u.getStreakStart(), new Date().getTime()) + " days");
                if (u.getDailyLimit()==0){
                    goalTV.setText("stay sober!");
                } else {
                    goalTV.setText(u.getDailyLimit() + " drinks");
                }

                // calculates and fills progress bar
                int percent = Math.round(100*(float)(u.getStartAmt()-u.getDailyLimit())/u.getStartAmt());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(percent, true);
                }
                progressTV.setText(percent + "%");
                if (percent < 30){
                    motivationTV.setText("you\ngot this!");
                } else if (percent < 60) {
                    motivationTV.setText("keep\nit up!");
                } else if (percent < 100) {
                    motivationTV.setText("almost\nthere!");
                } else {
                    motivationTV.setText("blast\noff!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        alienface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ShakeActivity.class);
                startActivity(i);
            }
        });

        ImageButton logoutBTN = v.findViewById(R.id.logout_button);
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.signOut(getActivity());
            }
        });
        return v;
    }
}
