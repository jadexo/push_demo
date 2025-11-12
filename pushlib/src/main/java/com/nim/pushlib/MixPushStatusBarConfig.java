package com.nim.pushlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.netease.nimlib.sdk.NotificationFoldStyle;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusBarNotificationFilter;
import com.nim.pushlib.pushpayload.NotifyClickAction;

public class MixPushStatusBarConfig {
    public String NOTIFY_SOUND_KEY =
            "android.resource://com.netease.yunxin.app.im/raw/msg";
    private int LED_ON_MS = 1000;
    private int LED_OFF_MS = 1500;
    private int notification_icon;
    private Class<? extends Activity> notificationEntrance;
    private String colorString = "#3a9efb";
    private boolean showBadge = true;
    private NotificationFoldStyle notificationFoldStyle = NotificationFoldStyle.ALL;
    private boolean downTimeEnableNotification = true;
    private int foregroundActCount = 0;

    private String getColorString(){
        return colorString;
    }

    private StatusBarNotificationFilter getNotificationFilter() {
        return imMessage -> foregroundActCount > 0
                ? StatusBarNotificationFilter.FilterPolicy.DENY
                : StatusBarNotificationFilter.FilterPolicy.DEFAULT;
    }

    private String getNotifySoundKey(){
        return NOTIFY_SOUND_KEY;
    }

    public static class Builder {
//        private StatusBarNotificationConfig statusBarNotificationConfig;
        private MixPushStatusBarConfig config;

        public Builder(){
            config = new MixPushStatusBarConfig();
//            statusBarNotificationConfig = new StatusBarNotificationConfig();
        }

        /**
         * 设置默认跳转界面
         * @param notificationEntrance
         * @return
         */
        public Builder setNotificationEntrance(Class<? extends Activity> notificationEntrance){
            config.notificationEntrance = notificationEntrance;
            return this;
        }

        /**
         * 设置通知自定义声音路径
         * @param notifySoundKey String 音频路径
         * @return
         */
        public Builder setNotifySoundKey(String notifySoundKey){
            config.NOTIFY_SOUND_KEY = notifySoundKey;
            return this;
        }

        public Builder setNotificationFilter(int foregroundActCount){
            config.foregroundActCount = foregroundActCount;
            return this;
        }

        public Builder setLedOnMS(int ledOnMS){
            config.LED_ON_MS = ledOnMS;
            return this;
        }

        public Builder setLedOffMS(int ledOffMS){
            config.LED_OFF_MS = ledOffMS;
            return this;
        }

        /**
         * 设置通知文件颜色
         * @param colorString 16进制的颜色代码
         * @return
         */
        public Builder setNotificationColor(String colorString){
            config.colorString = colorString;
            return this;
        }

        /**
         * 设置通知的ICON
         * @param notification_icon int 图标资源ID
         * @return
         */
        public Builder setNotification_icon(int notification_icon){
            config.notification_icon = notification_icon;
            return this;
        }

        /**
         * 是否在图标处显示统计数字
         * @param showBadge boolean
         * @return
         */
        public Builder setShowBadge(boolean showBadge){
            config.showBadge = showBadge;
            return this;
        }

        public Builder setDownTimeEnableNotification(boolean downTime){
            config.downTimeEnableNotification = downTime;
            return this;
        }

        public StatusBarNotificationConfig build() {
            StatusBarNotificationConfig statusBarConfig = new StatusBarNotificationConfig();
            statusBarConfig.notificationEntrance = config.notificationEntrance;
            statusBarConfig.notificationSmallIconId = config.notification_icon;
            statusBarConfig.notificationColor = Color.parseColor(config.getColorString());
            statusBarConfig.notificationSound = config.NOTIFY_SOUND_KEY;
            statusBarConfig.notificationFoldStyle = config.notificationFoldStyle;
            statusBarConfig.downTimeEnableNotification = config.downTimeEnableNotification;
            statusBarConfig.ledARGB = Color.GREEN;
            statusBarConfig.ledOnMs = config.LED_ON_MS;
            statusBarConfig.ledOffMs = config.LED_OFF_MS;
            statusBarConfig.showBadge = config.showBadge;
            statusBarConfig.notificationFilter = config.getNotificationFilter();
            return statusBarConfig;
        }
    }
}
