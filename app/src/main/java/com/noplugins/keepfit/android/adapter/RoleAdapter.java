package com.noplugins.keepfit.android.adapter;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.RoleBean;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
;
import java.util.List;

import lib.demo.spinner.MaterialSpinner;


public class RoleAdapter extends BaseQuickAdapter<RoleBean.RoleEntity, BaseViewHolder>{

    public RoleAdapter(@Nullable List<RoleBean.RoleEntity> data) {
        super(R.layout.role_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RoleBean.RoleEntity item) {
        EditText item_editText = helper.getView(R.id.edit_name);
        EditText et_phone = helper.getView(R.id.edit_phone);

        MaterialSpinner materialSpinner = helper.getView(R.id.post_type_spinner);
        String[] typeArrays = mContext.getResources().getStringArray(R.array.zhiwei_types);
        materialSpinner.setItems(typeArrays);
        materialSpinner.setSelectedIndex(0);
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                materialSpinner.setText(item);
            }
        });
        materialSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });

        if (item_editText.getTag() instanceof TextWatcher){
            item_editText.removeTextChangedListener((TextWatcher) item_editText.getTag());
        }

        if (et_phone.getTag() instanceof TextWatcher){
            et_phone.removeTextChangedListener((TextWatcher) et_phone.getTag());
        }

        //必须在判断tag后给editText赋值，否则会数据错乱
        item_editText.setText(item.getName());
        et_phone.setText(item.getPhone());
        if (item.getUserType()!=0){
            materialSpinner.setSelectedIndex(item.getUserType()-2);
            item_editText.setEnabled(false);
            et_phone.setEnabled(false);
            materialSpinner.setClickable(false);
        }
        item.setGymAreaNum(SpUtils.getString(mContext, AppConstants.CHANGGUAN_NUM));
        item.setUserName(item.getName());

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    item.setUserName(editable + "");
                }
            }
        };

        TextWatcher watcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    item.setPhone(editable + "");
                }
            }
        };

        //        给item中的editText设置监听
        item_editText.addTextChangedListener(watcher);
//        给editText设置tag，以便于判断当前editText是否已经设置监听
        item_editText.setTag(watcher);
        et_phone.addTextChangedListener(watcher1);
//        给editText设置tag，以便于判断当前editText是否已经设置监听
        et_phone.setTag(watcher1);



        helper.addOnClickListener(R.id.post_type_spinner)
                .addOnClickListener(R.id.Add_btn);

    }
}
