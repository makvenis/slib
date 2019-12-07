package com.sirius.aar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;

import com.sirius.slib.EndlessRecyclerOnScrollListener;
import com.sirius.slib.SimpleScrollRecycleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

   private String CHANNEL_ID="No1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wdNotify8();

        findViewById(R.id.mFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });
    }

    public void openFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent, 2008);
    }


    public void uriToFile(Uri uri,Context context){
        ContentResolver resolver = context.getContentResolver();
        Cursor mCursor = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = mCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        String s = mCursor.getString(nameIndex);

    }


    // 获取文件的真实路径
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();
            uriToFile(uri,this);
        }

    }


    public void  wdNotify8(){
        final int code = 2001;
        String id = "channel_001";
        String name = "name";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            /* 创建通知 */
            manager.createNotificationChannel(mChannel);
            //
            mBuilder = new NotificationCompat.Builder(this, id)
                    .setChannelId(id)
                    .setContentTitle("活动1")
                    .setContentText("您有一项新活动")
                    .setSmallIcon(R.mipmap.ic_launcher_round);
        } else {
            mBuilder = new NotificationCompat.Builder(this,id)
                    .setContentTitle("活动2")
                    .setContentText("您有一项新活动")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setOngoing(true)
                    .setChannelId(id);//无效
        }

        mBuilder.setProgress(100,23,false);
        manager.notify(code,mBuilder.build());

//        NotificationCompat.Builder mBuilders =new NotificationCompat
//                .Builder(this,"asd");

//        //下载以及安装线程模拟
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<100;i++){
//                    mBuilder.setProgress(100,i,false);
//                    mBuilder.notify(code,builder.build());
//                    //下载进度提示
//                    mBuilder.setContentText("下载"+i+"%");
//                    try {
//                        Thread.sleep(50);//演示休眠50毫秒
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                //下载完成后更改标题以及提示信息
//                mBuilder.setContentTitle("开始安装");
//                mBuilder.setContentText("安装中...");
//                //设置进度为不确定，用于模拟安装
//                mBuilder.setProgress(0,0,true);
//                mBuilder.notify(code,mBuilder.build());
////                manager.cancel(NO_3);//设置关闭通知栏
//            }
//        }).start();
    }

    public void wdNotify(){
        /* 创建通知 */
        final NotificationManager mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat
                .Builder(this, CHANNEL_ID);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setOngoing(true)
                .setVibrate(new long[]{0}) //震动
                .setSound(null)
                .setTicker("notification ticker")
                .setDefaults(NotificationCompat.FLAG_LOCAL_ONLY)
                .setSmallIcon(R.mipmap.ic_launcher);
        final Notification notification = mBuilder.build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int incr;
                for (incr = 0; incr <= 100; incr += 5) {
                    mBuilder.setProgress(100, incr, false);
                    mBuilder.setSound(null);
                    mNotifyManager.notify(0, notification);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
                mBuilder.setContentText("Download complete")
                        .setProgress(0, 0, false);
                mBuilder.setAutoCancel(true);
                mBuilder.setOngoing(false);
                mNotifyManager.notify(0, mBuilder.build());
            }
        }).start();
    }
}
