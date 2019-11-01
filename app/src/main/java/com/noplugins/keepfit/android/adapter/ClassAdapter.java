package com.noplugins.keepfit.android.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.RiChengBean;
import com.noplugins.keepfit.android.fragment.RiChengFragment;
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ClassAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> implements EasyPermissions.PermissionCallbacks {
    private static final int EMPTY_VIEW = 2;
    private static final int ITEM_VIEW = 3;
    RiChengFragment context;
    List<RiChengBean.ResultBean> classDateBeans;
    public static final int PERMISSION_STORAGE_CODE = 10001;
    public static final String PERMISSION_STORAGE_MSG = "需要电话权限才能联系客服哦";
    public static final String[] PERMISSION_STORAGE = new String[]{Manifest.permission.CALL_PHONE};

    public ClassAdapter(List<RiChengBean.ResultBean> m_classDateBean, RiChengFragment m_context) {
        classDateBeans = m_classDateBean;
        context = m_context;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {

        WeiJieShuViewHolder youYangViewHolder = new WeiJieShuViewHolder(view, false);
        return youYangViewHolder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType, parent);
        return holder;
    }

    RecyclerView.ViewHolder holder = null;

    private RecyclerView.ViewHolder getViewHolderByViewType(int viewType, ViewGroup parent) {
        View item_view = null;
        if (viewType == EMPTY_VIEW) {
            item_view = LayoutInflater.from(context.getActivity()).inflate(R.layout.select_date_empty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == ITEM_VIEW) {
            item_view = LayoutInflater.from(context.getActivity()).inflate(R.layout.class_date_item, parent, false);
            holder = new WeiJieShuViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
        if (view_holder instanceof WeiJieShuViewHolder) {
            WeiJieShuViewHolder holder = (WeiJieShuViewHolder) view_holder;
            RiChengBean.ResultBean resultBean = classDateBeans.get(position);
            holder.coach_name.setText(resultBean.getUserName());
            holder.status_tv.setText(resultBean.getUserCheckIn());
            if (resultBean.getCourseStatus() == 1) {//已结束
                holder.status_img.setImageResource(R.drawable.over);
            } else if (resultBean.getCourseStatus() == 2) {//未开始
                holder.status_img.setImageResource(R.drawable.weikaishi_icon);
            } else {//进行中
                holder.status_img.setImageResource(R.drawable.ongoing);
            }
            if (resultBean.getCourseType() == 1) {//团课
                holder.type_icon_bg.setBackgroundResource(R.drawable.zi_bg);
            } else if (resultBean.getCourseType() == 2) {//私教
                holder.type_icon_bg.setBackgroundResource(R.drawable.trainer);
            } else {//健身
                holder.type_icon_bg.setBackgroundResource(R.drawable.venue);
            }
            String daochang_person = "";
            if (resultBean.getApplayNum().length() > 0) {
                daochang_person = resultBean.getApplayNum();
            } else {
                daochang_person = "0";
            }
            String max_person = "";
            if (resultBean.getMaxNum().length() > 0) {
                max_person = resultBean.getMaxNum();
            } else {
                max_person = "0";
            }
            holder.phone_or_name_tv.setText(daochang_person + "/" + max_person + "人");
            holder.time_tv.setText(resultBean.getCourseTime());
            holder.class_type.setText(resultBean.getClassName());
            holder.money_tv.setText(resultBean.getPrice());
            holder.phone_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call_pop(holder, resultBean.getTeacherPhone());
                }
            });
        }


    }

    private void call_pop(WeiJieShuViewHolder holder, String phone_number) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context.getActivity())
                .setView(R.layout.call_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(holder.phone_btn);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        LinearLayout cancel_layout = view.findViewById(R.id.cancel_layout);
        LinearLayout sure_layout = view.findViewById(R.id.sure_layout);
        TextView content_tv = view.findViewById(R.id.content_tv);
        content_tv.setText("确认拨打 " + phone_number + "?");
        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSimple(phone_number);
                popupWindow.dismiss();
            }
        });


    }

    @AfterPermissionGranted(PERMISSION_STORAGE_CODE)
    public void initSimple(String phone_number) {
        if (hasStoragePermission(context.getActivity())) {
            //有权限
            callPhone(phone_number);
        } else {
            //申请权限
            EasyPermissions.requestPermissions(context, PERMISSION_STORAGE_MSG, PERMISSION_STORAGE_CODE, PERMISSION_STORAGE);
        }
    }

    public void callPhone(String phoneNum) {
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent1.setData(data);
        context.startActivity(intent1);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        return EasyPermissions.hasPermissions(context, permissions);
    }

    public static boolean hasStoragePermission(Context context) {
        return hasPermissions(context, PERMISSION_STORAGE);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(context, perms)) {
            new AppSettingsDialog.Builder(context)
                    .setTitle("提醒")
                    .setRationale("需要电话权限才能联系客服哦")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, context);

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    int RETUEN_CODE = 0;

    @Override
    public int getAdapterItemViewType(int position) {
        if (classDateBeans.size() == 0) {
            RETUEN_CODE = EMPTY_VIEW;
        } else {
            RETUEN_CODE = ITEM_VIEW;

        }
        return RETUEN_CODE;
    }


    @Override
    public int getAdapterItemCount() {
        return classDateBeans.size() > 0 ? classDateBeans.size() : 1;
    }


    public void setData(List<RiChengBean.ResultBean> list) {
        this.classDateBeans = list;
        notifyDataSetChanged();
    }


    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public EmptyViewHolder(View item_view, boolean isItem) {
            super(item_view);
            if (isItem) {
                this.view = item_view;
            }
        }
    }

    public class WeiJieShuViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView coach_name, status_tv, type_icon_tv, phone_or_name_tv, time_tv, class_type, money_tv;
        public ImageView phone_btn, status_img;
        public LinearLayout type_icon_bg;

        public WeiJieShuViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                coach_name = view.findViewById(R.id.coach_name);
                phone_btn = view.findViewById(R.id.phone_btn);
                status_tv = view.findViewById(R.id.status_tv);
                type_icon_tv = view.findViewById(R.id.type_icon_tv);
                type_icon_bg = view.findViewById(R.id.type_icon_bg);
                phone_or_name_tv = view.findViewById(R.id.phone_or_name_tv);
                status_img = view.findViewById(R.id.status_img);
                time_tv = view.findViewById(R.id.time_tv);
                class_type = view.findViewById(R.id.class_type);
                money_tv = view.findViewById(R.id.money_tv);
            }
        }
    }


}
