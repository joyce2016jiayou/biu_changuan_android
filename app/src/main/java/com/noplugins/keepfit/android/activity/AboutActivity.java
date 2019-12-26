package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.callback.OnclickCallBack;
import com.noplugins.keepfit.android.entity.VersionEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.VersionUtils;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.BaseDialog;
import com.noplugins.keepfit.android.util.ui.progress.CustomHorizontalProgresWithNum;
import com.umeng.socialize.media.Base;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.banben_shengji_btn)
    RelativeLayout banben_shengji_btn;
    private String update_url = "";
    private boolean is_qiangzhi_update;
    private DownloadBuilder builder;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_about);
        ButterKnife.bind(this);
        isShowTitle(true);
        setTitleView(R.string.tv107);
        tv_version.setText("版本号：" + getVerName(this));
        title_left_button_onclick_listen(new OnclickCallBack() {
            @Override
            public void onclick() {
                setResult(SpUtils.getInt(getApplicationContext(), AppConstants.FRAGMENT_SIZE) - 1);
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        banben_shengji_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_app();
            }
        });

    }

    private void update_app() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "gym");
        params.put("code", VersionUtils.getAppVersionCode(getApplicationContext()));
        params.put("phoneType", "2");
        Subscription subscription = Network.getInstance("升级版本", this)
                .update_version(params,
                        new ProgressSubscriber<>("升级版本", new SubscriberOnNextListener<Bean<VersionEntity>>() {
                            @Override
                            public void onNext(Bean<VersionEntity> result) {
                                update_url = result.getData().getUrl();
                                //是否需要强制升级1强制升级 2不升级 3可升级可不升级
                                if (result.getData().getUp() == 1) {
                                    is_qiangzhi_update = true;
                                    update_app_pop();
                                } else if (result.getData().getUp() == 3) {
                                    update_app_pop();
                                    is_qiangzhi_update = false;
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.tv184, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void update_app_pop() {
        builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(crateUIData());
        builder.setCustomVersionDialogListener(createCustomDialogTwo());//设置更新弹窗样式
        builder.setCustomDownloadingDialogListener(createCustomDownloadingDialog());//设置下载样式
        builder.setForceRedownload(true);//强制重新下载apk（无论本地是否缓存）
        builder.setShowNotification(true);//显示下载通知栏
        builder.setShowDownloadingDialog(true);//显示下载中对话框
        builder.setShowDownloadFailDialog(true);//显示下载失败对话框
        builder.setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/noplugins/apkpath/");//自定义下载路径
        builder.setOnCancelListener(() -> {
            if (is_qiangzhi_update) {
                Toast.makeText(AboutActivity.this, "已关闭更新", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AboutActivity.this, "已关闭更新", Toast.LENGTH_SHORT).show();
            }
        });
        builder.executeMission(this);
    }

    /**
     * 自定义下载中对话框，下载中会连续回调此方法 updateUI
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadingDialogListener createCustomDownloadingDialog() {
        return new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.custom_download_layout);
                baseDialog.setCanceledOnTouchOutside(false);
                return baseDialog;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                CustomHorizontalProgresWithNum pb = dialog.findViewById(R.id.pb);
                pb.setProgress(progress);
                pb.setMax(100);
            }
        };
    }

    /**
     * 更新弹窗样式
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogTwo() {
        return (context, versionBundle) -> {
            BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.shengji_pop_layout);
            baseDialog.setCanceledOnTouchOutside(false);
            TextView tv_msg = baseDialog.findViewById(R.id.tv_msg);
            tv_msg.setText(versionBundle.getContent());
            return baseDialog;
        };
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(getString(R.string.update_title));
        uiData.setDownloadUrl(update_url);
        if (is_qiangzhi_update) {
            uiData.setContent(getString(R.string.updatecontent2));
        } else {
            uiData.setContent(getString(R.string.updatecontent));
        }
        return uiData;
    }

    @Override
    public void onBackPressed() {
        setResult(SpUtils.getInt(getApplicationContext(), AppConstants.FRAGMENT_SIZE) - 1);
        finish();
    }

    public String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
