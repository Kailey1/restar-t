package cooldudes.restart.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Member;
import java.util.List;

import cooldudes.restart.MainActivity;
import cooldudes.restart.R;

import cooldudes.restart.model.Entry;

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
        public ImageView alien;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            date = v.findViewById(R.id.date);
            alien = v.findViewById(R.id.alien);
            journal = v.findViewById(R.id.journal);
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
                .inflate(R.layout.fragment_entry, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Entry m = entryList.get(position);
        holder.date.setText(String.valueOf(m.getDate()));
        holder.journal.setText(m.getWritten());
        int[] aliens = new int[]{R.drawable.wohoo, R.drawable.happy, R.drawable.meh, R.drawable.sad, R.drawable.horrible};
        for (int i=0; i<5; i++) {
            if (m.getMood()==i) {
                holder.alien.setImageResource(aliens[i]);
            }
        }
    }


    // returns size of list
    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
