package com.example.tan089.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SosForumChat extends AppCompatActivity {
    private EditText editText;
    private DatabaseReference chat_data_ref;
    private DatabaseReference user_name_ref;
    private ListView listView;
    private FirebaseAuth mAuth;
    private String name="";
    HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_forum_chat);
        mAuth= FirebaseAuth.getInstance();
        editText=(EditText)findViewById(R.id.edittext);
        chat_data_ref= FirebaseDatabase.getInstance().getReference().child("chat_data");
        user_name_ref=FirebaseDatabase.getInstance().getReference().child("chat_users").child(mAuth.getCurrentUser().getUid()).child("name");
        listView=(ListView)findViewById(R.id.listview);
        map=new HashMap<>();
        user_name_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                name=dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        FirebaseListAdapter<SosMessage> adapter=new FirebaseListAdapter<SosMessage>(
                this,SosMessage.class,R.layout.chat_msg_row,chat_data_ref
        ) {
            @Override
            protected void populateView(View v, SosMessage model, int position) {
                //need to seperated the row
                TextView author=(TextView)v.findViewById(R.id.author);
                TextView dateAndTime = (TextView)v.findViewById(R.id.dateTime);
                TextView msg = (TextView)v.findViewById(R.id.message);
                author.setText(model.getAuthor());
                dateAndTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
                msg.setText(model.getMessage());
            }
        };
        listView.setAdapter(adapter);

    }
    public void send(View view) {
        chat_data_ref.push().setValue(new SosMessage(editText.getText().toString(),name));//storing actual msg with name of the user
        editText.setText("");//clear the msg in edittext
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chatmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            mAuth.signOut();//when the user clicks signout option this will executes
            startActivity(new Intent(SosForumChat.this,GetLiveMessage.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
