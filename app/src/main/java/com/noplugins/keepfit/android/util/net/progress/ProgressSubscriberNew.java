package com.noplugins.keepfit.android.util.net.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Contacts;

import java.lang.reflect.Type;

import rx.Subscriber;

/**
 * Created by shiyujia02 on 2018/4/12.
 */

public class ProgressSubscriberNew<T> extends Subscriber<Bean<Object>> implements DialogInterface.OnCancelListener {
    private SubscriberOnNextListener<Bean<Object>> mListener;
    private Context mContext;
    private ProgressHUD mProgressHUD;
    private String message;
    private boolean mIsProgress;

    Class<T> entity_class;
    GsonSubscriberOnNextListener<T> gsonSubscriberOnNextListener;

    /**
     * 没有加载框 用于列表刷新加载
     *
     * @param tokenClass
     * @param listener   请求成功 逻辑处理
     * @param context    上下文
     */
    public ProgressSubscriberNew(Class<T> tokenClass, GsonSubscriberOnNextListener<T> mgsonSubscriberOnNextListener, SubscriberOnNextListener<Bean<Object>> listener, Context context) {
        this.gsonSubscriberOnNextListener = mgsonSubscriberOnNextListener;
        this.mListener = listener;
        this.entity_class = tokenClass;
        this.mContext = context;
        this.mIsProgress = false;
    }

    /**
     * 可设置有加载框 内容显示为 加载中...
     *
     * @param listener   请求成功 逻辑处理
     * @param context    上下文
     * @param isProgress 是否显示进度条
     */
    public ProgressSubscriberNew(Class<T> tClass, GsonSubscriberOnNextListener<T> mgsonSubscriberOnNextListener, SubscriberOnNextListener<Bean<Object>> listener, Context context, boolean isProgress) {
        this.gsonSubscriberOnNextListener = mgsonSubscriberOnNextListener;
        this.entity_class = tClass;
        this.mListener = listener;
        this.mContext = context;
        this.mIsProgress = isProgress;
    }


    /**
     * 可设置有加载框 设置加载内容显示
     *
     * @param listener   请求成功 逻辑处理
     * @param context    上下文
     * @param isProgress 是否显示进度条
     * @param message    进度条内容显示
     */
    public ProgressSubscriberNew(Class<T> tClass, GsonSubscriberOnNextListener<T> mgsonSubscriberOnNextListener, SubscriberOnNextListener<Bean<Object>> listener, Context context, boolean isProgress, String message) {
        this.entity_class = tClass;
        this.gsonSubscriberOnNextListener = mgsonSubscriberOnNextListener;
        this.mListener = listener;
        this.mContext = context;
        this.mIsProgress = isProgress;
        this.message = message;
    }

    private void showProgressDialog() {
        if (mProgressHUD != null) {
            mProgressHUD = ProgressHUD.show(mContext, "加载中", false, this);
        }
    }

    private void showProgressDialog(String message) {
        if (mProgressHUD != null) {
            mProgressHUD = ProgressHUD.show(mContext, message, false, this);
        }
    }

    private void dismissProgressDialog() {
        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        if (mIsProgress) {
            if (message != null && !message.equals("")) {
                showProgressDialog(message);
                return;
            }
            showProgressDialog();
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
       /* if (e instanceof SocketTimeoutException) {
//            Toast.makeText(MyApplication.getApplication(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
//            Toast.makeText(MyApplication.getApplication(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
//            if (e.getMessage().contains("AppClient has not been setup")) {
//                Log.e("速度快放假快乐速度快11","速度快放假快乐速度快11");
//                if (mListener != null) {
//                    Intent intent = new Intent(mContext, LoginActivity.class);
//                    mContext.startActivity(intent);
//                }
//            } else if (e.getMessage().contains("异地登录")) {
//                Log.e("速度快放假快乐速度快","速度快放假快乐速度快");
//                MyApplication.destoryActivity("SplashActivity");
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                mContext.startActivity(intent);
//            } else {
//                Log.e("速度快放假快乐速度快12","速度快放假快乐速度快12");
//                mListener.onError(e.getMessage());
//            }
        }*/

        //判断是不是用户断网了
        ConnectivityManager connMgr = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (isMobileConn || isWifiConn) {
            if (e.getMessage().contains("Unable to resolve host")) {
//                RunBeyToast runBeyToast = new RunBeyToast(mContext, 2000, "连接服务器失败，请稍后再试~"
//                        , R.color.black, 255
//                );
//                runBeyToast.show();
                mListener.onError("断网");
                //dismissProgressDialog();//关闭loading

            } else {
//                Log.e("服务器返回异常：", e.getMessage());
                mListener.onError(e.getMessage());
//                if ("timeout".endsWith(e.getMessage()) || "SSL handshake timed out".endsWith(e.getMessage())) {
//                    mListener.onError(e.getMessage());
//                    dismissProgressDialog();
//                } else {
//                    mListener.onError(e.getMessage());
                dismissProgressDialog();
//                }
            }
        } else {
//            RunBeyToast runBeyToast = new RunBeyToast(mContext, 2000, "Opps,网络走丢啦~"
//                    , R.color.black, 255
//            );
//            runBeyToast.show();
            mListener.onError("断网");
//            dismissProgressDialog();//关闭loading
        }
    }

    @Override
    public void onNext(Bean<Object> t) {
        if (t != null) {
//            Log.e("网络请求成功", "进来了onNext" + "返回code:" + t.getCode()
//                    + "\n" + "返回message:" + t.getMsg()
//                    + "\n" + "返回实体类" + t.getData() + "       " + t);
            if (t.getCode().equals("1")) {//表示成功
                if (mListener != null) {
                    if (gsonSubscriberOnNextListener != null) {
                        //如果请求接口正确了，就返回实体类
//                        Gson gson = new Gson();
                        //解决gson将Integer默认转换成Double的问题

                        Gson gson = new GsonBuilder().
                                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                                    @Override
                                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                                        if (src == src.longValue())
                                            return new JsonPrimitive(src.longValue());
                                        return new JsonPrimitive(src);
                                    }
                                }).create();
                        String myJson = gson.toJson(t.getData());//将gson转化为json
//                        Log.e("123", "   获取数据JSON---》  " + myJson);
                        T jsonObject = gson.fromJson(myJson, entity_class);//把JSON字符串转为对象
//                        Log.e("123", "返回的对象json:" + jsonObject);
                        gsonSubscriberOnNextListener.on_post_entity(jsonObject);
                    }
                }
            } else {
                if (t.getCode().equals("3")) {//异步登录
                    Gson gson = new GsonBuilder().
                            registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                                @Override
                                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                                    if (src == src.longValue())
                                        return new JsonPrimitive(src.longValue());
                                    return new JsonPrimitive(src);
                                }
                            }).create();
                    String myJson = gson.toJson(t.getData());//将gson转化为json
                    JSONObject obj = JSONObject.parseObject(myJson);
                    String msg = obj.getString("msg");
                    if (mListener != null) {
                        if ("1".equals(Contacts.isPage)) {
                            Contacts.isPage = "2";
//                            Intent intent = new Intent(mContext, LoginErrorDialog.class);
//                            intent.putExtra("errorMes", msg);
                            //mContext.startActivity(intent);
                        }
                    }
                } else if (t.getCode().equals("0")) {//接口报错信息
                    mListener.onError(t.getMsg());
                } else if (t.getCode().equals("400")) {
                    mListener.onError(t.getMsg());
                }
            }
        }

    }


    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
