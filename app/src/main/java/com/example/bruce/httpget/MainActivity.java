package com.example.bruce.httpget;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private MediaPlayer mediaPlayer;
    private VideoView videoView;

    private ImageView imageView;

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            if(msg.obj != null){
                JSONObject jsonObject = (JSONObject) msg.obj;
                try {
                    tv.setText(jsonObject.get("result").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //imageView.setImageBitmap((Bitmap) msg.obj);
                //Object[] arr = (Object[]) msg.obj;
                //tv.setText(arr[1].toString());
                //imageView.setImageBitmap((Bitmap) arr[0]);

            }else {
                tv.setText("未知错误发生");
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        init();

        //按钮连接服务器事件 n
        findViewById(R.id.btnGetData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("接收到请求");

                String name = et.getText().toString().trim();
                System.out.println("name=" + name);

/*          //get
            try {
                    URLDecoder.decode(name,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //String url = "http://192.168.155.1:8080/sm3/to";
                String url = "http://192.168.155.1:9096/MyWeiBo/public/index/index/index";
                String data = "name=" + name;
                HttpUrlConnectionGet hucg = new HttpUrlConnectionGet();

                hucg.get(url,data,tv);
*/
 /*           //post
              try {
                    URLDecoder.decode(name,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HttpUrlConnectionPost hucp = new HttpUrlConnectionPost();
                //String url = "http://192.168.155.1:8080/sm3/to";
                String url = "http://192.168.155.1:9096/MyWeiBo/public/index/index/index";
                //Users users = new Users(name,20);
                hucp.post(url,null,tv);
*/
                //readNet(name);

                //okHttp

                //handler
                //开启子线程

                /*
                new Thread(){
                    @Override
                    public void run() {

                        //super.run();
                        System.out.println("线程开始执行");
                        try {

                            Thread.sleep(500);

                            System.out.println("休眠结束");
                            //读取图片文件
                            InputStream strem = getAssets().open("image_08.jpg");
                            Bitmap bitmap = BitmapFactory.decodeStream(strem);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                            byte[] bytes = baos.toByteArray();


                            //将二进制文件先转为byte,再转为Bitmap,就可以绑定ImageView
                            //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes);

                            //解码服务器二进制流
                            //byte[] decodedBytes = Base64.decode(pic, Base64.DEFAULT);
                            //Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes , 0, decodedBytes.length);
                            //imageView.setImageBitmap(bitmap);



                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.arg1 = 1;
                            msg.arg2 = 1;

                            Object[] arrObj = new Object[3];
                            arrObj[0] = bitmap;
                            arrObj[1] = bytes;
                            //msg.obj = bitmap;
                            msg.obj = arrObj;

                            uiHandler.sendMessage(msg);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }.start();
                */

                /*
                InputStream strem = null;
                try {
                    strem = getAssets().open("image_08.jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(strem);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] bytes = baos.toByteArray();

                JSONObject json = null;
                //String username = et.getText().toString();
                //String password = "123";
                try {
                    //json = UserService.userLoginJson(username,password);
                    json = UserService.uploadImg(bytes.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUrlConnectionPostJson.post(PhpServer.uploadImg,json,uiHandler);
                */

                //okHttpTest
                /*
                OkHttpTest okHttpTest = new OkHttpTest();
                okHttpTest.okHttpGet(PhpServer.url+PhpServer.uploadImg);
                okHttpTest.okHttpPost(PhpServer.url+PhpServer.uploadImg);

                try {

                    okHttpTest.okPost(PhpServer.url+PhpServer.uploadImg,uiHandler);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */

                String username = name;
                String password = "123";
                String role = "admin";
                JSONObject data = JsonData.loginJson(username,password,role);
                //参数说明：参数1：handler 参数2：控制器 参数3：处理函数 参数4：json数据对象
                HandlerData.handlerData(uiHandler,PhpServer.indexController, IndexControllerFunctions.uploadImg,data);

                //tv.setText(tv.getText()+"yyy");


            }
        });


        //播放音乐
        findViewById(R.id.btnStartMisic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //1.播放raw文件夹下文件
                //mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.dzq);

                //2.播放sd卡文件
/*                mediaPlayer = new MediaPlayer();
                String path = "/sdcard/yezi.mp3";
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                //3.播放服务器文件
/*                String path = "http://192.168.155.1:8080/sm3/music/dzq.mp3";
                Uri uri = Uri.parse(path);
                mediaPlayer = MediaPlayer.create(MainActivity.this,uri);
                //播放
                mediaPlayer.start();*/


            }
        });


        //停止音乐
        findViewById(R.id.btnStopMisic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.stop();
            }
        });

        //暂停音乐
        findViewById(R.id.btnPauseMisic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.pause();
            }
        });

        //释放资源音乐
        findViewById(R.id.btnReleseMisic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.release();
            }
        });


        //播放网络视频
        findViewById(R.id.btnStartVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://192.168.155.1:8080/sm3/video/wgj.mp4";
                Uri uri = Uri.parse(url);

                //视频控制器
                videoView.setMediaController(new android.widget.MediaController(MainActivity.this));
                videoView.setVideoURI(uri);
                videoView.start();


            }
        });





    }

    /**初始化函数*/
    public void init(){

        tv = findViewById(R.id.tv);
        et = findViewById(R.id.et);
        videoView = findViewById(R.id.video);

        imageView = findViewById(R.id.iv);
    }

    /**读取网路的函数*/
    public void readNet(final String name){

        new Thread(new Runnable() {

            @Override
            public void run() {

                //urlConnection请求服务器，验证
                try {

                    URLDecoder.decode(name,"UTF-8");
                    /*
                    //1：url对象
                    URL url = new URL("http://192.168.155.1:8080/sm3/findUser?name="+name);
                    //2;url.openconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //3
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10 * 1000);
                    conn.setUseCaches(true);
                    conn.setRequestProperty("Charset","UTF-8");

                    conn.addRequestProperty("Connection","Kepp-Alive");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    */

                    //post
                    //URL url = new URL("http://192.168.155.1:8080/sm3/findUser");
                    URL url = new URL("http://192.168.155.1:8080/sm3/to");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5*1000);
                    conn.setReadTimeout(5*1000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestProperty("Charset","UTF-8");
                    //conn.setRequestProperty("Content-Type","application/json");
                    //序列输出设置
                    conn.setRequestProperty("Content-type","application/x-java-serialized-object");

                    //开始连接
                    System.out.println("开始连接.........");
                    conn.connect();
                    System.out.println("连接中.........");


                    ObjectOutputStream oos = new ObjectOutputStream(conn.getOutputStream());
                    Users users = new Users(name,20);
                    oos.writeObject(users);
                    oos.flush();
                    oos.close();
                    //4
                    int code = conn.getResponseCode();
                    System.out.println("CODE：" + code);
                    if (code == 200) {

                        //System.out.println("连接成功。。。");
                        Log.e("MainActivity","成功");
                        InputStream is = conn.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is,"utf-8");
                        BufferedReader br = new BufferedReader(isr);

                        String result;
                        while((result = br.readLine()) != null){

                            URLEncoder.encode(result,"UTF-8");

                            System.out.println("=====================服务器返回的信息：：" + result);
                            JSONObject json = new JSONObject(result);

                            System.out.println(json);
                            //将数据显示到界面
                            tv.setText(json.toString());
                        }

                    }else {

                        //System.out.println("失败代码:" + code +":连接失败。。。");
                        Log.e("MainActivity","失败");
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
