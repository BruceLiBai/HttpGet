package com.example.bruce.httpget;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 封装传输数据类：HttpUrlConnectionPostJson
 * 使用HpptUrlConnection  post
 * 传输到服务端数据类型：json
 * 获取服务端数据类型：json
 * 特别提示：UI线程必须有成员变量Handler，然后通过msg.obj获取返回的json数值
 */

public class HttpUrlConnectionPostJson {

    /**
     * TAG
     */
    private static final String TAG = "HttpUrlConnectionPostJson";

    public static void post(final String phpServerMethod, final JSONObject json, final Handler uiHandler){

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    String urlStr = PhpServer.url + phpServerMethod;
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
                    //开始连接
                    conn.connect();
                    //输出json
                    PrintWriter out = new PrintWriter(conn.getOutputStream());
                    out.write(json.toString());
                    out.flush();
                    out.close();

                    int code = conn.getResponseCode();
                    System.out.println("code状态码：" + code);
                    if (code == 200) {

                        Log.e(TAG,"post连接成功");
                        InputStream is = conn.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        String result;
                        StringBuilder sb = new StringBuilder();
                        //读取返回数据
                        while ((result = br.readLine()) != null){

                            sb.append(result);
                        }

                        Message msg = Message.obtain();
                        //将数据存入Message
                        msg.obj = new JSONObject(sb.toString());
                        uiHandler.sendMessage(msg);

                        //关闭流文件
                        br.close();
                        isr.close();
                        is.close();

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

    }


}
