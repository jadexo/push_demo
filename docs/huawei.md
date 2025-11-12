# HUAWEI PUSH

[参考文档 Reference Documentation NIM v9.x FOR HUAWEI](https://doc.yunxin.163.com/messaging/guide/jg2ODQxMjU?platform=android)


## SDK Config

## agconnect-services.json

配置文件需要放置在 `app` 目录下。

The configuration file needs to be placed in the 'app' directory.

```
./
├── README.md
├── app
│    ├── agconnect-services.json  // huawei config file
│    ├── build
│    ├── src
│    └── ...
├── build.gradle
├── docs
├── gradle
├── pushlib
...
```

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
        maven { url 'https://developer.huawei.com/repo/' }  // huawei maven
        ...
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {url 'https://developer.huawei.com/repo/'}  // huawei maven
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
        maven {url 'https://developer.huawei.com/repo/'}
        ...
    }
    dependencies {
        classpath 'com.huawei.agconnect:agcp:1.9.1.300'
        ...
    }
}
plugins { ... }
...
```

## Add dependency library

in APP
```
implementation rootProject.ext.PUSH_SDK_HW
```

in Module
```
api rootProject.ext.PUSH_SDK_HW 
```

## Add AGC plugin configuration

在 `build.gradle` 中配置 agc plugin. `app` 和 `module` 的 `build.gradle` 文件任一个配置。

Configure either the agc plugin. app or module's build. gradle file in 'build. gradle'.

```gradle
plugins {
    id 'com.android.library'
}
```

OR

```gradle
apply plugin: 'com.huawei.agconnect'
```



## AndroidManifest.xml

```xml
<!-- 华为消息推送服务-->
<service
    android:name="com.netease.nimlib.mixpush.hw.HWPushService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.huawei.push.action.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

## proguard-rules.pro

```ProGuard
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
```

如果您使用了 AndResGuard 插件，需要在应用级的 build.gradle 文件中加入 AndResGuard 允许清单。

If you use the AndResGuard plugin, you need to add the AndResGuard allow list in the build.gradle file at the application level.

```Gradle
whiteList = [
    "R.string.hms*",
    "R.string.connect_server_fail_prompt_toast",
    "R.string.getting_message_fail_prompt_toast",
    "R.string.no_available_network_prompt_toast",
    "R.string.third_app_*",
    "R.string.upsdk_*",
    "R.layout.hms*",
    "R.layout.upsdk_*",
    "R.drawable.upsdk*",
    "R.color.upsdk*",
    "R.dimen.upsdk*",
    "R.style.upsdk*",
    "R.string.agc*"
]
```

## pushPayload

see `nim payload`




