package com.noplugins.keepfit.android.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.noplugins.keepfit.android.KeepFitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";

    /**
     * 收到自定义消息回调
     * 支持的版本
     * 开始支持的版本：3.3.0
     * 说明 如果需要在旧版本的Receiver接收cn.jpush.android.intent.MESSAGE_RECEIVED广播
     * 可以不重写此方法，或者重写此方法且调用super.onMessage
     * 如果重写此方法，没有调用super，则不会发送广播到旧版本Receiver
     *
     * @param context
     * @param customMessage
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
        processCustomMessage(context, customMessage);
    }

    /**
     * 点击通知回调
     * 支持的版本
     * 开始支持的版本：3.3.0
     * 说明 如果需要在旧版本的Receiver接收cn.jpush.android.intent.NOTIFICATION_OPENED广播
     * 可以不重写此方法，或者重写此方法且调用super.onNotifyMessageOpened
     * 如果重写此方法，没有调用super，则不会发送广播到旧版本Receiver
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Bundle bundle = new Bundle();

        Log.e(TAG, "[onNotifyMessageOpened] " + message.notificationExtras);
        // String extra = bundle.getString(message.notificationExtras);
        //Log.e("极光返回的json", extra);


        JSONObject jsonObject = null;
        String type_id = "";
        try {
            jsonObject = new JSONObject(message.notificationExtras);
            type_id = jsonObject.getString("type");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("极光返回的页面跳转的ID", type_id);
        Intent itent = new Intent(context, KeepFitActivity.class);
        Bundle message_bunder = new Bundle();
        switch (type_id) {
            case "1"://跳转到消息第一项
                message_bunder.putString("jpush_enter1", "jpush_enter1");
                break;
            case "2": {
                message_bunder.putString("jpush_enter2", "jpush_enter2");
                break;
            }
            case "3": {
                message_bunder.putString("jpush_enter3", "jpush_enter3");
                break;
            }
            case "4": {
                message_bunder.putString("jpush_enter4", "jpush_enter4");
                break;
            }
        }
        itent.putExtras(message_bunder);
        itent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(itent);

//        try {
//            //打开自定义的Activity
//            Intent i = new Intent(context, KeepFitActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle);
//            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent);
//            i.putExtras(bundle);
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(i);
//        } catch (Throwable throwable) {
//
//        }

    }

    /**
     * 通知的MultiAction回调
     * 支持的版本
     * 开始支持的版本：3.3.2
     * 说明 如果需要在旧版本的Receiver接收cn.jpush.android.intent.NOTIFICATION_CLICK_ACTION广播
     * 可以不重写此方法，或者重写此方法且调用super.onMultiActionClicked
     * 如果重写此方法，没有调用super，则不会发送广播到旧版本Receiver
     *
     * @param context
     * @param intent
     */
    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);
        Log.e(TAG, "nActionExtra" + nActionExtra);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    /**
     * 接受到的推送调用
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message);


    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    /**
     * tag 增删查改的操作会在此方法中回调结果
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    /**
     * 查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    /**
     * alias 相关的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    /**
     * 设置手机号码会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, CustomMessage customMessage) {
//        if (MainActivity.isForeground) {
//            String message = customMessage.message;
//            String extras = customMessage.extra;
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
    }
}
