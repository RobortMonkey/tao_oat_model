package com.taotao.tao_oat_library;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * @package com.taotao.demo
 * @file AnimatorUtils
 * @date 2018/4/25  下午6:06
 * @autor wangxiongfeng
 */
public class AnimatorHelper {

    //
    public ValueAnimator setAnimatorValue(int... value) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        return valueAnimator;
    }

    /**
     * 对对象属性进行反射修改 需要有对象属性对set方法
     *
     *
     * translationX\translationY  水平和垂直方向偏移
     * rotation、rotationX\rotationY  rotation指3D翻转，rotationX\rotationY指水平和竖直方向的一个旋转动画
     * scaleX\scaleY  X轴方向，和Y轴方向缩放的一个动画
     * X\Y  具体会移动到某一个点
     * alpha  透明度动画
     */
    public void setValueObject(View view, String elementName) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, elementName, 0, 100);
        objectAnimator.setDuration(1000);


        //添加插值器
        objectAnimator.setInterpolator(new LinearInterpolator());
        //添加估值器
        //
        objectAnimator.setEvaluator(new IntEvaluator());

        objectAnimator.start();
    }

    /**
     * 估值器  Evaluator  intevaluator
     */

    class MyEvaluator implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            //  不断的new 新的点
            float resultX = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
            float resultY = startValue.getY() + fraction * (endValue.getY() - startValue.getX());
            return new Point(resultX, resultY);

        }
    }

    public class Point {
        private float x;
        private float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }


    /**
     *插值器
     */
    class MyInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {

            return 0;
        }
    }


}
