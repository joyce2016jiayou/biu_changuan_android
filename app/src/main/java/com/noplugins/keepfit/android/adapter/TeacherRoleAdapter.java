package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.TeacherBean;
import com.noplugins.keepfit.android.entity.TypeItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherRoleAdapter extends RecyclerView.Adapter<TeacherRoleAdapter.ViewHolder>{
    private List<TeacherBean.TeacherEntity> datas;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private Context mcontext;
    private List<TypeItemEntity> typeItemEntities = new ArrayList<>();
    private String[] typeArrays;
    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

    public TeacherRoleAdapter(Context context, List<TeacherBean.TeacherEntity> data, int layoutId) {
        this.datas = data;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        typeArrays = mcontext.getResources().getStringArray(R.array.gongneng_types);
        mLayoutId = layoutId;
    }

    /**
     * 声明接口变量
     */
    private onItemClick onItemClick;

    /**
     * 定义监听接口tag是区分点击的什么，position是位置，要想获取position还需要在重新设置下tag
     */
    public static interface onItemClick {

        void onItemClick(int position);
    }

    /**
     * 声明给外界的方法
     *
     * @param listener
     */
    public void setOnItemDeleteClickListener(onItemClick listener) {
        this.onItemClick = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayoutId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        //第0 不能有删除功能
//        if (position == 0) {
//            holder.Add_btn.setImageResource(R.drawable.role_add_icon);
//        } else {
//            holder.Add_btn.setImageResource(R.drawable.role_jian_btn);
//        }
//        //判断是否有TextWatcher监听事件，有的话先移除
//        if (holder.edit_name.getTag(R.id.edit_name) instanceof TextWatcher) {
//            holder.edit_name.removeTextChangedListener(((TextWatcher) holder.edit_name.getTag(R.id.edit_name)));
//        }
//        //移除了TextWatcher事件后设置item对应的文本
//        holder.edit_name.setText(datas.get(position).getPlace());
//
//        //设置焦点
//        if (datas.get(position).isFocus()) {
//            if (!holder.edit_name.isFocused()) {
//                holder.edit_name.requestFocus();
//            }
//            CharSequence text = datas.get(position).getPlace();
//            holder.edit_name.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
//        } else {
//            if (holder.edit_name.isFocused()) {
//                holder.edit_name.clearFocus();
//            }
//        }
//        //edit 事件处理
//        holder.edit_name.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                final boolean focus = datas.get(position).isFocus();
//                check(position);
//                if (!focus && !holder.edit_name.isFocused()) {
//                    holder.edit_name.requestFocus();
//                    holder.edit_name.onWindowFocusChanged(true);
//                }
//            }
//            return false;
//        });
//
//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (TextUtils.isEmpty(s)) {
//                    datas.get(position).setPlace(null);
//                } else {
//                    //监听edit 值
//                    datas.get(position).setPlace(s.toString());
//                    //将editText中改变的值设置的HashMap中
//                    //hashMap.put(position, s.toString());
//                }
//            }
//        };
        if (datas.get(position).getDeleted() == 0){
            holder.edit_name.setEnabled(false);
            holder.edit_phone.setEnabled(false);
        }

        holder.edit_name.setText(datas.get(position).getUserName());
        holder.edit_phone.setText(datas.get(position).getPhone());

        //添加
        holder.Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(position);
            }
        });

        //设置下拉选择框
        hashMap.put(position, 0);


    }

    //  添加数据
    public void addData(TeacherBean.TeacherEntity itemBean) {

//        itemBean.setFocus(true);
//      在list中添加数据，并通知条目加入一条
        datas.add(datas.size(), itemBean);
        //添加动画
        notifyItemInserted(datas.size());
//        if (position != mData.size()) {
//            otifyItemRangeChanged(position, mData.size() - position);
//        }


    }

//    public ArrayList<ItemBean> getData() {
//        for (int i = 0; i < datas.size(); i++) {
//            ItemBean itemBean = datas.get(i);
//            itemBean.setType_name(typeArrays[hashMap.get(i)]);
//        }
//        return datas;
//    }

//    //设置状态
//    private void check(int position) {
//        for (ItemBean l : datas) {
//            l.setFocus(false);
//        }
//        datas.get(position).setFocus(true);
//    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView Add_btn;
        private EditText edit_name;
        private EditText edit_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            Add_btn = itemView.findViewById(R.id.Add_btn);
            edit_name = itemView.findViewById(R.id.edit_name);
            edit_phone = itemView.findViewById(R.id.edit_phone);
        }
    }
}
