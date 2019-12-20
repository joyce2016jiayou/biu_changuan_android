package com.noplugins.keepfit.android.customize;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ms.banner.holder.BannerViewHolder;
import com.noplugins.keepfit.android.R;

/**
 * Created by songwenchao
 * on 2018/5/17 0017.
 * <p>
 * 类名
 * 需要 --
 * 可以 --
 */
public class CustomViewHolder implements BannerViewHolder<Object> {

    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回mImageView页面布局
        mImageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        mImageView.setLayoutParams(params);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return mImageView;
    }

    @Override
    public void onBind(Context context, int position, Object data) {
        // 数据绑定
        Log.d("tag","url:"+data.toString());
        Glide.with(context).load(data)
                .placeholder(R.drawable.logo_gray)
                .into(mImageView);
    }
}