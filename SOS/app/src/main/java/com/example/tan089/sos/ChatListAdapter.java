package com.example.tan089.sos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by tan089 on 8/24/2017.
 */

public class ChatListAdapter extends BaseAdapter{
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    //Array that holds data for chat list
    private ArrayList<DataSnapshot> mSnapshotList;

    private ChildEventListener mListener = new ChildEventListener() {
        //new chat message display
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {
        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mListener);
        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }


    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public SosMessage getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        //get json value from snapshot
        return snapshot.getValue(SosMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);
            //Store all the data in the row into this viewholder
            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);
        }
        final  SosMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

       boolean isMe = message.getAuthor().equals(mDisplayName);
        setChatRowAppearance(isMe, holder);

        String author = message.getAuthor();
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);
        return convertView;
    }

    public void setChatRowAppearance(boolean isItMe, ViewHolder holder) {
        //User send messages
        if (isItMe) {
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);
           // holder.body.setBackgroundResource(R.drawable.bubble2);
        }
        //someone else messages
        else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            //holder.body.setBackgroundResource(R.drawable.bubble1);
        }
        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);
    }
    public void cleanup() {
        mDatabaseReference.removeEventListener(mListener);
    }
}
