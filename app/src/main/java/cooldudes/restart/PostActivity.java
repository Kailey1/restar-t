package cooldudes.restart;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.restart.model.Entry;

import static cooldudes.restart.LoginActivity.user;
import static cooldudes.restart.model.AppUser.findDiff;

import cooldudes.restart.model.AppUser;
import cooldudes.restart.model.Post;

public class PostActivity extends AppCompatActivity {

    private EditText titleET, contentET;
    private Button doneBtn;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        titleET = findViewById(R.id.edit_title);
        contentET = findViewById(R.id.edit_content);
        doneBtn = findViewById(R.id.post_btn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = fireRef.child("posts").push().getKey();
                Post p = new Post(key, titleET.getText().toString(), contentET.getText().toString());
                fireRef.child("posts").child(key).setValue(p);
                finish();
            }
        });
    }

}
