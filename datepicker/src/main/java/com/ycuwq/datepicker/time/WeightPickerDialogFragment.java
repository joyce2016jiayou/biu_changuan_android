package com.ycuwq.datepicker.time;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.ycuwq.datepicker.R;

/**
 * 时间选择器，弹出框
 * Created by ycuwq on 2018/1/6.
 */
public class WeightPickerDialogFragment extends DialogFragment {

	protected WeightPicker mDatePicker;
	private int mSelectedWeight = -1;
	private int mSelectedXiaoshu = -1;
	private WeightPicker.OnTimeSelectedListener mOnDateChooseListener;
	private boolean mIsShowAnimation = true;
	protected Button mCancelButton, mDecideButton;

	public void setOnDateChooseListener(WeightPicker.OnTimeSelectedListener onDateChooseListener) {
		mOnDateChooseListener = onDateChooseListener;
	}

	public void showAnimation(boolean show) {
		mIsShowAnimation = show;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_time, container);

		mDatePicker = view.findViewById(R.id.dayPicker_dialog);
		mCancelButton = view.findViewById(R.id.btn_dialog_date_cancel);
		mDecideButton = view.findViewById(R.id.btn_dialog_date_decide);
		mDatePicker.setShowCurtain(false);
		mDatePicker.setShowCurtainBorder(false);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		mDecideButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnDateChooseListener != null) {
					mOnDateChooseListener.onTimeSelected(mDatePicker.getHour(),mDatePicker.getMinute());
				}
				dismiss();
			}
		});

		if (mSelectedWeight > 0) {
			setSelectedDate();
		}

		initChild();
		return view;
	}

	protected void initChild() {

	}

	@Override
	public void onStart() {
		super.onStart();
		//设置背景半透明
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.DatePickerBottomDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

		dialog.setContentView(R.layout.dialog_date);
		dialog.setCanceledOnTouchOutside(true); // 外部点击取消

		Window window = dialog.getWindow();
		if (window != null) {
			if (mIsShowAnimation) {
				window.getAttributes().windowAnimations = R.style.DatePickerDialogAnim;
			}
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.gravity = Gravity.BOTTOM; // 紧贴底部
			lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
			lp.dimAmount = 0.35f;
			window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}

		return dialog;
	}

	public void setSelectedDate(int weight,int xiaoshu) {
		mSelectedWeight = weight;
		mSelectedXiaoshu = xiaoshu;
		setSelectedDate();
	}

	private void setSelectedDate() {
		if (mDatePicker != null) {
			mDatePicker.setTime(mSelectedWeight,mSelectedXiaoshu);
		}
	}

	public interface OnDateChooseListener {
		void onDateChoose(int weight,int xiaoshu);
	}


}
