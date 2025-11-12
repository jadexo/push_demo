package com.demo.app.android;

import com.netease.nimlib.sdk.mixpush.model.MixPushTypeEnum;

public class PushType {
    String token, tokenName;
    MixPushTypeEnum type;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setType(MixPushTypeEnum type) {
        this.type = type;
    }

    public MixPushTypeEnum getType() {
        return type;
    }
}
