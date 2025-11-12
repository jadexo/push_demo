# OPPO PUSH

[参考文档 Reference Documentation NIM v9.x FOR OPPO](https://doc.yunxin.163.com/messaging/guide/zI4ODc0MTE?platform=android)


## SDK Config

下载将sdk放置在 `pushlib/libs`

在 `APP` 目录或 `Module` 目录下的 `build.gradle` 文件里的 `dependencies` 添加依赖。

Download and place the SDK in `pushlib/libs`

Add dependencies in the `build.gradle` file located in the `APP` or `Module` directory.

`com.heytap.msp_V3.7.1.aar`

## Add dependency library

in APP
```gradle
implementation rootProject.ext.PUSH_SDK_OPPO
```

in Module
```gradle
api files(rootProject.ext.PUSH_SDK_OPPO)
```

Related dependencies

```gradle
implementation "androidx.sqlite:sqlite:2.1.0"
implementation 'com.alibaba:fastjson:1.2.57'
implementation 'com.google.code.gson:gson:2.10.1'
implementation 'commons-codec:commons-codec:1.6'
implementation 'androidx.annotation:annotation:1.1.0'
```


## AndroidManifest.xml

```xml
<!-- OPPO 推送配置权限 -->
<uses-permission android:name="com.coloros.mcs.permission.RECIEVE_MCS_MESSAGE"/>
<uses-permission android:name="com.heytap.mcs.permission.RECIEVE_MCS_MESSAGE"/>

<application
    ...>
    ...
    <!-- OPPO 推送配置项 需要配置以下两项 -->
    <!-- 兼容 Q 以下版本 -->
    <service
    android:exported="true"
    android:name="com.netease.nimlib.mixpush.oppo.OppoPushService"
    android:permission="com.coloros.mcs.permission.SEND_MCS_MESSAGE">
    <intent-filter>
        <action android:name="com.coloros.mcs.action.RECEIVE_MCS_MESSAGE"/>
    </intent-filter>
    </service>
    
    <!-- 兼容 Q 版本 -->
    <service
    android:exported="true"
    android:name="com.netease.nimlib.mixpush.oppo.OppoAppPushService"
    android:permission="com.heytap.mcs.permission.SEND_PUSH_MESSAGE">
    <intent-filter>
        <action android:name="com.heytap.mcs.action.RECEIVE_MCS_MESSAGE"/>
        <action android:name="com.heytap.msp.push.RECEIVE_MCS_MESSAGE"/>
    </intent-filter>
    </service>
    ...
</application>
```

## proguard-rules.pro

```ProGuard
-keep public class * extends android.app.Service
-keep class com.heytap.msp.** { *;}
```

## pushPayload

see `nim payload`




