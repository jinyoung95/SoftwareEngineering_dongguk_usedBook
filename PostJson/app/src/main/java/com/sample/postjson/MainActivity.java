package com.sample.postjson;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//http://127.0.0.1:8000/loginCheck/
//curl -X POST -d "ID=2014112041&PW=apfhddlek!13" http://ussoftwaretp.azurewebsites.net/loginCheck/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvIsConnected, tvResponse;
    EditText etId,etPassword;
    Button btnPost;
    Person person;
    static    String strJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 입력한 값 받아온다
        etId = (EditText) findViewById(R.id.etId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnPost = (Button) findViewById(R.id.btnPost);
        tvResponse = (TextView) findViewById(R.id.tvResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not
        if(isConnected()){
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
        else{
            tvIsConnected.setText("You are NOT conncted");
        }

        // add click listener to Button "POST"
        btnPost.setOnClickListener(this);

    }

    public static String POST(String url, Person person){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();
            int code = httpCon.getResponseCode();
            System.out.println(code);

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", person.getId());
            jsonObject.accumulate("password", person.getPassword());

            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            // 타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            // Request Body에 Data를 담기위해 OutputStream 객체를 생성.
            // 서버에 보낼 객체 생성
            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("utf-8"));   // 서버에 작성
            os.flush(); //객체 닫기
            // receive response as inputStream
            try {
                //서버에서 읽어오는 객체 생성
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnPost:
                if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                else {
                    // call AsynTask to perform network operation on separate thread
                    HttpAsyncTask httpTask = new HttpAsyncTask(MainActivity.this);
                    //httpTask에 data 넘겨줌
                    httpTask.execute("http://127.0.0.1:8000/loginCheck/", etId.getText().toString(), etPassword.getText().toString());
                    //httpTask.execute("http://hmkcode.appspot.com/jsonservlet", etName.getText().toString(), etCountry.getText().toString(), etTwitter.getText().toString());
                }
                break;
        }

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private   MainActivity mainAct;

        HttpAsyncTask(MainActivity mainActivity) {
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
        // onPostExecute displays the results of the AsyncTask.
        // 스레드 끝나면 호출됨
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mainAct, "Received!", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray json = new JSONArray(strJson);
                        mainAct.tvResponse.setText(json.toString(1));
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
