package com.xiaok.androidqzone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;


import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class VideoBrowerActivity extends AppCompatActivity {

    private List<VideoBrower> aDate;
    private int[]videoAvatars;
    private String[]usernames;
    private String[]videoDates;
    private String[]videoDescripation;
    private String[]videoPaths;
    private String[]videoPosition;

    private ListView lv_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_brower);

        lv_video = findViewById(R.id.video_listview);


        videoAvatars = new int[]{R.drawable.head_picture,R.mipmap.video_avatar_02,R.mipmap.video_avatar_03,R.mipmap.video_avatar_04};
        usernames = new String[]{"xiaok","小张","王小明","李晓华"};
        videoDates = new String[]{"刚刚","09月17日10:12","08月22日18:15","08月22日17:42"};
        videoDescripation = new String[]{"#测试1.1# 童年卢本伟","#测试1.2# 川普","#测试1.3# 李云龙","#测试1.4# 奥利给"};
        videoPaths = new String[]{"https://www.bilibili.com/video/av80588467",
                "https://www.bilibili.com/video/av77275486?spm_id_from=333.5.b_6b696368696b755f6775696465.18",
                "https://www.bilibili.com/video/av80721495",
                "https://www.bilibili.com/video/av75889185?spm_id_from=333.5.b_6b696368696b755f6d6164.4"};
        videoPosition = new String[]{"北京市朝阳区","郑州市中原区","郑州市中原区","郑州市中原区"};

        aDate = new LinkedList<>();
        for (int i=0;i<videoAvatars.length;i++){
            aDate.add(new VideoBrower(videoAvatars[i],usernames[i],videoDates[i],
                    videoDescripation[i],videoPaths[i],videoPosition[i]));
        }

        lv_video.setAdapter(new VideoBrowerAdapter((LinkedList<VideoBrower>)aDate,VideoBrowerActivity.this));
        lv_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }


}
