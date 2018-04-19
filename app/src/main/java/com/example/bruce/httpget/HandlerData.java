package com.example.bruce.httpget;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JTL526922 on 2018/4/10.
 * 数据处理类
 */

public class HandlerData {

    /**
     *
     * @param handler UI 定义的handler
     * @param controller 服务端控制器
     * @param function 服务端处理函数
     * @param data json数据对象
     */
    public static void handlerData(final Handler handler,final String controller,final String function,final JSONObject data){

        //okHttp对象
        OkHttpClient client = new OkHttpClient();

        //url地址
        String url = PhpServer.url + controller + function;
        //json数据
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，

        //字符串格式json
        String jsonStr = data.toString();//json数据.
        //body
        RequestBody body = RequestBody.create(JSON, jsonStr);
        //request
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

                System.out.println("返回状态码:" + response.code());
                if(response.isSuccessful()){

                    String result = response.body().string();

                    System.out.println("返回的结果:" + result);

                    try {
                        //将字符串的json转为Json对象
                        JSONObject jsonResult = new JSONObject(result);

                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.arg1 = 1;
                        msg.arg2 = 1;
                        //存储json对象
                        msg.obj = jsonResult;
                        //发送给主线程
                        handler.sendMessage(msg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });//回调方法的使用



    }




}
