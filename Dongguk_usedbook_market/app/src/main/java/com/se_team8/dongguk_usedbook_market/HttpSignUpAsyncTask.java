//package com.se_team8.dongguk_usedbook_market;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import static com.se_team8.dongguk_usedbook_market.LoginActivity.POSTsignUp;
//
///**
// * Created by Eomji on 2016-11-27.
// */
//
//
//// 회원가입 위한 스레드
//public class HttpSignUpAsyncTask extends AsyncTask<String, Void, String> {
//    Person person;
//
//    private LoginActivity mainAct;
//    HttpSignUpAsyncTask(LoginActivity mainActivity) {
//        this.mainAct = mainActivity;
//    }
//
//    // 스레드의 메인부분 (데이터를 처리하는 부분)
//    @Override
//    protected String doInBackground(String... urls) {
//
//        person = new Person();
//        person.setId(urls[1]);
//        person.setPassword(urls[2]);
//        person.setName(urls[3]);
//
//        return POSTsignUp(urls[0],person);
//    }
//    // doInBackground(메인스레드)가 끝나면 호출됨
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        mainAct.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(mainAct, "로그인되었습니다!", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                intent.putExtra("username", person.getName());
//                intent.putExtra("userID", person.getId());
//                startActivity(intent);
//            }
//        });
//    }
//}
