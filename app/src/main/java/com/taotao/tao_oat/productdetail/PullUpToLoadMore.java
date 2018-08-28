package com.taotao.tao_oat.productdetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @package com.taotao.tao_oat.productdetail
 * @file PullUpToLoadMore
 * @date 2018/8/28  上午9:09
 * @autor wangxiongfeng
 */
public class PullUpToLoadMore extends ViewGroup {

    private boolean topScrollViewIsBottom;
    private boolean bottomScrollVIewIsInTop;
    private int scaledTouchSlop;
    private int currPosition;
    Scroller scroller = new Scroller(getContext());
    VelocityTracker velocityTracker = VelocityTracker.obtain();
    private MyScrollView mScrollViewTop;
    private MyScrollView mMScrollViewTop;
    private MyScrollView mScrollViewbottom;
    int speed = 200;
    private int position1Y;

    public PullUpToLoadMore(Context context) {
        super(context);
        init();
    }

    public PullUpToLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullUpToLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        currPosition = 0;
        post(new Runnable() {
            @Override
            public void run() {
                mMScrollViewTop = (MyScrollView) getChildAt(0);
                mMScrollViewTop.setScrollListener(new MyScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {
                        topScrollViewIsBottom = true;

                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {
                    }

                    @Override
                    public void notBottom() {
                        topScrollViewIsBottom = false;

                    }
                });
                mScrollViewbottom = (MyScrollView) getChildAt(1);
                mScrollViewbottom.setScrollListener(new MyScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {

                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {
                        if (scrollY == 0) {
                            bottomScrollVIewIsInTop = true;
                        } else {
                            bottomScrollVIewIsInTop = false;
                        }

                    }

                    @Override
                    public void notBottom() {

                    }
                });

                scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                position1Y = mMScrollViewTop.getBottom();

            }

        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childTop = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, childTop, r, childTop + child.getMeasuredHeight());
            childTop += child.getMeasuredHeight();
        }
    }

    private boolean isIntercept;
    private float lastX;
    private float lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntercept = false;
        float y = ev.getY();
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastY = y;
                lastX = x;

                break;
            case MotionEvent.ACTION_MOVE:

                /**
                 * 当滚动到 底部时 父容器拦截 触摸事件 入容器向下滚动当送开手时 。判断视图当滚动位置。
                 */

                if (topScrollViewIsBottom) {
                    float dy = lastY - y;
                    if (dy > 0 && currPosition == 0) {
                        if (dy >= scaledTouchSlop) {
                            isIntercept = true;
                            lastY = y;
                            lastX = x;
                        }
                    }
                }
                if (bottomScrollVIewIsInTop) {
                    float dy = lastY - y;
                    if (dy < 0 && currPosition == 1) {
                        if (Math.abs(dy) >= scaledTouchSlop) {
                            isIntercept = true;
                            lastX = x;
                            lastY = y;
                        }
                    }
                }
//
//                    float dy = lastY - y;
//                    float dx = lastX - x;
//
//                    if (dy < 0 && currPosition == 1) {
//
//                        if (Math.abs(dy) >= scaledTouchSlop) {
//                            if (Math.abs(dy) > Math.abs(dx)) {//上下滑动
//                                isIntercept = true;
//                            } else {//左右滑动
//                                isIntercept = false;
//                            }
//                        }
//                    }


                break;
            case MotionEvent.ACTION_UP:

                break;
        }


        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int x = (int) event.getX();
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                //向上滑动 dy >  0  向下滑 dy<0;
                float dy = lastY - y;

                if (getScrollY() + dy < 0) {
                    dy = getScrollY() + dy + Math.abs(getScrollY() + dy);
                }

                if (getScrollY() + dy + getHeight() > mScrollViewbottom.getBottom()) {
                    dy = dy - (getScrollY() + dy - (mScrollViewbottom.getBottom() - getHeight()));
                }
                scrollBy(0, (int) dy);
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;

                velocityTracker.computeCurrentVelocity(1000);
                float yVelocity = velocityTracker.getYVelocity();

                if (currPosition == 0) {
                    if (yVelocity < 0 && yVelocity < -speed) {
                        smoothScroll(position1Y);
                        currPosition = 1;
                    } else {
                        smoothScroll(0);
                    }
                } else {
                    if (yVelocity > 0 && yVelocity > speed) {
                        smoothScroll(0);
                        currPosition = 0;
                    } else {
                        smoothScroll(position1Y);
                    }
                }
                break;
        }
        lastY = y;
        lastX = x;
        return true;
    }

    //通过Scroller实现弹性滑动
    private void smoothScroll(int tartY) {
        int dy = tartY - getScrollY();
        scroller.startScroll(getScrollX(), getScrollY(), 0, dy);
        invalidate();
    }


    //滚动到顶部
    public void scrollToTop() {
        smoothScroll(0);
        currPosition = 0;
        mMScrollViewTop.smoothScrollTo(0, 0);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
