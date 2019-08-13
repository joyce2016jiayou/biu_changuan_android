package com.noplugins.keepfit.android.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "自定义消息";
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID

    @Override
    public void onReceive(Context context, Intent intent) {
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(context, R.raw.message, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        try {
            Bundle bundle = intent.getExtras();
            Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //Log the Registration Id to your server...
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息message: " + message);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息extras: " + extras);

                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                Log.e(TAG, "接收到的推送title： " + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                Log.e(TAG, "接收到的推送content： " + bundle.getString(JPushInterface.EXTRA_ALERT));
                //设置推送的声音
                sp.play(music, 0.2f, 0.2f, 0, 0, 1);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.e("极光返回的json", extra);
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                String title;
                String content;
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.e("极光推送通知调用的接口", "返回的标题：" + title + "\n+返回的内容：" + content);
                JSONObject jsonObject = new JSONObject(extra);
                String type_id = jsonObject.getString("type");
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


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.e(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
    }
}
