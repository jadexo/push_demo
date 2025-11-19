package com.demo.app.android;


import static com.nim.pushlib.MixPushConfigGenerator.loadPushConfig;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hihonor.push.sdk.HonorPushClient;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;

import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.util.NIMUtil;

public class MainApplication extends Application {
    private static final String TAG = "MYPUSH";
    private static MainApplication instance;

    // 获取Application的上下文，避免内存泄漏
    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    // 获取Application的单例实例
    public static MainApplication getInstance() {
        return instance;
    }
    private static int foregroundActCount = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.i(TAG,"run Application");

        SDKOptions sdkOptions = new SDKOptions();
        sdkOptions.statusBarNotificationConfig = new StatusBarNotificationConfig();
        sdkOptions.mixPushConfig = loadPushConfig();
        sdkOptions.disableAwake = true;
        NIMClient.init(this, null, sdkOptions);
        NIMClient.toggleNotification(false);

        if (NIMUtil.isMainProcess(this)) {
            Log.i(TAG,"初始化完成");
            // for Oppo sdk init
            com.heytap.msp.push.HeytapPushManager.init(this, true);
            // for Honor sdk init
            HonorPushClient.getInstance().init(getApplicationContext(), true);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        foregroundActCount = 0;
    }

    public static int getForegroundActCount() {
        return foregroundActCount;
    }
}
