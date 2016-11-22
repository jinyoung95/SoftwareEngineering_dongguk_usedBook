package com.sample.postjson;

/**
 * Created by JinYoung on 2016-11-21.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvIsConnected, tvResponse;
    EditText etID, etPW;
    Button btnPost;
    Person person;
    static  String strJson = "";

    /*
        Get reference to the defined views in layout file
        Check for connection status
        Add click listener to the Post button
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the views
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        etID = (EditText) findViewById(R.id.etID);
        etPW = (EditText) findViewById(R.id.etPW);
        btnPost = (Button) findViewById(R.id.btnPost);
        tvResponse = (TextView) findViewById(R.id.tvResponse);

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

    /*
        Check if there is network connection or not
     */
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    /*
        This method is executed when user click on Post button
        It create HttpAsyncTask object and execute it.
    */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPost:
                if(!validate()) // ID or PW is empty
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                else {
                    // call AsynTask to perform network operation on separate thread
                    HttpAsyncTask httpTask = new HttpAsyncTask(MainActivity.this);
                    httpTask.execute("http://127.0.0.1:8000/api-token-auth/", etID.getText().toString(), etPW.getText().toString());
                }
                break;
        }
    }

    /*
        Helper method to check that data is not empty before send the request.
    */
    private boolean validate(){
        if(etID.getText().toString().trim().equals(""))
            return false;
        else if(etPW.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    /*
        Helps in creating the connection in a separate thread so the UI will not freeze.
    */
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        private   MainActivity mainAct;

        HttpAsyncTask(MainActivity mainActivity) { this.mainAct = mainActivity; }
        @Override
        protected String doInBackground(String... urls) {
            person = new Person();
            person.setID(urls[1]);
            person.setPW(urls[2]);

            return POST(urls[0], person);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(mainAct, "send Data!", Toast.LENGTH_LONG).show();
                        JSONArray json = new JSONArray(strJson);
                        mainAct.tvResponse.setText(json.toString(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


//    private String buildParameters( Map params )  throws IOException
//    {
//        StringBuilder sb = new StringBuilder();
//        for(Iterator i = params.keySet().iterator(); i.hasNext(); )
//        {
//            String key = (String)i.next();
//            sb.append( key );
//            sb.append( "=" );
//            sb.append( URLEncoder.encode((String)params.get(key), "UTF-8") );
//            if( i.hasNext() )
//                sb.append( "&" );
//        }
//        return sb.toString();
//    }

    /*
        Open Http connection.
        Create HttpPOST object passing the url.
        Create Person object & convert it to JSON string.
        Add JSON to HttpPOST, set headers & send the POST request.
        Get the response Inputstream, convert it to String and return it.
    */
    public static String POST(String url, Person person){
        OutputStream os = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        HttpURLConnection httpCon = null;
        String response = "";

        try {
            URL urlCon = new URL(url);
            httpCon = (HttpURLConnection)urlCon.openConnection();   // URL 연결
            httpCon.setRequestMethod("POST"); // 요청 방식 선택 (GET, POST)
            httpCon.setRequestProperty("Accept", "application/json");   // 서버의 Response Data를 JSON 형식의 타입으로 요청
            httpCon.setRequestProperty("Content-type", "application/json"); // 서버의 Request Body를 JSON 형식의 타입으로 요청
            httpCon.setDoOutput(true);  // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션
            httpCon.setDoInput(true);   // InputStream으로 서버로 부터 응답을 받겠다는 옵션

            // build jsonObject
         //   JSONObject jsonObject = new JSONObject();
        //    jsonObject.put("ID", person.getID());
        //    jsonObject.put("PW", person.getPW());

         //   os = httpCon.getOutputStream(); // Request Body에 Data를 담기위해 OutputStream 객체를 생성
        //    os.write(jsonObject.toString().getBytes());
            //os.write(jsonObject.toString().getBytes("UTF-8"));
            //os.write(jsonObject.toString().getBytes("euc-kr"));
       //     os.flush(); // Request Body에 Data 입력
      //      os.close();

//            Map<String, String> params = new LinkedHashMap<>();
//            params.put("ID", person.getID());
//            params.put("PW", person.getPW());
//
//            StringBuilder postData = new StringBuilder();
//            for(Map.Entry<String,String>param:params.entrySet()){
//                if(postData.length()!=0) postData.append('&');
//                postData.append(URLEncoder.encode(param.getKey(),"UTF-8"));
//                postData.append('=');
//                postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
//            }

            //String urlParameters = postData.toString();
            String urlParameters = "ID="+person.getID()+"&PW="+person.getPW();

            // String urlParameters = buildParameters(params);
            OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());
            wr.write(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = httpCon.getResponseCode();

            // receive response as inputStream
            if(responseCode == HttpURLConnection.HTTP_OK) {
                try {
                    is = httpCon.getInputStream();
                    // convert inputstream to string
                    if (is != null){
                        response = convertInputStreamToString(is);
                        System.out.println(response);
                    }
                    else
                        response = "Did not work!";
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                finally {
                    httpCon.disconnect();
                }
            }
            httpCon.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            return null;
        }

        return response;
    }

    /*
        Helper method to convert inputstream to String
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        bufferedReader.close();
        return result;
    }
}
