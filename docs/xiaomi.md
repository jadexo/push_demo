# XIAOMI PUSH

[参考文档 Reference Documentation NIM v9.x FOR XIAOMI](https://doc.yunxin.163.com/messaging/guide/zg2NDE4ODM?platform=android)

## SDK

下载将sdk放置在 `pushlib/libs`

在 `APP` 目录或 `Module` 目录下的 `build.gradle` 文件里的 `dependencies` 添加依赖。

Download and place the SDK in `pushlib/libs`

Add dependencies in the `build.gradle` file located in the `APP` or `Module` directory.

`MiPush_SDK_Client_6_0_1-C_3rd.aar`

in APP
```
implementation files(rootProject.ext.PUSH_SDK_XM);
```

in Module
```
api files(rootProject.ext.PUSH_SDK_XM);
```

## AndroidManifest.xml

```xml
<!--配置 receiver-->
<receiver
    android:name="com.netease.nimlib.mixpush.mi.MiPushReceiver"
    android:exported="true">
    <intent-filter android:priority="0x7fffffff">
        <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE"/>
        <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED"/>
        <action android:name="com.xiaomi.mipush.ERROR"/>
    </intent-filter>
</receiver>
```

## proguard-rules.pro

```
-keep class com.xiaomi.** {*;}
```

## pushPayload

see `nim payload`
