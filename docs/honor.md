# HONOR PUSH

[参考文档 Reference Documentation NIM v9.x FOR HONOR](https://doc.yunxin.163.com/messaging/guide/DMwMjc5MDM?platform=android)


## SDK Config

## mcs-services.json

配置文件需要放置在 `app` 目录下。

The configuration file needs to be placed in the 'app' directory.

```
./
├── README.md
├── app
│    ├── mcs-services.json  // Honor config file
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
        maven {url 'https://developer.hihonor.com/repo'}  // honor maven
        ...
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {url 'https://developer.hihonor.com/repo'}  // honor maven
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
        maven {url 'https://developer.hihonor.com/repo'}
        ...
    }
    dependencies {
        classpath 'com.hihonor.mcs:asplugin:2.0.1.300'
        classpath 'com.android.tools.build:gradle:8.1.+'
        ...
    }
}
plugins { ... }
...
```

## Add dependency library

in APP
```
implementation rootProject.ext.PUSH_SDK_HONOR
```

in Module
```
api rootProject.ext.PUSH_SDK_HONOR
```

## Add AGC plugin configuration

在 `build.gradle` 中配置 agc plugin. `app` 和 `module` 的 `build.gradle` 文件任一个配置。

Configure either the agc plugin. app or module's build. gradle file in 'build. gradle'.

```gradle
plugins {
    id 'com.hihonor.mcs.asplugin'
}
```

OR

```gradle
apply plugin: 'com.hihonor.mcs.asplugin'
```



## AndroidManifest.xml

```xml
<!-- honor 消息推送服务-->
<service
    android:exported="false"
    android:name="com.netease.nimlib.mixpush.honor.HonorPushService">
    <intent-filter>
        <action android:name="com.hihonor.push.action.MESSAGING_EVENT"/>
    </intent-filter>
</service>

<meta-data
android:name="com.hihonor.push.app_id"
android:value="${honorAppId}" />
```

## proguard-rules.pro

```ProGuard
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-dontwarn com.hihonor.push.**
-keep class com.hihonor.push.** {*;}
```

## pushPayload

see `nim payload`




