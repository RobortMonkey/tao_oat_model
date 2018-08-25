package com.taotao.tao_oat_library.design_pattern;

import android.content.Context;

/**
 * @package com.taotao.tao_oat_library.design_pattern
 * @file build_modle
 * @date 2018/8/25  上午10:57
 * @autor wangxiongfeng
 */
public class BuildModle {


    private static BuildModle singleton;


    public static BuildModle with(Context context) {
        if (singleton == null) {
            synchronized (BuildModle.class) {
                if (singleton == null) {
                    singleton = new Build(context).build();
                }
            }
        }
        return singleton;
    }

    private int initValue;
    private int strValue;

    public BuildModle(int initValue, int strValue) {
        this.initValue = initValue;
        this.strValue = strValue;
    }


    //在build中配置 参数。

    public static class Build {
        private static int initValue;
        private static int strValue;

        public Build(Context context) {


        }


        private BuildModle build() {
            initValue = 2;
            strValue = 3;
            return new BuildModle(initValue, strValue);

        }

    }

}
