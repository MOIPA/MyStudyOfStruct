package com.example.tr.instantcool2.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tr.instantcool2.Fragment.FirstSinghtFragment;
import com.example.tr.instantcool2.Fragment.SignUpFragment;
import com.example.tr.instantcool2.R;
import com.example.tr.instantcool2.Utils.ShowInfoUtil;

public class StartActivity extends AppCompatActivity {

    private FirstSinghtFragment firstFragment ;
    private static Context context;
    private InterfaceStoreData interfaceStoreData;
    private MyConn serviceConn;
    private Intent service;

    //负责处理界面toast的handler
    public static Handler handeToast = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String needContent = "";
            String succeed = "";
            String failed = "";
            needContent = data.getString("needContent");
            succeed = data.getString("succeed");
            failed = data.getString("failed");
            System.out.println(needContent + "*" + succeed + "*" + failed);
            if (!(needContent == null)) {
//                Toast.makeText(context, needContent, Toast.LENGTH_SHORT).show();
                ShowInfoUtil.showInfo(context,needContent);
                needContent = null;
            } else if (!(succeed == null)) {
                ShowInfoUtil.showInfo(context,succeed);
                succeed = null;
            } else if (!(failed == null)) {
                ShowInfoUtil.showInfo(context,failed);
                failed = null;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //添加起始页面
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SignUpFragment fragment = new SignUpFragment();
        firstFragment=new FirstSinghtFragment();
        transaction.add(R.id.start_frame_container, firstFragment);
        transaction.commit();
        context = getApplicationContext();
    }

    private void StartDataStoreService(){
        Intent service =new Intent(this, StoreDataService.class);
        startService(service);
        Log.d("Service","service running!");
//        serviceConn = new MyConn();
//        bindService(service,serviceConn,BIND_AUTO_CREATE);
    }

    //创建连接管理类
    private class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            interfaceStoreData = (InterfaceStoreData) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(serviceConn);
    }
}
