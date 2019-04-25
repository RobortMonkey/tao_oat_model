package com.taotao.tao_oat_lib.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @package com.taotao.tao_oat_lib.weight
 * @file DragLayout
 * @date 2018/8/29  上午9:32
 * @autor wangxiongfeng
 */
public class DragLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private Scroller scroller = new Scroller(getContext());
    private View mRedView;
    private View mBlueView;

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        /**
         ** （1）forParent ：“用来监视的父View”。传入参数父View，即可监视该父View中的所有子View。
         *  （2）sensitivity：“检测时的敏感度；值越大越敏感，1是正常范围”。比如说手指在滑动屏幕时速度特别快，敏感度越大时，此时速度快也可以检测到，反之亦然。
         *  （3）Callback ：“提供信息和接受的事件”。最重要的参数！
         */
        mViewDragHelper = ViewDragHelper.create(this, slop, mCallback);

    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 此方法用于判断是否捕获当前child的触摸事件，可以指定ViewDragHelper移动哪一个子View。
         * @param child
         * @param pointerId
         * @return
         */

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {


            return false;
        }

        /**
         * 此方法在View被开始捕获和解析时回调，即当tryCaptureView()中的返回值为true的时候，此方法才会被调用。
         * @param capturedChild
         * @param activePointerId
         */

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }


        /**
         * 这两个为具体滑动方法，分别对应水平和垂直方向上的移动
         * @param child
         * @param left
         * @param dx
         * @return
         */

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return super.clampViewPositionHorizontal(child, left, dx);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        /**
         * 获取View的拖拽范围
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return super.getViewVerticalDragRange(child);
        }


        /**
         * 该方法在child（需要捕捉的View）位置改变时执行，参数left（top）跟之前介绍方法中含义相同，
         * 为child最新的left（top）位置，而dx（dy）是child相较于上一次移动时水平（垂直）方向上改变的距离
         * @param state
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        /**
         * 手指抬起的时候执行该方法
         * xvel： x方向移动的速度，若是正值，则代表向右移动，若是负值则向左移动；
         * yvel： y方向移动的速度，若是正值则向下移动，若是负值则向上移动。
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mRedView != null && mBlueView != null) {
            mRedView.layout(0, 0, mRedView.getMeasuredWidth(), mRedView.getMeasuredHeight());
            mBlueView.layout(0, mRedView.getMeasuredHeight(), mBlueView.getMeasuredWidth(), mRedView.getMeasuredHeight() + mBlueView.getMeasuredHeight());
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        //方法一：对子View的测量需求
        /*获取子View的宽度100dp  的两种方法：
        int size = (int) getResources().getDimension(R.dimen.width);
        int size = readView.getLayoutParams().width;*/
//        int measureSpec = MeasureSpec.makeMeasureSpec(mRedView.getLayoutParams().width, MeasureSpec.EXACTLY);//具体指定宽高，为精确模式
//        mRedView.measure(measureSpec, measureSpec);//当父控件测量完子控件，才可以填（0,0）
//        mBlueView.measure(measureSpec, measureSpec);

        //方法二：如果说没有特殊的对子View的测量需求，可用如下方法
//        measureChild(redView, widthMeasureSpec, heightMeasureSpec);
//        measureChild(blueView, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        int count = getChildCount();
        if (count >= 2) {
            mRedView = getChildAt(0);
            mBlueView = getChildAt(1);

            int measureSpec = MeasureSpec.makeMeasureSpec(0, 0);//具体指定宽高，为精确模式
            mRedView.measure(measureSpec, measureSpec);//当父控件测量完子控件，才可以填（0,0）
            mBlueView.measure(measureSpec, measureSpec);

        }

        //方法一：对子View的测量需求
        /*获取子View的宽度100dp  的两种方法：
        int size = (int) getResources().getDimension(R.dimen.width);
        int size = readView.getLayoutParams().width;*/
//        int measureSpec = MeasureSpec.makeMeasureSpec(0, 0);//具体指定宽高，为精确模式
//        mRedView.measure(measureSpec, measureSpec);//当父控件测量完子控件，才可以填（0,0）
//        mBlueView.measure(measureSpec, measureSpec);
//
        //方法二：如果说没有特殊的对子View的测量需求，可用如下方法
//        measureChild(mRedView,widthMeasureSpec,heightMeasureSpec);
//        measureChild(mBlueView,widthMeasureSpec,heightMeasureSpec);


    }

    //处理是否拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //由viewDragHelper 来判断是否应该拦截此事件
        boolean result = mViewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件传给viewDragHelper来解析处理
        mViewDragHelper.processTouchEvent(event);
        //消费掉此事件，自己来处理
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}
