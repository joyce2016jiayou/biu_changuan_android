package com.noplugins.keepfit.android.util.ui;

import android.content.Context;
import android.content.DialogInterface;

import com.noplugins.keepfit.android.util.net.progress.ProgressHUD;


/**
 * Created by shiyujia02 on 2018/4/23.
 */

public class ProgressUtil implements DialogInterface.OnCancelListener{
    private  Context mContext;
    private ProgressHUD mProgressHUD;



    public void showProgressDialog(Context context, String str) {
        mContext = context;
        mProgressHUD = ProgressHUD.show(mContext, str, true, this);
    }

    public void showProgressDialog(String message) {
        mProgressHUD = ProgressHUD.show(mContext, message, true, this);
    }

    public void dismissProgressDialog() {
        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }
}
