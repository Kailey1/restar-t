package cooldudes.restart;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cooldudes.restart.model.Entry;
import cooldudes.restart.model.Post;
import cooldudes.restart.model.PostAdapter;

import static cooldudes.restart.LoginActivity.user;

public class SharingActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ProgressFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    private TextView msgView;
    private Button newBTN;

    private List<Post> posts = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);
        swipeRefreshLayout = findViewById(R.id.refresh_posts);
        swipeRefreshLayout.setOnRefreshListener(this);

        // recycler view set up
        recyclerView = findViewById(R.id.posts_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PostAdapter(posts, this);
        recyclerView.setAdapter(mAdapter);
        newBTN = findViewById(R.id.new_button);

        getEntries();

        newBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SharingActivity.this, PostActivity.class);
                startActivity(i);
            }
        });


    }

    public void getEntries() {
        swipeRefreshLayout.setRefreshing(true);
        // retrieves info from database
        fireRef.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // clears the list to fetch new data
                posts.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Post m = itemSnapshot.getValue(Post.class);
                    posts.add(m);
                }

                // sorts missions based on time
                Collections.sort(posts);

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
//        getEntries();
    }

}