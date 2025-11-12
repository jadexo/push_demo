package com.demo.app.android;

import static com.demo.app.android.IMessage.sendMessage;
import static com.demo.app.android.Utils.generateRandomString;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushServiceObserve;
import com.netease.nimlib.sdk.mixpush.model.MixPushToken;
import com.netease.nimlib.sdk.mixpush.model.MixPushTypeEnum;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.nim.pushlib.BuildConfig;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 68600
//    public final String accid = BuildConfig.receiver_id;
//    private final String token = BuildConfig.receiver_token;
    private final String toUser = BuildConfig.receiver_id;
    // 68047
    public final String accid = BuildConfig.sender_id;
    private final String token = BuildConfig.sender_token;

    private boolean needSend = false;
    PushType pt = new PushType();
    private static final int REQUEST_NOTIFICATION = 1001;
    private static final String PERMISSION_POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";
    TextView textAccountType,textToken,textTokenName, textType, textAccid;
    Button btnSend;
    private static final String TAG = "MYPUSH";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAccountType = findViewById(R.id.account_type);
        textToken = findViewById(R.id.token);
        textTokenName = findViewById(R.id.tokenName);
        textType = findViewById(R.id.type);
        textAccid = findViewById(R.id.accid);
        btnSend = findViewById(R.id.sendButton);

        textAccid.setText(accid);

        if(accid != toUser){
            needSend = true;
            textAccountType.setText("消息发送者");
        }else{
            textAccountType.setText("消息接收者");
        }

        registerImListener(true);
        checkPermission();
        Login();
    }

    private void Login(){
        LoginInfo loginInfo = new LoginInfo(accid, token);
        NIMClient.getService(AuthService.class).login(loginInfo).setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                Log.d(TAG, "登陆成功");
                Log.d(TAG, "accid:" + accid);
            }
            @Override
            public void onFailed(int code) {
                Log.i(TAG, "登陆失败");
                if (code == 302) {
                    Log.i(TAG, "账号密码错误");
                    // your code
                } else {
                    // your code
                }
            }
            @Override
            public void onException(Throwable exception) {
                // your code
                Log.i(TAG, "登陆异常");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        Log.d(TAG,"run updateUI");
        if(needSend){
            btnSend.setVisibility(View.VISIBLE);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = generateRandomString(10);
                    sendMessage(toUser,content);
                }
            });
        }

        if (pt != null && pt.getToken() != null) {
            Log.i(TAG, "Token: " + pt.getToken());
            textToken.setText(pt.getToken());
            textTokenName.setText(pt.getTokenName() != null ? pt.getTokenName() : "未获取");
            textType.setText(pt.getType() != null ? pt.getType().toString() : "未获取");
        } else {
            Log.i(TAG, "等待Token获取...");
            textToken.setText("等待Token获取...");
            textTokenName.setText("初始化中");
            textType.setText("未知");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清理监听器，避免内存泄漏
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "执行了 onRequestPermissionsResult");

        if (requestCode == REQUEST_NOTIFICATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "用户授予了通知权限");
            } else {
                Log.i(TAG, "用户拒绝了通知权限");
            }
        }
    }

    private void checkPermission() {
        boolean hasPermission = ContextCompat.checkSelfPermission(this, PERMISSION_POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            String[] permissions = new String[]{PERMISSION_POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    private void registerImListener(boolean register) {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(onlineStatusObserver, register);
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(receiveMessageObserver, register);
        NIMClient.getService(MixPushServiceObserve.class).observeMixPushToken(mixPushTokenObserver, register);
    }

    private Observer<StatusCode> onlineStatusObserver = new Observer<StatusCode>() {
        @Override
        public void onEvent(StatusCode status) {
            //获取状态的描述
            String desc = status.getDesc();
            Log.i(TAG,"登陆状态："+desc);
            Log.i(TAG,"登陆状态 Value："+status.getValue());
            if (status.wontAutoLogin()) {
                // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
            }
        }
    };

    private Observer receiveMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> imMessages) {
            Log.i(TAG,"监听云消息");
            for (IMMessage message : imMessages) {
                Log.d(TAG, "收到云信消息: " + message.getContent());
            }
        }
    };

    private Observer mixPushTokenObserver = new Observer<MixPushToken>() {
        @Override
        public void onEvent(MixPushToken result) {

            String token = result.getToken();
            MixPushTypeEnum pushType = result.getPushType();
            String tokenName = result.getTokenName();

            pt.setToken(token);
            pt.setType(pushType);
            pt.setTokenName(tokenName);
            Log.d(TAG, "Token获取成功: " + token);
            Log.d(TAG, "推送类型: " + pushType);
            Log.d(TAG, "Token名称: " + tokenName);
            updateUI();
        }
    };
}