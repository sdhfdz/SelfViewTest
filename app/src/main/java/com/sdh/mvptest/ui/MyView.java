package com.sdh.mvptest.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.sdh.mvptest.R;

/**
 * Created by sdh on 2016/10/8.
 */
public class MyView extends View {
    private  int mColor;
    private  int mMode;
    private  int mWaveWidth;
    private  int mWaveCount;
    private Context mcontext;
    private int mWidth;
    private float mRectWidth;
    private int mHeight;
    private float mRectHeight;
    private float mWaveHeight;

    public MyView(Context context) {

        this(context, null);

    }

    public MyView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.WaveView,defStyleAttr,0);
        mWaveCount = a.getInt(R.styleable.WaveView_waveCount,10);
        mWaveWidth = a.getInt(R.styleable.WaveView_waveWidth,20);
        mMode = a.getInteger(R.styleable.WaveView_mode,-2);
        mColor = a.getColor(R.styleable.WaveView_android_color, Color.parseColor("#2C97DE"));
    }

    private void init(Context context) {
        this.mcontext=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            mRectWidth = (float) (mWidth * 0.8);
        }else if(widthMode == MeasureSpec.AT_MOST){
            mWidth = PxUtils.dpToPx(300,mcontext);

            mRectWidth = (float) (mWidth * 0.8);

        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
            mRectHeight = (float) (mHeight * 0.8);

        }else if(heightMode == MeasureSpec.AT_MOST){

            mHeight = PxUtils.dpToPx(200,mcontext);

            mRectHeight = (float) (mHeight * 0.8);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(mColor);

        //计算每个三角形的高
        mWaveHeight = mRectHeight / mWaveCount;

        //绘制矩形

        //计算padding
        float padding = ((mWidth - mRectWidth) / 2);
        canvas.drawRect(padding, padding, mRectWidth + padding, mRectHeight + padding, p);
        if (mMode==-2){
            float startX = padding + mRectWidth;
            float startY = padding;
            Path path = new Path();
            path.moveTo(startX, startY);
            for (int i = 0; i < mWaveCount; i++) {
                path.lineTo(startX + mWaveWidth, startY + i * mWaveHeight + (mWaveHeight / 2));
                path.lineTo(startX, startY + mWaveHeight * (i + 1));

            }
            path.close();
            canvas.drawPath(path, p);
        }
        super.onDraw(canvas);
    }
}
