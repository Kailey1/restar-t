package cooldudes.restart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                String title, content;
                if(!titleET.getText().toString().isEmpty()){
                    title = titleET.getText().toString();
                } else{
                    titleET.setError( "this field is required!" );
                    return;
                }
                if(!contentET.getText().toString().isEmpty()){
                    content = contentET.getText().toString();
                } else{
                    contentET.setError( "this field is required!" );
                    return;
                }
                String key = fireRef.child("posts").push().getKey();
                Post p = new Post(key, title, content);
                fireRef.child("posts").child(key).setValue(p);
                finish();
            }
        });
    }

}
