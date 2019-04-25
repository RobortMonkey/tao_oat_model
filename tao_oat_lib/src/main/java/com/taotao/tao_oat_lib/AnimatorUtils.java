package com.taotao.tao_oat_lib;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by space on 2018/6/9 0009.
 */

public class AnimatorUtils {

    public void animaotrObject() {
        Point startPoint = new Point(100, 100);// 初始点为圆心(70,70)
        Point endPoint = new Point(700, 1000);// 结束点为(700,1000)
        ValueAnimator animator = ValueAnimator.ofObject(new PointtEvaluator(), startPoint, endPoint);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        animator.start();
    }

    class Point {
        private float x;
        private float y;

        // 构造方法用于设置坐标
        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        // get方法用于获取坐标
        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

    }

    class PointtEvaluator implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;

            // 根据fraction来计算当前动画的x和y的值
            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
            float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

            // 将计算后的坐标封装到一个新的Point对象中并返回
            Point point = new Point(x, y);
            return point;

        }
    }

    public void animatorFloat(){
//        ValueAnimator.ofFloat()
    }

}
