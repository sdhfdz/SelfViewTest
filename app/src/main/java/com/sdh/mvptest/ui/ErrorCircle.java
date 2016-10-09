package com.sdh.mvptest.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by sdh on 2016/10/8.
 */
public class ErrorCircle extends View{
    private int mWidth;
    private int mHeight;
    private int mProgress;

    private int mLineOneX = 0;
    private int mLineOneY = 0;

    private int mLineTwoX;
    private int mLineTwoY;

    private boolean isLineDrawDone = false;
    private boolean isDrawDone = false;

    private int mScroll;
    private OnStopListener mOnStopListener;
    public ErrorCircle(Context context) {
        super(context);
    }

    public ErrorCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = 200;
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 200;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setStrokeWidth(10);
        p.setAntiAlias(true);
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(0 + 10, 0 + 10, mWidth - 10, mHeight - 10);
        canvas.drawArc(rectF, 180, 360 * mProgress / 100, false, p);
        mProgress+=5;

        if (mProgress > 100) {
            //画左边的线
            if (mLineOneX < mWidth * 0.5) {
                mLineOneX+=20;
                mLineOneY+=20;
            }
            if (mLineOneX>=mWidth*0.5){
                mLineOneX=(int)(mWidth*0.5);
            }
            if (mLineOneY>=mHeight*0.5){
                mLineOneY=(int)(mHeight*0.5);
            }
            canvas.drawLine(mWidth / 4, mHeight / 4, mWidth / 4 + mLineOneX, mHeight / 4 + mLineOneY, p);


            if (mLineOneX >= mWidth * 0.5) {
                if (mLineTwoX < mWidth * 0.5) {
                    mLineTwoX+=20;
                    mLineTwoY+=20;
                }else {
                    //判断全部绘制完成
                    isLineDrawDone = true;
                }
                if (mLineTwoX>=mWidth*0.5){
                    mLineTwoX=(int)(mWidth*0.5);
                }
                if (mLineTwoY>=mHeight*0.5){
                    mLineTwoY=(int)(mHeight*0.5);
                }
                canvas.drawLine(mWidth / 4, (float) (mHeight * 0.75), mWidth / 4 + mLineTwoX, (float) (mHeight * 0.75) - mLineTwoY, p);


            }


        }


        if(isLineDrawDone){
            Log.e("wing","draw done");
            if(!isDrawDone) {
                if (mOnStopListener != null) {
                    mOnStopListener.onStop(this);
                }
                isDrawDone = true;
            }

        }else{

            postInvalidateDelayed(10);
        }


        super.onDraw(canvas);
    }
    public void SetOnStopListener(OnStopListener listener){
        this.mOnStopListener=listener;
    }

    public interface OnStopListener{
        void onStop(View view);
    }
    public void reset() {
        mProgress = 0;
        mLineOneX = 0;
        mLineOneY = 0;
        mLineTwoX = 0;
        mLineTwoY = 0;
        isLineDrawDone = false;
        isDrawDone = false;
        invalidate();
    }
}
