package com.noplugins.keepfit.android.util.ui.chart;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class MyBarChartRenderer extends BarChartRenderer {
    private RectF mBarShadowRectBuffer = new RectF();


    public MyBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.mChart = chart;
        mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHighlightPaint.setColor(Color.rgb(0, 0, 0));
        mHighlightPaint.setAlpha(120);
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mBarBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // draw the bar shadow before the values
        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());

            BarData barData = mChart.getBarData();

            final float barWidth = barData.getBarWidth();
            final float barWidthHalf = barWidth / 2.0f;
            float x;

            for (int i = 0, count = Math.min((int)(Math.ceil((float)(dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {
                BarEntry e = dataSet.getEntryForIndex(i);
                x = e.getX();
                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;
                trans.rectValueToPixel(mBarShadowRectBuffer);
                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right)){
                    continue;
                }
                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left)){
                    break;
                }
                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();
                c.drawRect(mBarShadowRectBuffer, mShadowPaint);
            }
        }
        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());
        buffer.feed(dataSet);
        trans.pointValuesToPixel(buffer.buffer);
        final boolean isSingleColor = dataSet.getColors().size() == 1;
//        if (isSingleColor) {
            mRenderPaint.setColor(dataSet.getColor());
//        }
        for (int j = 0; j < buffer.size(); j += 4) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])){
                continue;
            }
            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) {
                break;
            }
            //isSingleColor是不是只有一个颜色  防止不需要渐变色的柱状图
            //下面是向画笔添加渐变的的不同的级别颜色不同
//            if (!isSingleColor) {
//
//            }else{
//                Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#70AAFD"),
//                        Color.parseColor("#0D70FC"), Shader.TileMode.CLAMP);
////                mRenderPaint.setShader(shader);
//                mRenderPaint.setColor(Color.parseColor("#0D70FC"));
//            }
//            if (dataSet.getEntryForIndex(j/4).getY() > 140){
//                Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#F37779"),
//                        Color.parseColor("#DA1738"), Shader.TileMode.CLAMP);
//                mRenderPaint.setColor(Color.parseColor("#DA1738"));
//            } else if(dataSet.getEntryForIndex(j/4).getY() < 30){
//                Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#70AAFD"),
//                        Color.parseColor("#0D70FC"), Shader.TileMode.CLAMP);
////                    mRenderPaint.setShader(shader);
//                mRenderPaint.setColor(Color.parseColor("#0D70FC"));
//            } else {
//                Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#71A4A7"),
//                        Color.parseColor("#0E9355"), Shader.TileMode.CLAMP);
////                    mRenderPaint.setShader(shader);
//                mRenderPaint.setColor(Color.parseColor("#0E9355"));
//            }
            //修改画笔的属性 是否圆头
//            mRenderPaint.setStrokeCap(Paint.Cap.ROUND);
            mRenderPaint.setColor(Color.parseColor("#0E9355"));
            float wtch =  buffer.buffer[j + 2]-buffer.buffer[j];
            mRenderPaint.setStrokeWidth(wtch);
            float allStartY = buffer.buffer[j + 1];
            float allEndY =  buffer.buffer[j + 3];
            float lengthY = allEndY - allStartY;
            c.drawLine(buffer.buffer[j]+wtch/2, allStartY, buffer.buffer[j + 2]-wtch/2,
                    allEndY, mRenderPaint);

            mRenderPaint.setColor(Color.parseColor("#0D70FC"));
            float no3StartY = allStartY + lengthY - lengthY*(0.25f);
            float no3EndY = allEndY;
            float length3Y = no3EndY - no3StartY;
            c.drawLine(buffer.buffer[j]+wtch/2, no3StartY, buffer.buffer[j + 2]-wtch/2,
                    no3EndY, mRenderPaint);

            mRenderPaint.setColor(Color.parseColor("#FF3700"));
            float no2StartY = no3StartY -lengthY*(0.25f);
            float no2EndY = no3StartY;
            c.drawLine(buffer.buffer[j]+wtch/2, no2StartY, buffer.buffer[j + 2]-wtch/2,
                    no2EndY, mRenderPaint);

        }
    }
    /**
     * 手指点击的半透明灰色修改
     * */
    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
//        BarData barData = mChart.getBarData();
//        for (Highlight high : indices) {
//            IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());
//            if (set == null || !set.isHighlightEnabled())
//                continue;
//            BarEntry e = set.getEntryForXValue(high.getX(), high.getY());
//            if (!isInBoundsX(e, set))
//                continue;
//            Transformer trans = mChart.getTransformer(set.getAxisDependency());
//            mHighlightPaint.setColor(set.getHighLightColor());
//            mHighlightPaint.setAlpha(set.getHighLightAlpha());
//            boolean isStack = (high.getStackIndex() >= 0  && e.isStacked()) ? true : false;
//
//            final float y1;
//            final float y2;
//
//            if (isStack) {
//
//                if(mChart.isHighlightFullBarEnabled()) {
//
//                    y1 = e.getPositiveSum();
//                    y2 = -e.getNegativeSum();
//
//                } else {
//
//                    Range range = e.getRanges()[high.getStackIndex()];
//
//                    y1 = range.from;
//                    y2 = range.to;
//                }
//
//            } else {
//                y1 = e.getY();
//                y2 = 0.f;
//            }
//
//            prepareBarHighlight(e.getX(), y1, y2, barData.getBarWidth() / 2f, trans);
//
//            setHighlightDrawPos(high, mBarRect);
//            // c.drawRect(mBarRect, mHighlightPaint);  原父类方法
//            //修改画笔的属性 是否圆头  根据mBarRect的矩形区域算出线的宽度和中心点画线
//            mHighlightPaint.setStrokeCap(Paint.Cap.ROUND);
//            mHighlightPaint.setStrokeWidth(mBarRect.right-mBarRect.left);
//            c.drawLine(mBarRect.centerX(),mBarRect.top,mBarRect.centerX(),mBarRect.bottom,mHighlightPaint);
//        }
    }
}