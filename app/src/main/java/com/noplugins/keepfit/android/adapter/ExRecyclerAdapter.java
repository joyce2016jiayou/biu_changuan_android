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
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.ItemBean;

import java.util.ArrayList;

import lib.demo.spinner.MaterialSpinner;

/**
 * Created on 2018/12/20 10:21
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks: Recycler 解决editView 复用的adapter
 */
public class ExRecyclerAdapter extends RecyclerView.Adapter<ExRecyclerAdapter.ViewHolder> {

    private ArrayList<ItemBean> datas;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private Context mcontext;

    public ExRecyclerAdapter(Context context, ArrayList<ItemBean> data, int layoutId) {
        this.datas = data;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayoutId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //第0 不能有删除功能
        if (position == 0) {
            holder.Add_btn.setImageResource(R.drawable.jiahao);
        } else {
            holder.Add_btn.setImageResource(R.drawable.jianhao);
        }
        //判断是否有TextWatcher监听事件，有的话先移除
        if (holder.xianzhi_number.getTag(R.id.xianzhi_number) instanceof TextWatcher) {
            holder.xianzhi_number.removeTextChangedListener(((TextWatcher) holder.xianzhi_number.getTag(R.id.xianzhi_number)));
        }
        //移除了TextWatcher事件后设置item对应的文本
        holder.xianzhi_number.setText(datas.get(position).getPlace());

        //设置焦点
        if (datas.get(position).isFocus()) {
            if (!holder.xianzhi_number.isFocused()) {
                holder.xianzhi_number.requestFocus();
            }
            CharSequence text = datas.get(position).getPlace();
            holder.xianzhi_number.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
        } else {
            if (holder.xianzhi_number.isFocused()) {
                holder.xianzhi_number.clearFocus();
            }
        }
        //edit 事件处理
        holder.xianzhi_number.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                final boolean focus = datas.get(position).isFocus();
                check(position);
                if (!focus && !holder.xianzhi_number.isFocused()) {
                    holder.xianzhi_number.requestFocus();
                    holder.xianzhi_number.onWindowFocusChanged(true);
                }
            }
            return false;
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    datas.get(position).setPlace(null);
                } else {
                    //监听edit 值
                    datas.get(position).setPlace(s.toString());
                }
            }
        };
        //设置tag为TextWatcher
        holder.xianzhi_number.addTextChangedListener(textWatcher);
        holder.xianzhi_number.setTag(R.id.xianzhi_number, textWatcher);

        //添加
        holder.Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0){//表示添加
                    addData(new ItemBean());
                }else{//表示删除
                    if (position != 0) {
                        datas.remove(position);
                        notifyItemRemoved(position);
                        if (position != datas.size()) {
                            notifyItemRangeChanged(position, getItemCount());
                        }
//                    datas.remove(position);
//                    notifyDataSetChanged();
                    }
                }
            }
        });

        //设置下拉选择框
        String[] typeArrays = mcontext.getResources().getStringArray(R.array.gongneng_types);
        holder.spinner_changsuo_type.setItems(typeArrays);
        holder.spinner_changsuo_type.setSelectedIndex(0);
        holder.spinner_changsuo_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });
        holder.spinner_changsuo_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });



    }

    //  添加数据
    public void addData(ItemBean itemBean) {
        if(datas.size()==3){
            Toast.makeText(mcontext,R.string.tv26,Toast.LENGTH_SHORT).show();
        }else{
            itemBean.setFocus(true);
//      在list中添加数据，并通知条目加入一条
            datas.add(datas.size(), itemBean);
            //添加动画
            notifyItemInserted(datas.size());
//        if (position != mData.size()) {
//            otifyItemRangeChanged(position, mData.size() - position);
//        }
        }

    }

    //设置状态
    private void check(int position) {
        for (ItemBean l : datas) {
            l.setFocus(false);
        }
        datas.get(position).setFocus(true);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView Add_btn;
        private EditText xianzhi_number;
        private MaterialSpinner spinner_changsuo_type;

        public ViewHolder(View itemView) {
            super(itemView);
            xianzhi_number= itemView.findViewById(R.id.xianzhi_number);
            Add_btn = itemView.findViewById(R.id.Add_btn);
            spinner_changsuo_type = itemView.findViewById(R.id.spinner_changsuo_type);

        }


    }
}
