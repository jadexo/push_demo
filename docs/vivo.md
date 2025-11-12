# VIVO PUSH

[参考文档 Reference Documentation NIM v9.x FOR VIVO](https://doc.yunxin.163.com/messaging/guide/zY0MjM0OTc?platform=android)


## SDK Config

当前说明与 NIM 文档不同，NIM 是将 vivo sdk 下载到本地进行编译，本文档则是通过 maven 将 SDK 引入。

The current description is different from the NIM document. NIM downloads the vivo SDK locally for compilation, while this document introduces the SDK through Maven.

## Set Maven repo

分别在 `{project_rootPath}\settings.gradle` 和 `{project_rootPath}\build.gradle` 中配置 `maven` 仓库和类

Configure the Maven repository and classes separately in `{project_rootPath}\settings.gradle` and `{project_rootPath}\build.gradle`

In `{project_rootPath}\settings.gradle`

```gradle
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven{ url 'https://repos.vivo.com.cn/maven/repository/external-lib/'}  // vivo maven
        ...
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven{ url 'https://repos.vivo.com.cn/maven/repository/external-lib/'}  // vivo maven
        ...
    }
}

...
```

In `{project_rootPath}\build.gradle`

```gradle
buildscript {
    repositories {
        google()
        jcenter()
        maven{ url 'https://repos.vivo.com.cn/maven/repository/external-lib/'}  // vivo maven
        ...
    }
    dependencies {
        ...
    }
}
plugins { ... }
...
```

## Add dependency library

in APP
```
implementation rootProject.ext.PUSH_SDK_VIVO
```

in Module
```
api rootProject.ext.PUSH_SDK_VIVO
```

## AndroidManifest.xml

```xml
<!-- VIVO -->
<service
    android:name="com.vivo.push.sdk.service.CommandClientService"
    android:permission="com.push.permission.UPSTAGESERVICE"
    android:exported="true"/>

<receiver
android:name="com.netease.nimlib.mixpush.vivo.VivoPushReceiver"
android:exported="false">
<intent-filter>
    <!-- 接收 push 消息 -->
    <action android:name="com.vivo.pushclient.action.RECEIVE"/>
</intent-filter>
</receiver>

<meta-data
android:name="api_key"
android:value="${vivoAppKey}"/>
<meta-data
android:name="app_id"
android:value="${vivoAppId}"/>
```

## proguard-rules.pro

```ProGuard
-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
```

## pushPayload

see `nim payload`




