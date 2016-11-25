package com.se_team8.dongguk_usedbook_market;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Juhyeon on 2016-11-24.
 */

public class MessageDataSource {
    private static final Firebase sRef = new Firebase("https://ppuu-41926.firebaseio.com/");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG="MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER="sender";

    public static void saveMessage(Message message, String convoId){
        Date date = message.getmDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT,message.getmText());
        msg.put(COLUMN_SENDER,"XYZ");
        sRef.child(convoId).child(key).setValue(msg);
    }

    public static MessageListener addMessagesListener(String convoId, final MessagesCallbacks callbacks){
        MessageListener listener = new MessageListener(callbacks);
        sRef.child(convoId).addChildEventListener(listener);
        return listener;

    }


    public static void stop(MessageListener listener) {
        sRef.removeEventListener(listener);
    }

    public static class MessageListener implements ChildEventListener {

        private MessagesCallbacks callbacks;
        public MessageListener(){

        }
        public MessageListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String, String> msg = (HashMap<String, String>) dataSnapshot.getValue();
            Message message = new Message();
            message.setmSender(msg.get(COLUMN_SENDER));
            message.setmText(msg.get(COLUMN_TEXT));

            try {
                message.setmDate(sDateFormat.parse(dataSnapshot.getKey()));
            }catch (ParseException e){
                e.printStackTrace();
            }

            if(callbacks != null){
                callbacks.onMessageAdded(message);
            }
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
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    public interface MessagesCallbacks{
        public void onMessageAdded(Message message);
    }
}