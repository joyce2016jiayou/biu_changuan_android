package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AreaSubmitAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<String> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private TextView yaoqing_number_tv;
    private int select_num;
    private int max_selectnum = 5;

    public AreaSubmitAdapter(List<String> mlist, Activity mcontext) {
        list = mlist;
        context = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        YouYangViewHolder youYangViewHolder = new YouYangViewHolder(view, false);
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
            item_view = LayoutInflater.from(context).inflate(R.layout.daywhatch_empty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == TYPE_YOUTANG) {
            item_view = LayoutInflater.from(context).inflate(R.layout.area_submit_item, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(v, position);
//                    }
                    submit_jujue_popwindow(holder);

                }
            });
            holder.agree_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.agree_icon.setVisibility(View.VISIBLE);
                    holder.jujue_icon.setVisibility(View.GONE);
                    holder.submit_layout_center.setVisibility(View.GONE);
                    //弹窗提示同意成功
                    SuperCustomToast toast = SuperCustomToast.getInstance(context);
                    toast.setDefaultTextColor(context.getResources().getColor(R.color.top_heiziti));
                    toast.show("提交成功！", R.layout.success_toast, R.id.content_toast, context);

                }
            });

            holder.jujue_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.jujue_edit_layout.setVisibility(View.VISIBLE);
                }
            });
            //拒绝原因提交按钮
            holder.submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.agree_icon.setVisibility(View.GONE);
                    holder.jujue_icon.setVisibility(View.VISIBLE);
                    holder.jujue_edit_layout.setVisibility(View.GONE);
                    holder.submit_layout_center.setVisibility(View.GONE);
                    //弹窗提示拒绝成功
                    SuperCustomToast toast = SuperCustomToast.getInstance(context);
                    toast.setDefaultTextColor(context.getResources().getColor(R.color.top_heiziti));
                    toast.show("提交成功！", R.layout.success_toast, R.id.content_toast, context);
                }
            });


        }
    }

    public static Dialog jujue_dialog;

    private void submit_jujue_popwindow(YouYangViewHolder holder) {
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.jujue_pop_detial, null);
        jujue_dialog = new Dialog(context, R.style.transparentFrameWindowStyle2);
        jujue_dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = jujue_dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        jujue_dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        jujue_dialog.setCanceledOnTouchOutside(true);
        jujue_dialog.show();
        /**操作*/
        LinearLayout agree_btn = view.findViewById(R.id.agree_btn);
        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jujue_dialog.dismiss();
                holder.agree_icon.setVisibility(View.VISIBLE);
                holder.jujue_icon.setVisibility(View.GONE);
                holder.submit_layout_center.setVisibility(View.GONE);
                //弹窗提示同意成功
                SuperCustomToast toast = SuperCustomToast.getInstance(context);
                toast.setDefaultTextColor(context.getResources().getColor(R.color.top_heiziti));
                toast.show("提交成功！", R.layout.success_toast, R.id.content_toast, context);

            }
        });
        LinearLayout jujue_btn = view.findViewById(R.id.jujue_btn);
        LinearLayout submit_layout_center = view.findViewById(R.id.submit_layout_center);
        LinearLayout jujue_edit_layout = view.findViewById(R.id.jujue_edit_layout);
        LinearLayout submit_btn = view.findViewById(R.id.submit_btn);

        //拒绝按钮
        jujue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_layout_center.setVisibility(View.GONE);
                jujue_edit_layout.setVisibility(View.VISIBLE);

            }
        });
        //拒绝提交按钮
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jujue_dialog.dismiss();
                holder.agree_icon.setVisibility(View.GONE);
                holder.jujue_icon.setVisibility(View.VISIBLE);
                holder.jujue_edit_layout.setVisibility(View.GONE);
                holder.submit_layout_center.setVisibility(View.GONE);
                //弹窗提示拒绝成功
                SuperCustomToast toast = SuperCustomToast.getInstance(context);
                toast.setDefaultTextColor(context.getResources().getColor(R.color.top_heiziti));
                toast.show("提交成功！", R.layout.success_toast, R.id.content_toast, context);
            }
        });

    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (list.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return TYPE_YOUTANG;
        }
    }


    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }


    public void setData(List<String> list) {
        this.list = list;
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


    public class YouYangViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public LinearLayout agree_btn, jujue_btn, agree_icon, jujue_icon, jujue_edit_layout, submit_layout_center, submit_btn;
        public TextView title_tv, tv_date_time;
        public CircleImageView touxiang_image;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                agree_btn = view.findViewById(R.id.agree_btn);
                title_tv = view.findViewById(R.id.title_tv);
                tv_date_time = view.findViewById(R.id.tv_date_time);
                touxiang_image = view.findViewById(R.id.touxiang_image);
                jujue_btn = view.findViewById(R.id.jujue_btn);
                agree_icon = view.findViewById(R.id.agree_icon);
                jujue_icon = view.findViewById(R.id.jujue_icon);
                jujue_edit_layout = view.findViewById(R.id.jujue_edit_layout);
                submit_layout_center = view.findViewById(R.id.submit_layout_center);
                submit_btn = view.findViewById(R.id.submit_btn);
            }
        }
    }
}
