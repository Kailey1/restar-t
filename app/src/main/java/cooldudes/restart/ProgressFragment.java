package cooldudes.restart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cooldudes.restart.model.Entry;
import cooldudes.restart.model.ItemAdapter;

import static cooldudes.restart.LoginActivity.user;

public class ProgressFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ProgressFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    private Button newBTN;


    private List<Entry> entries = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progress, null);

        main = (MainActivity) getActivity();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // recycler view set up
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(main);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ItemAdapter(entries, main);
        recyclerView.setAdapter(mAdapter);
        newBTN = view.findViewById(R.id.new_button);

        getEntries();

        newBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // opens journal entry for that day
                Intent i = new Intent(getActivity(), JournalEntry.class);
                i.putExtra("ENTRY_TIME", AlarmReceiver.getMidnight());
                startActivity(i);
            }
        });


        return view;
    }

    public void getEntries() {
        swipeRefreshLayout.setRefreshing(true);
        // retrieves info from database
        DatabaseReference entriesRef = fireRef.child("users").child(user.getUid()).child("journal");
        entriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // clears the list to fetch new data
                entries.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Entry m = itemSnapshot.getValue(Entry.class);
                    ProgressFragment.this.entries.add(m);
                }

                // sorts missions based on time, status
                Collections.sort(entries);

                // refreshes recycler view
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    // reloads when refreshed
    @Override
    public void onRefresh() {
        getEntries();
    }

}