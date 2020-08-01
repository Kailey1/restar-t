package cooldudes.restart;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import cooldudes.restart.model.AppUser;

import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.MainActivity.fireRef;
import static cooldudes.restart.model.AppUser.findDiff;

public class DashboardFragment extends Fragment {

    private TextView goalTV, streakTV, tMinusTV, progressTV;
    private TextView percent, progressbar;
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

        fireRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AppUser u = dataSnapshot.getValue(AppUser.class);

                streakTV.setText(findDiff(u.getStreakStart(), new Date().getTime()) + " days");
                goalTV.setText(u.getDailyLimit() + " mL");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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