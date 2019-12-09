package com.noplugins.keepfit.android.util.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 资料填写进度
 */
public class StepView extends View {
    private static final int minHorizontalSpace = 100;  //左右两侧保留的最小距离
    private int circleRadius = 20;  //圆半径
    private float circleMultiple = 1.5f;    //当前进度圆与正常圆的比例
    private int mWidth;
    private int paddingLeft;
    private int paddingRight;
    private int paddingBottom;
    private int paddingTop;
    private Paint circlePaint;  //画圆画笔
    private Paint textPaint;    //文字画笔
    private Paint linePaint;    //进度线画笔
    private int stepNum = 0;
    private int currentStep = 0;    //当前步骤(从0开始)
    private int lineDoneColor = Color.parseColor("#FFB90F");
    private int lineDefaultColor = Color.parseColor("#FFE4E1");
    private int circleSelectColor = Color.parseColor("#F9CE0F");
    private int circleDefaultColor = Color.parseColor("#FFE4E1");
    private int textSelectColor = Color.parseColor("#F9CE0F");
    private int textDefaultColor = Color.parseColor("#FFE4E1");
    private String[] titles;

    public StepView(Context context) {
        super(context);
        init();
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //初始化进度圆画笔
        circlePaint = new Paint();
        circlePaint.setColor(circleDefaultColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        //初始化文字画笔
        textPaint = new Paint();
        textPaint.setColor(textDefaultColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);

        //初始化进度线画笔
        linePaint = new Paint();
        linePaint.setColor(lineDefaultColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widhtSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //左右两边要保留一定距离,否则显示不完全
        paddingLeft = Math.max(getPaddingLeft(), minHorizontalSpace);
        paddingRight = Math.max(getPaddingRight(), minHorizontalSpace);
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();

        mWidth = widhtSize;
        //对整体View的高度控制,保证可以完整显示
        int mHeight = (int) Math.min(heightSize, 2 * circleMultiple * circleRadius + 120) + paddingTop + paddingBottom;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (titles == null || titles.length == 0) {
            return;
        }

        //①计算进度线的长度
        int space = (mWidth - stepNum * circleRadius * 2 - paddingLeft - paddingRight) / (stepNum - 1);


        //进度线和进度圆的Y坐标
        float mY = circleMultiple * circleRadius + paddingTop;

        //②画进度线
        float lineLeft; //左起点
        float lineRight;//右终点
        for (int i = 0; i < stepNum; i++) {
            linePaint.setColor(i <= currentStep ? lineDoneColor : lineDefaultColor);
            lineLeft = Math.max(i, 1) * 2 * circleRadius + Math.max(i - 1, 0) * space + paddingLeft;
            lineRight = Math.max(i, 1) * 2 * circleRadius + i * space + paddingLeft;
            RectF rectf = new RectF(lineLeft, mY - 4, lineRight, mY + 4);
            canvas.drawRect(rectf, linePaint);
        }

        //③画进度圆
        float x; //圆心横坐标
        for (int i = 0; i < stepNum; i++) {
            circlePaint.setColor(i <= currentStep ? circleSelectColor : circleDefaultColor);
            x = (float) ((i * 2 + 1) * circleRadius + i * space) + paddingLeft;
            canvas.drawCircle(x, mY, i == currentStep ? circleRadius * circleMultiple : circleRadius, circlePaint);
        }


        //④进度圆内的文字
        for (int i = 0; i < stepNum; i++) {
            textPaint.setColor(i == currentStep ? textDefaultColor : textSelectColor);
            canvas.drawText(i == currentStep ? (i + 1) + "" : "", (i * 2 + 1) * circleRadius + i * space + paddingLeft, mY + 13, textPaint);
        }

        //⑤具体进度文案
        float textY = mY + circleMultiple * circleRadius + 55;//设置文字坐标
        for (int i = 0; i < stepNum; i++) {
            textPaint.setColor(i <= currentStep ? textSelectColor : textDefaultColor);
            canvas.drawText(titles[i], (i * 2 + 1) * circleRadius + i * space + paddingLeft, textY, textPaint);
        }

    }

    //设置进度标题数组
    public void setTitles(String[] titles) {
        this.titles = titles;
        this.stepNum = titles.length;
        postInvalidate();
    }

    //获取总进度数
    public int getStepNum() {
        return stepNum;
    }

    //设置当前进度
    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
        postInvalidate();
    }

    //获取当前进度
    public int getCurrentStep() {
        return currentStep;
    }

}
