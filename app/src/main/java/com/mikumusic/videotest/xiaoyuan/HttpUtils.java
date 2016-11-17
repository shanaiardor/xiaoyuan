package com.mikumusic.videotest.xiaoyuan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/4.
 * getInputStringFromUri 获取uri返回的String
 */

public class HttpUtils {
    Context context;
    String content = "";
    Bitmap tmpBitmap = null;

    /**
     * 获取uri返回的String
     * @param uri 请求的uri
     * @return 返回get的返回
     */
    public String getInputStringFromUri(String uri){
        this.context = context;
        try {
            //初始化URL
            URL url = new URL(uri);
            //创建HttpUriConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //判断获取的应答码是否正确
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                //给予链接成功的提示
                //创建InputStreamReader
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
                int i;
                while ((i = inputStreamReader.read()) != -1){
                    content = content + (char)i;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertUnicode.convertUnicode(content);
    }

    /**
     * 获取uri返回的图片
     * @param uri 请求的uri
     * @return 返回bitmap
     */
    public Bitmap getInputPicFromUri(final String uri){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(uri);
                    //初始化URL
                    //创建HttpUriConnection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //判断获取的应答码是否正确
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        //给予链接成功的提示
                        //获取输入流 建立图片
                        tmpBitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return tmpBitmap;
    }
}
