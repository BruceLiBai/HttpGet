package com.example.bruce.httpget;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpUrlConnectionPost
 */

public class HttpUrlConnectionPost {

    private static final String TAG = "HttpUrlConnectionPost";

    private Object obj;

    public Object post(final String urlStr, final Object object,final TextView tv){

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5*1000);
                    conn.setReadTimeout(5*1000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestProperty("Accept","application/json");
                    //json
                    conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");

                    //序列输出设置
                    //conn.setRequestProperty("Content-type","application/x-java-serialized-object");
                    //开始连接
                    conn.connect();
                    //输出对象
                    /*
                    ObjectOutputStream oos = new ObjectOutputStream(conn.getOutputStream());
                    oos.writeObject(object);
                    oos.flush();
                    oos.close();
                    */

                    //输出json
                    JSONObject json = new JSONObject();
                    json.put("name","bruce");
                    json.put("age",20);

                    PrintWriter out = new PrintWriter(conn.getOutputStream());
                    out.write(json.toString());

                    out.flush();
                    out.close();

                    int code = conn.getResponseCode();
                    System.out.println("code状态码：" + code);
                    if (code == 200) {

                        Log.e(TAG,"post连接成功");
                        InputStream is = conn.getInputStream();
/*                        ObjectInputStream ois = new ObjectInputStream(is);
                        Users users = (Users) ois.readObject();
                        tv.setText(users.getName() + ":::" + users.getAge());

                        obj = ois;
                        ois.close();
                        is.close();*/

                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        String result;
                        while ((result = br.readLine()) != null){

                            //将结果转为json
                            //tv.setText(new JSONObject(result).toString());
                            tv.setText(result);
                        }


                    }else {

                        Log.e(TAG,"post连接失败");
                    }

                    //关闭连接
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //将结果对象返回
        return obj;
    }



}
