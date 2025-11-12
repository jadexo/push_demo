package com.demo.app.android;

import static com.demo.app.android.NimSDKOptionConfig.buildMixPushConfig;
import static com.nim.pushlib.MixPushConfigGenerator.loadPushConfig;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hihonor.push.sdk.HonorPushClient;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;

import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.nim.pushlib.MixPushConfigGenerator;
import com.nim.pushlib.MixPushStatusBarConfig;

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

        // SDK 初始化（启动后台服务，若已经存在用户登录信息，SDK 将进行自动登录）。不能对初始化语句添加进程判断逻辑。
//        StatusBarNotificationConfig mixPushStatusBarConfig = new MixPushStatusBarConfig.Builder()
//                .setShowBadge(true)
//                .build();

        SDKOptions sdkOptions = new SDKOptions();
//        sdkOptions.statusBarNotificationConfig = mixPushStatusBarConfig;
//        sdkOptions.statusBarNotificationConfig = new StatusBarNotificationConfig();
//        sdkOptions.mixPushConfig = loadPushConfig();
        sdkOptions.mixPushConfig = buildMixPushConfig();
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
