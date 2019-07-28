package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ui.erweima.encode.CodeCreator;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ErWeiMaFragment extends Fragment {

    @BindView(R.id.erweima_tv)
    TextView erweima_tv;
    @BindView(R.id.contentIvWithLogo)
    ImageView contentIvWithLogo;

    Bitmap bitmap = null;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_er_wei_ma, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        String key = getArguments().getString("key");

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.wuyang);
        bitmap = CodeCreator.createQRCode(key, 400, 400, logo);

        if (bitmap != null) {
            contentIvWithLogo.setImageBitmap(bitmap);
        }

    }


}
