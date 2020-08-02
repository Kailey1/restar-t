package cooldudes.restart.model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

import cooldudes.restart.JournalEntry;
import cooldudes.restart.MainActivity;
import cooldudes.restart.R;

import static cooldudes.restart.JournalEntry.aliens;
import static cooldudes.restart.LoginActivity.appUser;
import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.model.AppUser.findDiff;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private static final String TAG = ItemAdapter.class.getSimpleName();

    private static int mPoints;

    private List<Entry> entryList;
    public MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView date, journal, andTv;
        public LinearLayout entryLayout;
        public ImageView alien;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            date = v.findViewById(R.id.date);
            alien = v.findViewById(R.id.alien);
            journal = v.findViewById(R.id.goal_met);
            entryLayout = v.findViewById(R.id.entry_layout);
        }
    }

    // constructor
    public ItemAdapter(List<Entry> requests, MainActivity m) {
        entryList = requests;
        main = m;
    }

    // create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new card view
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_entry, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Entry m = entryList.get(position);

        fireRef.child("users").child(user.getUid()).child("startTime").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long start = (long) dataSnapshot.getValue();
                String dateHeader = "DAY " + (findDiff(start, m.getTime())+1) + "  -  " + new java.text.SimpleDateFormat("MMMM d, YYYY").format(m.getTime());
                holder.date.setText(dateHeader);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (m.isFilled()){
            holder.journal.setVisibility(View.VISIBLE);
            holder.alien.setVisibility(View.VISIBLE);
            if (m.isGoalMet()){
                holder.journal.setText("goal reached!");
            } else {
                holder.journal.setText("did not reach goal");
            }
            holder.alien.setImageResource(aliens[m.getMood()]);
        } else {
            holder.journal.setVisibility(View.GONE);
            holder.alien.setVisibility(View.GONE);
        }

        holder.entryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main, JournalEntry.class);
                i.putExtra("ENTRY_TIME", m.getTime());
                main.startActivity(i);
            }
        });


    }


    // returns size of list
    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
