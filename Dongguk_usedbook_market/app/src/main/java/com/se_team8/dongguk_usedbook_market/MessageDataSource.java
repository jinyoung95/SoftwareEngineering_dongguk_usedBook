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
/**
 * https://github.com/ashokslsk/Firebase  소스 일부 참고함. --> 데이터 통신, 수신/송신 메세지 설정 부분
 * 파이어 베이스를 이용한 메세지 전송 클래스
 * */
public class MessageDataSource {
<<<<<<< HEAD
    private static final Firebase sRef = new Firebase("https://ppuu-41926.firebaseio.com/");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG="MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER="sender";
    private static final String COLUMN_READ="read";
    private String mSender;

    public static void saveMessage(Message message, String convoId, String mRecipient){
        Date date = message.getmDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT,message.getmText());
       // msg.put(COLUMN_SENDER, mSender);
        msg.put(COLUMN_SENDER, convoId);
        msg.put(COLUMN_READ,"0");
        sRef.child(convoId).child(mRecipient).child(key).setValue(msg);
        sRef.child(mRecipient).child(convoId).child(key).setValue(msg);

        msg.put(COLUMN_READ,"1");
        sRef.child(convoId).child(mRecipient).child(key).setValue(msg);
    }

    public static MessageListener addMessagesListener(String convoId, String mRecipient, final MessagesCallbacks callbacks) {
        MessageListener listener = new MessageListener(callbacks);
        sRef.child(convoId).child(mRecipient).addChildEventListener(listener);
        return listener;
    }
    public Firebase getsRef(){
        return sRef;
=======
    private static final Firebase sRef = new Firebase("https://ppuu-41926.firebaseio.com/"); //파이어베이스 주소
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss"); //데이트 포맷 --> 디렉토리 주소로 이용할 것
    private static final String COLUMN_TEXT = "text"; //메세지를 저장할 데이터명
    private static final String COLUMN_SENDER="sender"; //수신자를 저장할 데이터명

    //메세지를 저장하는 메소드
    public static void saveMessage(Message message, String convoId, String mRecipient){
        Date date = message.getmDate(); //날짜를 설정
        String key = sDateFormat.format(date); //데이터 포맷을 이용한 키를 설정하여 채팅디렉토리 이름지정
        HashMap<String, String> msg = new HashMap<>(); //메세지를 해시맵으로 정의

        msg.put(COLUMN_TEXT,message.getmText()); //해당 데이터 명에 메세지 입력
        msg.put(COLUMN_SENDER, convoId);//해당 데이터명에 수신자 입력

        sRef.child(convoId).child(mRecipient).child(key).setValue(msg); //데이터를 저장할 위치.
        sRef.child(mRecipient).child(convoId).child(key).setValue(msg); //개개인이 누구랑 대화했는지 트리형식으로 디렉토리 설정하게 함
    }

    //메세지 받는 메소드
    public static MessageListener addMessagesListener(String convoId, String mRecipient, final MessagesCallbacks callbacks) {
        MessageListener listener = new MessageListener(callbacks);
        sRef.child(convoId).child(mRecipient).addChildEventListener(listener); //해당 디렉토리에서 추가된 메세지 수신
        return listener;
>>>>>>> Eomji
    }
    //메세지를 제거하는 메소드
    public static void stop(MessageListener listener) {
        sRef.removeEventListener(listener);
    }
    //메세지 리스너 클래스
    public static class MessageListener implements ChildEventListener {

        private MessagesCallbacks callbacks;

        public MessageListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }

        @Override //추가되는 메세지 설정
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

        //리스너 기본 메소드들
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) { }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onCancelled(FirebaseError firebaseError) { }
    }

    public interface MessagesCallbacks{
        public void onMessageAdded(Message message);
    }
}
