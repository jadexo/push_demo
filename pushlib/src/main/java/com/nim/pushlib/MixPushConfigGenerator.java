package com.nim.pushlib;

import com.netease.nimlib.sdk.mixpush.MixPushConfig;

public class MixPushConfigGenerator {
    public static MixPushConfig loadPushConfig(){
        MixPushConfig pushConfig = new MixPushConfig();
        //获取config.gradle文件中的厂商推送配置信息
        // FCM Google id: 8
        pushConfig.fcmCertificateName = BuildConfig.fcmCertificateName;
        // XIAOMI 小米 id: 5
        pushConfig.xmAppId = BuildConfig.xmAppId;
        pushConfig.xmAppKey = BuildConfig.xmAppKey;
        pushConfig.xmCertificateName = BuildConfig.xmCertificateName;
        // HUAWEI id: 6
        pushConfig.hwAppId = BuildConfig.hwAppId;
        pushConfig.hwCertificateName = BuildConfig.hwCertificateName;
        // HONOR 荣耀 id: 11
        pushConfig.honorCertificateName = BuildConfig.honorCertificateName;
        // VIVO id: 9
        pushConfig.vivoCertificateName = BuildConfig.vivoCertificateName;
        // OPPO id: 10
        pushConfig.oppoAppId = BuildConfig.oppoAppId;
        pushConfig.oppoAppKey = BuildConfig.oppoAppKey;
        pushConfig.oppoAppSercet = BuildConfig.oppoAppSercet;
        pushConfig.oppoCertificateName = BuildConfig.oppoCertificateName;
        // MEIZU 魅族 id: 7
        pushConfig.mzAppId = BuildConfig.mzAppId;
        pushConfig.mzAppKey = BuildConfig.mzAppKey;
        pushConfig.mzCertificateName = BuildConfig.mzCertificateName;

        return pushConfig;
    }
}
