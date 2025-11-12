package com.demo.app.android;

import static com.demo.app.android.Utils.generateRandomString;

import android.util.Log;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.nim.pushlib.pushpayload.NotifyClickAction;
import com.nim.pushlib.pushpayload.NotifyEffectMode;
import com.nim.pushlib.pushpayload.PushPayloadBuilder;

import java.util.HashMap;
import java.util.Map;

public class IMessage {
    private static final String TAG = "MYPUSH";
    static void sendMessage(String toUser, String MessageContent){
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        IMMessage textMessage = MessageBuilder.createTextMessage(toUser, sessionType, MessageContent);

        Log.i(TAG,"消息："+textMessage.getContent());
        Log.i(TAG, "getFromNick:"+textMessage.getFromNick());

        PushPayloadBuilder payloadBuilder = new PushPayloadBuilder();
        /**
         * notification title
         * If the value passed in is empty, it will cause the notification build to fail and unable to initiate notifications through the push vendor.
         */
        payloadBuilder.setPushTitle(textMessage.getFromNick());

        /**
         * Set message click event
         * Set the visible encapsulation logic for notification messages after clicking
         * payloadBuilder.setClickAction();
         * The following is a demo of the behavior after registering and clicking on notifications
         */
        NotifyClickAction naction = new NotifyClickAction.Builder()
                .setNotifyEffect(NotifyEffectMode.EFFECT_MODE_WEB)
                .setWebUrl("https://www.google.com/")
                .build();
        payloadBuilder.setClickAction(naction);

        textMessage.setPushPayload(payloadBuilder.generatePayload());
        textMessage.setPushContent(textMessage.getContent());
        NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Log.i(TAG,"消息发送成功: "+param);
            }

            @Override
            public void onFailed(int code) {
                Log.i(TAG,"消息发送失败 code:"+code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.i(TAG,"消息错误:"+exception.getMessage());
            }
        });
    }
}
