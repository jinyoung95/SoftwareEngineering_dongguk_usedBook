package com.se_team8.dongguk_usedbook_market;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eomji(2014112041 김엄지) on 2016-11-10.
 * Created by Jinyoung(2014112057 최진영) on 2016-11-29.
 */

/** 로그인 화면 Activity */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etId,etPassword;
    Button btnPost;
    private static Person person = new Person();
    static String strJson = "";
    ProgressDialog dialog;

    private static final String TAG = "LoginActivity";
    private static String mainURL = "http://softwareengineeringtp.azurewebsites.net/";
    private static String phoneToken; // 휴대폰 기기에 대한 토큰
    private static String userToken; // 사용자의 토큰

    /**
     * 사용자가 입력한 값 받아오고 로그인 버튼 클릭 listener 실행
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // 타이틀이 안보이도록 함

        // 입력한 값 받아온다
        etId = (EditText) findViewById(R.id.userIdInput);
        etPassword = (EditText) findViewById(R.id.userPwdInput);
        btnPost = (Button) findViewById(R.id.loginButton);

        // 로그인 버튼 클릭 listener
        btnPost.setOnClickListener(this);
    }

    /**
     * 로그인 버튼을 클릭하면 휴대폰 기기에 대한 토큰을 받고 로그인을 확인하는 스레드를 실행
     * @param view
     * @author 최진영
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.loginButton:      //로그인버튼 클릭
                if(!validate()) // 입력한 데이터가 없으면 메시지 출력
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                else {
                    // Handle possible data accompanying notification message.
                    if (getIntent().getExtras() != null) {
                        for (String key : getIntent().getExtras().keySet()) {
                            Object value = getIntent().getExtras().get(key);
                            Log.d(TAG, "Key: " + key + " Value: " + value);
                        }
                    }
                    FirebaseMessaging.getInstance().subscribeToTopic("news");

                    // Get token
                    phoneToken = FirebaseInstanceId.getInstance().getToken();

                    // call AsynTask to perform network operation on separate thread
                    HttpAsyncTask httpTask = new HttpAsyncTask(LoginActivity.this);
                    httpTask.execute(mainURL+"loginCheck/", etId.getText().toString(), etPassword.getText().toString());
                }
                break;
        }
    }

    /**
     * 로그인 수행
     * @param url - 요청할 서버의 주소
     * @return - 사용자 이름, 회원가입여부(overlap) 정보 반환
     * @author - 최진영
     * */
    public static String login(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // id와 pw를 JSONObject 형식으로 변환해 저장
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", person.getId().toString());
            jsonObject.put("PW", person.getPassword().toString());
            // JSONObject를 String형식으로 변한
            json = jsonObject.toString();

            // header 설정
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            httpCon.setDoOutput(true);  // POST방식으로 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // 서버로부터 응답을 받겠다는 옵션

            //checkConnection(httpCon); // 디버깅용

            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream()); // 서버에 보낼 객체 생성
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            // 서버로부터 받은 응답 읽기
            try {
                //서버에서 읽어오는 객체 생성
                is = httpCon.getInputStream();
                if(is != null)
                    result = convertInputStreamToString(is);     //응답을 String형식으로 저장
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 회원가입 수행
     * @param url - 연결할 서버의 주소
     * @return - 회원가입 여부 정보 반환
     * @author - 김엄지
     * */
    public static String signUp(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // username, pw, id를 JSONObject 형식으로 변환해 저장
            JSONObject signUpJsonObj = new JSONObject();
            signUpJsonObj.put("username",person.getId().toString());
            signUpJsonObj.put("password", "1");         // 편의상 1로 설정
            signUpJsonObj.put("name", person.getName().toString());
            signUpJsonObj.put("token", phoneToken);
            json = signUpJsonObj.toString();

            // header 설정정
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            httpCon.setDoOutput(true);  // POST방식으로 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // 서버로부터 응답을 받겠다는 옵션

            //checkConnection(httpCon);

            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream()); // 서버에 보낼 객체 생성
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            // 서버로부터 받은 응답 읽기
            try {
                is = httpCon.getInputStream();  //서버에서 읽어오는 객체 생성
                if(is != null)
                    result = convertInputStreamToString(is);    // 응답을 string형식으로 저장
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 토큰받기
     * @param - 연결할 서버의 주소
     * @return - 서버로부터 받아온 사용자의 token값
     * @author - 김엄지
     * */
    public static String getTocken(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // username, password를 JSONObject 형식으로 변환해 저장
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", person.getId().toString());
            jsonObject.put("password", "1");
            json = jsonObject.toString();
            // header 설정
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            httpCon.setDoOutput(true);  // POST방식으로 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // 서버로부터 응답을 받겠다는 옵션

            //checkConnection(httpCon);

            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream()); // 서버에 보낼 객체 생성
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            // 서버로부터 응답 읽기
            try {
                is = httpCon.getInputStream();  //서버에서 읽어오는 객체 생성
                if(is != null)
                    result = convertInputStreamToString(is);    // 응답을 String형식으로 변환
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    /** 회원가입 위한 스레드
     * @author 김엄지
     * */
    private class HttpSignUpAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;
        HttpSignUpAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        /**
         *  스레드의 메인부분 (회원가입 수행)
         *  @param urls - urls[0]: 연결할 서버 주소
         *                 urls[1]: 사용자 아이디
         *                 urls[2]: 사용자 비밀번호 (편의상 항상 1)
         *                 urls[3]: 사용자 이름
         *                 urls[4]: 기기의 토큰
         *  @return
         *  */
        @Override
        protected String doInBackground(String... urls) {
            person.setId(urls[1]);
            person.setPassword(urls[2]);
            person.setName(urls[3]);
            phoneToken=urls[4];

            return signUp(urls[0]);
        }
        /**
         *  doInBackground(메인스레드)가 끝나면 호출됨
         *  @param result
         *  */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpTokenAsyncTask tokenTask = new HttpTokenAsyncTask(LoginActivity.this);
                    tokenTask.execute(mainURL+"api-token-auth/", person.getId(),person.getPassword());
                }
            });
        }
    }

    /** 로그인 위한 스레드
     * @author 김엄지
     * */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;
        HttpAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        /** 메인스레드 실행하기 전에 수행됨 (로딩 화면 표시) */
        protected  void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(mainAct);
            dialog.show();  // 로딩 화면 표시
        }

        /**
         *  스레드의 메인부분 (로그인 수행)
         *  @param urls - urls[0]: 연결할 서버 주소
         *                 urls[1]: 사용자 id
         *                 urls[2]: 사용자 비밀번호
         *  @return - 사용자 이름, 회원가입 여부(overlap)
         *  */
        @Override
        protected String doInBackground(String... urls) {
            person.setId(urls[1]);
            person.setPassword(urls[2]);

            return login(urls[0]);
        }
        /** doInBackground(메인스레드)가 끝나면 호출됨
         * (정보 맞으면 토큰 받아오는 스레드 실행, 로그인 정보 틀리면 에러메시지, 회원가입 되지 않았으면 회원가입 스레드 실행)
         * @param result - 사용자 이름, 회원가입 여부
         * */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(!strJson.contains("error")) {    // 로그인 정보가 올바르면
                            JSONObject json = new JSONObject(strJson);
                            person.setName(json.getString("username"));
                            person.setOverlap(json.getString("overlap"));

                            if(person.getOverlap().compareTo("0")==0) { // 회원가입 되어있지 않으면
                                // 회원가입 스레드 실행
                                HttpSignUpAsyncTask signUpTask = new HttpSignUpAsyncTask(LoginActivity.this);
                                signUpTask.execute(mainURL+"signup/", person.getId(),person.getPassword(), person.getName(), phoneToken);
                            }
                            else{ // 회원가입 되어있으면
                                // 토큰 받는 스레드 실행
                                HttpTokenAsyncTask tokenTask = new HttpTokenAsyncTask(LoginActivity.this);
                                tokenTask.execute(mainURL+"api-token-auth/", person.getId(),person.getPassword());
                            }
                        }
                        else{   // 로그인 정보가 틀리면 메시지 출력
                            dialog.dismiss();
                            Toast.makeText(mainAct, "로그인 실패! ID나 PW를 확인해주세요!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    /** 토큰받기 위한 스레드
     * @author 김엄지
     * */
    private class HttpTokenAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;
        HttpTokenAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        /**
         *  스레드의 메인부분 (로그인 수행)
         *  @param urls - urls[0]: 연결할 서버 주소
         *                 urls[1]: 사용자 id
         *                 urls[2]: 사용자 비밀번호
         *  @return - 사용자의 토큰
         *  */
        @Override
        protected String doInBackground(String... urls) {
            person.setId(urls[1]);
            person.setPassword(urls[2]);

            return getTocken(urls[0]);
        }
        /**
         *  doInBackground(메인스레드)가 끝나면 호출됨
         * @param result - 사용자의 토큰
         * */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            userToken = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject json = new JSONObject(userToken);
                        userToken = json.getString("token");

                        onTokenRefresh(); // 기기의 토큰 refresh
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 푸시 알림을 위해 휴대폰 기기에 대한 토큰을 refresh
     * @author 최진영
     */
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Instance ID token을 서버에 전송하는 스레드 실행
     * @author 최진영
     * @param newToken new token.
     */
    private void sendRegistrationToServer(String newToken) {
        phoneToken = newToken;

        // 휴대폰 기기의 토큰을 서버에 전송하는 스레드 실행
        LoginActivity.HttpPushAsyncTask httpPushTask = new LoginActivity.HttpPushAsyncTask(LoginActivity.this);
        httpPushTask.execute(mainURL+"receive/token/"+person.getId()+"/", phoneToken);
    }

    /**
     * push를 위해 휴대폰 기기의 토큰을 서버에 보내기
     * @author 최진영
     * @param url - 연결할 서버의 주소
     * @return - 토큰 전송 여부 정보 반환
     * */
    public static String pushRequest(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username",person.getId().toString());
            jsonObject.put("password", "1");
            jsonObject.put("token", phoneToken);

            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // header설정 (Request Body 전달시 application/json로 서버에 전달.)
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Authorization","JWT "+ userToken); // 헤더에 token값 전달
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            //checkConnection(httpCon);
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기
            checkConnection(httpCon);

            //서버에서 읽어오는 객체 생성
            is = httpCon.getInputStream();
            if(is != null)
                // convert inputstream to string
                result = convertInputStreamToString(is);
            else
                result = "Did not work!";
            httpCon.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * push를 위해 서버에 기기의 토큰을 보내는 스레드
     * @author 최진영
     * */
    private class HttpPushAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;

        HttpPushAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        /**
         *  스레드의 메인부분 (토큰을 서버에 전송)
         *  @param urls - urls[0]: 연결할 서버 주소
         *                 urls[1]: 휴대폰 기기의 토큰
         *  @return - 전송 여부
         *  */
        @Override
        protected String doInBackground(String... urls) {
            phoneToken=urls[1];
            return pushRequest(urls[0]);
        }

        /**
         * doInBackground(메인스레드)가 끝나면 호출됨
         * @param result - 서버 연결 결과
         * */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mainAct, "로그인되었습니다!", Toast.LENGTH_LONG).show();
                    // 사용자 정보와 토큰 HomeActivity로 전달
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("username", person.getName());
                    intent.putExtra("userID", person.getId());
                    intent.putExtra("token", userToken);
                    dialog.dismiss();
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * 입력텍스트를 모두 입력했는지 확인
     * @return - 모두 입력했으면 true, 입력하지 않았으면 false를 반환
     * @author - 최진영
     * */
    private boolean validate(){
        if(etId.getText().toString().trim().equals(""))
            return false;
        else if(etPassword.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    /**
     * InputStream을 String으로 변환 (서버에서 받은 값을 String으로 변환)
     * @param inputStream - 서버에서 받은 값을 저장한 InputStream 객체
     * @return - 서버에서 받은 값을 String으로 변환한 결과
     * @author - 최진영
     * */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    /**
     * 연결이 제대로 이루어졌는지 확인하고 그렇지 않다면 오류메시지 출력
     * @param httpCon - 연결한 서버의 HttpURLConnection 객체체
     * @author 김엄지
     * */
    private static void checkConnection(HttpURLConnection httpCon){
        //연결 확인
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int code = 0;
        try {
            code = httpCon.getResponseCode(); // 실질적으로 웹서버에 접속하여 요청을 보내고 응답을 수신하는 시점
            if(code>=400){      // 서버오류이면
                bos.reset();
                InputStream err = httpCon.getErrorStream();
                while(true){
                    int readlen = err.read(buf);
                    if(readlen<1)
                        break;
                    bos.write(buf,0,readlen);
                }
                String output = new String(bos.toByteArray(), "utf-8");
                System.err.println(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}