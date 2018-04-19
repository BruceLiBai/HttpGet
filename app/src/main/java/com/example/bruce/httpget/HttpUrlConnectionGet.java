package com.example.bruce.httpget;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 *
 */

public class HttpUrlConnectionGet {


    private static final String TAG = "HttpUrlConnectionGet";

    private JSONObject jsonResult;

    public JSONObject get(final String urlStr, final String data, final TextView tv){

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    //对data进行编码
                    //URLDecoder.decode(data,"UTF-8");
                    //数据与url进行拼接
                    //URL url = new URL(urlStr + "?" + data);
                    URL url = new URL(urlStr + "?" + data);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10 * 1000);
                    conn.setUseCaches(true);
                    conn.setRequestProperty("Charset","UTF-8");

                    conn.addRequestProperty("Connection","Kepp-Alive");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    //开始连接
                    conn.connect();
                    int code = conn.getResponseCode();
                    System.out.println("连接状态码：" + code);
                    if (code == 200) {

                        Log.e(TAG,"get连接成功");
                        InputStream is = conn.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        String result;
                        while ((result = br.readLine()) != null){

                            //将结果转为json
                           jsonResult  = new JSONObject(result);
                            tv.setText(jsonResult.toString());
                        }

                    }else {

                        Log.e(TAG,"get连接失败");
                    }

                    //关闭连接
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //将结果json对象返回
        return jsonResult;
    }





}
