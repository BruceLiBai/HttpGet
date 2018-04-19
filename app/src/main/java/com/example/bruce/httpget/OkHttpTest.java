package com.example.bruce.httpget;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JTL526922 on 2017/11/23.
 */

public class OkHttpTest {

    private final OkHttpClient client = new OkHttpClient();

    //同步get
    public void okHttpGet(final String url){

        new Thread(){
            @Override
            public void run() {
                //super.run();

                Request request = new Request.Builder().url(url).header("name","bruce").build();
                Call call = client.newCall(request);
                Response response = null;

                try {
                    response = call.execute();
                    String result = response.body().string();
                    System.out.println("百度返回：" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }.start();

    }


    //post
    public void okHttpPost(String url){

        //String url = "https://www.baidu.com/";
        //OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("键", "值")
                .add("键", "值") .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println("返回:" + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    //okPost异步请求
    public void okPost(String url, final Handler handler) throws JSONException {

        //OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。

        /*
        //表单传参
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("username","zhangsan");//传递键值对参数

        Request request = new Request.Builder()//创建Request 对象。
        .url(url)
        .post(formBody.build())//传递请求体
        .build();
        */

        //json
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        JSONObject json = new JSONObject();
        json.put("username","bruce");
        json.put("password","123");
        String jsonStr = json.toString();//json数据.
        RequestBody body = RequestBody.create(JSON, jsonStr);

        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(body)//传递请求体
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                System.out.println("-----" + response.code());
                if(response.isSuccessful()){


                    String result = response.body().string();

                    System.out.println("返回的结果:" + result);

                    try {
                        JSONObject jsonResult = new JSONObject(result);

                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.arg1 = 1;
                        msg.arg2 = 1;
                        //存储对象
                        msg.obj = jsonResult;

                        handler.sendMessage(msg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        });//回调方法的使用
    }







}
