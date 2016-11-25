package com.se_team8.dongguk_usedbook_market;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Juhyeon on 2016-11-10.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //TextView tvIsConnected, tvResponse;
    EditText etId,etPassword;
    Button btnPost;
    Person person;
    static    String strJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 입력한 값 받아온다
        etId = (EditText) findViewById(R.id.userIdInput);
        etPassword = (EditText) findViewById(R.id.userPwdInput);
        btnPost = (Button) findViewById(R.id.loginButton);

        // add click listener to Button "POST"
        btnPost.setOnClickListener(this);

    }

    public static String POST(String url, Person person){
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
            //json = String.format("ID=%s&PW=%s",person.getId().toString(),person.getPassword().toString());

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
//            byte[] buf = new byte[4096];
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            int code = httpCon.getResponseCode();
//            if(code>=400){
//                bos.reset();
//                InputStream err = httpCon.getErrorStream();
//                while(true){
//                    int readlen = err.read(buf);
//                    if(readlen<1)
//                        break;
//                    bos.write(buf,0,readlen);
//                }
//                String output = new String(bos.toByteArray(), "utf-8");
//                System.err.println(output);
//            }

            // Request Body에 Data를 담기위해 OutputStream 객체를 생성.
            // 서버에 보낼 객체 생성
            DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
            out.write(json.getBytes("utf-8"));   // 서버에 작성
            out.flush(); //객체 닫기

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
                    httpTask.execute("http://softwareengineeringtp.azurewebsites.net/loginCheck/", etId.getText().toString(), etPassword.getText().toString());
                }
                break;
        }
    }
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

            return POST(urls[0],person);
        }
        // doInBackground(메인스레드)가 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mainAct, "로그인되었습니다!", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray json = new JSONArray(strJson);
                        //mainAct.tvResponse.setText(json.toString(1));
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
}
