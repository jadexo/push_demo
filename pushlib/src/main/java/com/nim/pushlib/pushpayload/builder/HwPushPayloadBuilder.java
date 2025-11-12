package com.nim.pushlib.pushpayload.builder;

import android.text.TextUtils;

import com.nim.pushlib.BuildConfig;
import com.nim.pushlib.pushpayload.IPushPayloadBuilder;
import com.nim.pushlib.pushpayload.NotifyClickAction;
import com.nim.pushlib.pushpayload.NotifyEffectMode;
import com.nim.pushlib.pushpayload.PushPayloadBuilderType;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HwPushPayloadBuilder implements IPushPayloadBuilder {
    private Map<String, Object> payloadMap = new HashMap<>();

    private Map<String, String> mCustomDataMap;
    private NotifyClickAction mClickAction;
    private String mChannelId = BuildConfig.hwChannelId;
    private String mCategory = BuildConfig.hwCategory;



    public HwPushPayloadBuilder() {
    }

    @Override
    public IPushPayloadBuilder setPushTitle(String title) {
        return null;
    }

    @Override
    public IPushPayloadBuilder addCustomData(String key, String data) {
        if (mCustomDataMap == null) {
            mCustomDataMap = new HashMap<>();
        }
        mCustomDataMap.put(key, data);
        return null;
    }

    @Override
    public IPushPayloadBuilder enableTestPushMode(boolean enable) {
        if (enable){
            payloadMap.put("target_user_type",1);
        }else {
            payloadMap.put("target_user_type",0);
        }
        return null;
    }

    @Override
    public IPushPayloadBuilder setClickAction(NotifyClickAction clickAction) {
        this.mClickAction = clickAction;
        return null;
    }

    @Override
    public IPushPayloadBuilder addChannelId(PushPayloadBuilderType builderType, String channelId) {
        mChannelId = channelId;

        return null;
    }

    @Override
    public IPushPayloadBuilder addCategory(PushPayloadBuilderType builderType, String channelId) {
        return null;
    }

    /**
     * hw官网内容
     * 消息点击行为类型，取值如下：
     * 1：打开应用自定义页面
     * 2：点击后打开特定URL
     * 3：点击后打开应用
     * @return
     */
    @Override
    public Map<String, Object> generatePayload() {
        if (mClickAction != null) {
            Map<String, Object> clickActionMap = new HashMap<>();
            NotifyEffectMode effectMode = mClickAction.getNotifyEffect();
            switch (effectMode) {
                case EFFECT_MODE_APP:
                    clickActionMap.put("type",3);
                    break;
                case EFFECT_MODE_CONTENT:
                    clickActionMap.put("type",1);
                    //华为的自定义信息可以携带在intent uri中
                    String intentFilterString = mClickAction.getIntentFilterString();
                    clickActionMap.put("intent",intentFilterString);
                    break;
                case EFFECT_MODE_WEB:
                    clickActionMap.put("type",2);
                    clickActionMap.put("url",mClickAction.getWebUrl());
                    break;
            }
            payloadMap.put("click_action",clickActionMap);
        }
        Map<String, Object> androidConfigMap = new HashMap<>();

        if (mCustomDataMap != null && mCustomDataMap.size() > 0) {
            JSONObject json = new JSONObject(mCustomDataMap);
            androidConfigMap.put("data", json.toString());
        }
        androidConfigMap.put("category",mCategory);
        if (androidConfigMap.size()>0){
            payloadMap.put("androidConfig",androidConfigMap);
        }
        if (!TextUtils.isEmpty(mChannelId)){
            payloadMap.put("channel_id",mChannelId);
        }
        return payloadMap;
    }
}
