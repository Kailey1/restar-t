package cooldudes.restart.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

import cooldudes.restart.MainActivity;
import cooldudes.restart.R;

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
        String dateHeader = "DAY " + (findDiff(m.getTime(), new Date().getTime())+1) + "  |  " + new java.text.SimpleDateFormat("MMMM d").format(m.getTime());
        holder.date.setText(dateHeader);
        holder.journal.setText(m.getTriggers());

        int[] aliens = new int[]{R.drawable.wohoo, R.drawable.happy, R.drawable.meh, R.drawable.sad, R.drawable.horrible};
        holder.alien.setImageResource(aliens[m.getMood()]);
    }


    // returns size of list
    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
