package com.se_team8.dongguk_usedbook_market;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by Eomji on 2016-11-10.
 */

//curl -X POST -d "username=(사용자로부터 입력받은 학번)&password=1&name=(서버로부터 받은 이름)" http://softwareengineeringtp.azurewebsites.net/signup/
// username=학번&password=1  http://softwareengineeringtp.azurewebsites.net/api-token-auth/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etId,etPassword;
    Button btnPost;
    private static Person person;
    static String strJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 입력한 값 받아온다
        etId = (EditText) findViewById(R.id.userIdInput);
        etPassword = (EditText) findViewById(R.id.userPwdInput);
        btnPost = (Button) findViewById(R.id.loginButton);

        // 로그인 버튼 클릭 listener
        btnPost.setOnClickListener(this);

    }

    /** 로그인 */
    public static String login(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", person.getId().toString());
            jsonObject.put("PW", person.getPassword().toString());

            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // Set some headers to inform server about the type of the content
            // 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");
            //httpCon.setUseCaches(false);
            //httpCon.setReadTimeout(2000);

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            //연결 확인
            //checkConnection(httpCon);

            // Request Body에 Data를 담기위해 OutputStream 객체를 생성.
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            // receive response as inputStream
            try {
                //서버에서 읽어오는 객체 생성
                is = httpCon.getInputStream();
                if(is != null)
                    // convert inputstream to string
                    result = convertInputStreamToString(is);
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

    /** 회원가입 */
    public static String signUp(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject signUpJsonObj = new JSONObject();
            signUpJsonObj.put("username",person.getId().toString());
            signUpJsonObj.put("password", "1");
            signUpJsonObj.put("name", person.getName().toString());

            // convert JSONObject to JSON to String
            json = signUpJsonObj.toString();

            // Set some headers to inform server about the type of the content
            // 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            //checkConnection(httpCon);

            // Request Body에 Data를 담기위해 OutputStream 객체를 생성.
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            // receive response as inputStream
            try {
                //서버에서 읽어오는 객체 생성
                is = httpCon.getInputStream();
                if(is != null)
                    // convert inputstream to string
                    result = convertInputStreamToString(is);
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

    /** 토큰받기 */
    public static String getTocken(String url){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", person.getId().toString());
            jsonObject.put("password", "1");

            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // Set some headers to inform server about the type of the content
            // 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            //연결 확인
            //checkConnection(httpCon);

            // Request Body에 Data를 담기위해 OutputStream 객체를 생성.
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

            //checkConnection(httpCon);

            // receive response as inputStream
            try {
                //서버에서 읽어오는 객체 생성
                is = httpCon.getInputStream();
                if(is != null)
                    // convert inputstream to string
                    result = convertInputStreamToString(is);
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

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.loginButton:
                if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                else {
                    // call AsynTask to perform network operation on separate thread
                    HttpAsyncTask httpTask = new HttpAsyncTask(LoginActivity.this);
                    //httpTask에 data 넘겨줌
                    //httpTask.execute("http://softwareengineeringtp.azurewebsites.net/loginCheck/", etId.getText().toString(), etPassword.getText().toString());

                    //테스트 위한 임시 코드 4줄
                    //HttpSignUpAsyncTask test = new HttpSignUpAsyncTask(LoginActivity.this);
                    //test.execute("http://softwareengineeringtp.azurewebsites.net/signup/", "2014112057", "1","최진영");
                    HttpTokenAsyncTask test = new HttpTokenAsyncTask(LoginActivity.this);
                    test.execute("http://softwareengineeringtp.azurewebsites.net/api-token-auth/",  etId.getText().toString(), "1");
                }
                break;
        }
    }

    /** 회원가입 위한 스레드 */
    private class HttpSignUpAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;
        HttpSignUpAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {

            person = new Person();
            person.setId(urls[1]);
            person.setPassword(urls[2]);
            person.setName(urls[3]);

            return signUp(urls[0]);
        }
        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpTokenAsyncTask tokenTask = new HttpTokenAsyncTask(LoginActivity.this);
                    tokenTask.execute("http://softwareengineeringtp.azurewebsites.net/api-token-auth/", person.getId(),person.getPassword());
                }
            });
        }
    }

    /** 로그인 위한 스레드 */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;
        HttpAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {

            person = new Person();
            person.setId(urls[1]);
            person.setPassword(urls[2]);

            return login(urls[0]);
        }
        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(strJson.compareTo("error")!=0) {
                            JSONObject json = new JSONObject(strJson);
                            person.setName(json.getString("username"));
                            person.setOverlap(json.getString("overlap"));

                            if(person.getOverlap().compareTo("0")==0) {
                                HttpSignUpAsyncTask signUpTask = new HttpSignUpAsyncTask(LoginActivity.this);
                                signUpTask.execute("http://softwareengineeringtp.azurewebsites.net/signup/", person.getId(),person.getPassword(), person.getName());
                            }
                            else{
                                HttpTokenAsyncTask tokenTask = new HttpTokenAsyncTask(LoginActivity.this);
                                tokenTask.execute("http://softwareengineeringtp.azurewebsites.net/api-token-auth/", person.getId(),person.getPassword());
                            }
                        }
                        else{
                            Toast.makeText(mainAct, "로그인 실패!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    /** 토큰받기 위한 스레드 */
    private class HttpTokenAsyncTask extends AsyncTask<String, Void, String> {
        private LoginActivity mainAct;
        private String token = "";
        HttpTokenAsyncTask(LoginActivity mainActivity) {
            this.mainAct = mainActivity;
        }

        // 스레드의 메인부분 (데이터를 처리하는 부분)
        @Override
        protected String doInBackground(String... urls) {

            person = new Person();
            person.setId(urls[1]);
            person.setPassword(urls[2]);

            return getTocken(urls[0]);
        }
        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            token = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject json = new JSONObject(token);
                        token = json.getString("token");
                        Toast.makeText(mainAct, "로그인되었습니다!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("username", person.getName());
                        intent.putExtra("userID", person.getId());
                        intent.putExtra("token", token);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean validate(){
        if(etId.getText().toString().trim().equals(""))
            return false;
        else if(etPassword.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private static void checkConnection(HttpURLConnection httpCon){
        //연결 확인
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int code = 0;
        try {
            code = httpCon.getResponseCode();
            if(code>=400){
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
