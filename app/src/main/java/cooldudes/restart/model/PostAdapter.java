package cooldudes.restart.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import cooldudes.restart.R;
import cooldudes.restart.SharingActivity;

import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.model.AppUser.findDiff;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> postList;
    public SharingActivity s;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView titleTV, contentTV, dateTV;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            titleTV = v.findViewById(R.id.post_title);
            contentTV = v.findViewById(R.id.post_content);
            dateTV = v.findViewById(R.id.post_date);
        }
    }

    // constructor
    public PostAdapter(List<Post> requests, SharingActivity m) {
        postList = requests;
        s = m;
    }

    // create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new card view
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_post, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Post m = postList.get(position);
        holder.titleTV.setText(m.getTitle());
        holder.contentTV.setText(m.getWritten());
        String strDate = new java.text.SimpleDateFormat("HH:mm - MMMM d").format(m.getTime());
        holder.dateTV.setText(strDate);

    }


    // returns size of list
    @Override
    public int getItemCount() {
        return postList.size();
    }
}
