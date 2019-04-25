package com.taotao.tao_oat_lib.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * @package com.guotaijun.gtj.guotaijun.wedget
 * @file QuestionProgressBar
 * @date 2018/8/17  下午3:19
 * @autor wangxiongfeng
 * 绘制 横向的数值 还绘制了进度条。
 */
public class QuestionProgressBar extends View implements Runnable {

    private int mWidth;
    private int mHeight;


    private String beforColor = "#517dbc";
    private String currentColor = "#666666";
    private String affertColor = "#6c6c6c";

    private int commonNumSize = 35;

    private int choiseNumSize = 45;

    private int choiseNum = 1;

    int proCicrle;   //圆角半径


    private int distance;

    private int spacing;


    int COLOR_BLUE = 0xff538ede;
    int COLOR_GREY = 0xffd8dbde;


    private Paint mProPaint;
    private Paint mNumPaint;
    private int mSize;

    private float progress = 0;

    private int maxVaule = 10;

    private int paddMiddle = dp2px(10);

    private int progressHeight = dp2px(10);

    private int progressLeng;
    private int mBigNumSize;
    private float mPercentage = 1.0f;
    private float mOldRight;
    private int value = 1; //1 向前 -1 向后

    public QuestionProgressBar(Context context) {
        super(context);
        init();
    }

    public QuestionProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuestionProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        //数字画笔
        mNumPaint = new Paint();
        mNumPaint.setAntiAlias(true);  //抗锯齿
        mNumPaint.setStyle(Paint.Style.FILL);//充满
        mNumPaint.setDither(true);// 设置抖动,颜色过渡更均匀
        mNumPaint.setStrokeCap(Paint.Cap.ROUND);


        //进度条画笔
        mProPaint = new Paint();
        mProPaint.setAntiAlias(true);  //抗锯齿
        mProPaint.setStyle(Paint.Style.FILL);//充满
        mProPaint.setDither(true);// 设置抖动,颜色过渡更均匀
        mProPaint.setStrokeCap(Paint.Cap.ROUND);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        mWidth = w;
        mHeight = h;

        int left = getPaddingLeft();
        int right = getPaddingRight();

        mWidth = mWidth - (left + right);
        int top = getPaddingTop();
        int bottom = getPaddingBottom();
        mHeight = mHeight - (top + bottom);


        distance = (w - getLastNumSize() - right - left) / (maxVaule - 1);

        spacing = mHeight / 2;

        proCicrle = progressHeight / 2;
        progressLeng = w - getPaddingRight() - getPaddingLeft();


        mBigNumSize = getLastBigNumSize() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float v = drawNum(canvas);

        drawProgress(canvas, v);
    }

    /**
     * 绘制文字
     */
    private float drawNum(Canvas canvas) {
        float position = getPaddingLeft();
        for (int i = 0; i < maxVaule; i++) {
            int num = i + 1;
            mNumPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mNumPaint.setTextSize(dp2px(12));

            String str = "";
            if (num < 10) {
                str = "0" + num;
            } else {
                str = num + "";
            }
            if (num == 1) {
                Rect rect = new Rect();
                mNumPaint.getTextBounds(str, 0, str.length(), rect);
                mSize = rect.width();
            }
            //x坐标等于=中间-字符串宽度的一般
            float xPos = distance * i;
            float yPos = spacing - paddMiddle;


            if (num < choiseNum) {
                mNumPaint.setColor(Color.parseColor(beforColor));
                canvas.drawText(str, xPos + getPaddingLeft(), yPos, mNumPaint);
            }
            if (num > choiseNum) {
                mNumPaint.setColor(Color.parseColor(affertColor));
                canvas.drawText(str, xPos + getPaddingLeft(), yPos, mNumPaint);
            }
            //选中的状态
            if (num == choiseNum) {
                mNumPaint.setColor(Color.parseColor(currentColor));
                mNumPaint.setTextSize(dp2px(12) * (1 + (0.5f * mPercentage)));
                Log.d("choiseNum", dp2px(12) * (1 + (0.5f * mPercentage)) + "  ");

                mNumPaint.setFakeBoldText(true);//设置是否为粗体文字
                float xXpost = distance * (i - 1) + ((distance * 2 - mSize) / 2 - mSize / 2) + mSize / 3 * 2 + getPaddingLeft();
                canvas.drawText(str, xXpost, yPos, mNumPaint);
                position = xXpost + mBigNumSize;
            }


            if (num == choiseNum + value * -1) {
                mNumPaint.setTextSize(dp2px(12) * (1 + (0.5f * (1 - mPercentage))));

                if (num < choiseNum) {
                    mNumPaint.setColor(Color.parseColor(beforColor));
                    canvas.drawText(str, xPos + getPaddingLeft(), yPos, mNumPaint);
                }
                if (num > choiseNum) {
                    mNumPaint.setColor(Color.parseColor(affertColor));
                    canvas.drawText(str, xPos + getPaddingLeft(), yPos, mNumPaint);
                }
            }


            mNumPaint.reset();//重置画笔
        }
        return position;
    }


    /**
     * 绘制进度条
     */
    public void drawProgress(Canvas canvas, float progress) {
        mProPaint.setColor(COLOR_GREY);
        int top = spacing + paddMiddle;
        int bottom = spacing + paddMiddle + progressHeight;
        RectF rectBg = new RectF(getPaddingLeft(), top, progressLeng, bottom);// 设置个新的长方形
        canvas.drawRoundRect(rectBg, proCicrle, proCicrle, mProPaint);//第二个参数是x半径，第三个参数是y半径


        mProPaint.setColor(COLOR_BLUE);
        float right = progress;
//        Log.i("main", "right  ==" + right + "  /width =" + width);
        RectF oval3 = new RectF(getPaddingLeft(), top, (right - distance * value) + (distance * mPercentage) * value, bottom);// 设置个新的长方形

        canvas.drawRoundRect(oval3, proCicrle, proCicrle, mProPaint);//第二个参数是x半径，第三个参数是y半径
        Log.d("drawProgress", right + "    mOldRight    " + mOldRight + "     (right - mOldRight) * mPercentage   " + (right - mOldRight) * mPercentage);

        mOldRight = right;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < maxVaule; i++) {
                Thread.sleep(500);

                choiseNum++;

                progress = 100 / 23 * choiseNum;
                if (choiseNum > maxVaule)
                    return;

                postInvalidate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChoiseNum(int num) {
        choiseNum = num;
    }

    public int getLastNumSize() {
        mNumPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mNumPaint.setTextSize(dp2px(12));
        Rect rect = new Rect();
        String s = "00";
        mNumPaint.getTextBounds(s, 0, s.length(), rect);
        return mSize = rect.width();

    }

    public int getLastBigNumSize() {
        mNumPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mNumPaint.setTextSize(dp2px(12) * 1.5f);
        Rect rect = new Rect();
        String s = "00";
        mNumPaint.getTextBounds(s, 0, s.length(), rect);
        return mSize = rect.width();

    }


    public void setDistancePercentage(float value, int direction) {
        mPercentage = value;
        this.value = direction;
        postInvalidate();
        Log.e("addOnPageChangeListener", "mPercentage     " + mPercentage + "      setChoiseNum  " + choiseNum);

    }


}
