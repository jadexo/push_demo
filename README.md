# Set YunXing Notification

本工程是经过即时测试 NIM 和当前可用的厂商 sdk 版本适配调试结果

This project has undergone real-time testing to adapt and debug NIM and currently available vendor SDK versions

Application version information：
- NIM Version 9.16.3 
- Xiaomi push sdk 6_0_1-C_3rd - aar 
- Oppo push sdk 3.7.1 - aar
- Huawei push sdk 6.12.0.300 - maven
- Honor push sdk 7.0.61.303 - maven
- Vivo push sdk 4.1.0.0 - maven

# About Basic Configuration Information

配置信息一般在当前工程目录的根目录下 `pushConfig.gradle` 中。

The configuration information is generally located in the root directory of the current project directory under `pushConfig.gradle`.

```gradle
ext {
    //主项目使用到的IM版本 9.12.2  9.16.3  9.20.17
    NIMVersion = "9.16.3"
    //云信的appkey，对应清单文件中的com.netease.nim.appKey的值
    APP_KEY = "xxxxxxxxx"
    APPLICATION_ID = "xxxxxxxxx"
    
    ...
}
```

注意：在配置信息中各推送平台无需指定消息类型。因为各平台的消息类型已经在云信控制台中已经配置好了，无需在应用端指定。

Note: Each push platform does not need to specify the message type in the configuration information. Because the message types for each platform have already been configured in the Yunxin console, there is no need to specify them on the application side.


# Configuration instructions for each push service platform

- [Xiaomi push doc](./docs/xiaomi.md)
- [Oppo push doc](./docs/oppo.md)
- [Huawei push doc](./docs/huawei.md)
- [Honor push doc](./docs/honor.md)
- [Vivo push doc](./docs/vivo.md)