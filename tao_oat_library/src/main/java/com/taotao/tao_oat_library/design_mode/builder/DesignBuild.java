package com.taotao.tao_oat_library.design_mode.builder;

import android.content.Context;
import android.text.TextUtils;

/**
 * @package com.taotao.tao_oat_library.design_mode
 * @file DesignBuild
 * @date 2019/3/27  4:07 PM
 * @autor wangxiongfeng
 *
 *
 * Build 模式
 */
public class DesignBuild {

    private Context mContext;
    private String name;

    public DesignBuild(Context context, String name) {
        mContext = context;
        this.name = name;
    }

    public DesignBuild(Context context) {
        mContext = context;
    }


    public static class Builder {
        private Context mContext;
        private String name;
        private String defaultName = "zhang san";

        public void setName(String name) {
            if (TextUtils.isEmpty(name)) {
                name = defaultName;
            }
            this.name = name;
        }

        public DesignBuild builder(Context context) {
            return new DesignBuild(context, name);
        }

    }
}
