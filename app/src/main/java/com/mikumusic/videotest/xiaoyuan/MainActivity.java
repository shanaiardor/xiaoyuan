package com.mikumusic.videotest.xiaoyuan;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String jsonstring;
    ListView listView;
    //储存数据的集合
    ArrayList<List_date> listdate;
    //http工具请求包
    HttpUtils httpUtils;
    String uri = "http://space.bilibili.com/ajax/member/getSubmitVideos?mid=5055&pagesize=30&tid=0&keyword=&page=1&_=1478497773896";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化 http工具 和 数据集合
        httpUtils = new HttpUtils();
        listdate = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String M;
                //根据uri获取json
                M = httpUtils.getInputStringFromUri(uri);
                Message message = new Message();
                message.what = 0x1;
                message.obj = M;
                handler.sendMessage(message);
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x1:
                    jsonstring = (String)msg.obj;

                    //解析json
                    List_Date_Get();
                    listView = (ListView)findViewById(R.id.lv);
                    listView.setAdapter(new ListViewAdapter(MainActivity.this,listdate));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listdate.get(position).getAid();
                            Toast.makeText(MainActivity.this,listdate.get(position).getAid() , Toast.LENGTH_SHORT).show();
                            System.out.printf(listdate.get(position).getAid());

                        }
                    });
            }
            super.handleMessage(msg);
        }
    };

    //从json里解析出数据封装到集合里
    private void List_Date_Get() {
        try {
            JSONObject videojson = new JSONObject(jsonstring);
            JSONArray datevlist = videojson.getJSONObject("data").getJSONArray("vlist");
            for (int i = 0; i < datevlist.length(); i++) {
                //图片get方法
                List_date dlistiten = new List_date();
                datevlist.getJSONObject(i).getString("pic");
                dlistiten.setIcon(datevlist.getJSONObject(i).getString("pic"));
                dlistiten.setTitle(datevlist.getJSONObject(i).getString("title"));
                dlistiten.setBody(datevlist.getJSONObject(i).getString("description"));
                dlistiten.setAid(datevlist.getJSONObject(i).getString("aid"));
                listdate.add(dlistiten);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
