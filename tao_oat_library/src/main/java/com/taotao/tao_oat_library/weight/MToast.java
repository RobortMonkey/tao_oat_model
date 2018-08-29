package com.taotao.tao_oat_library.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taotao.tao_oat_library.R;

/**
 * @package com.taotao.tao_oat_library.utils
 * @file MToast
 * @date 2018/8/16  上午11:14
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class MToast {


    public static void showToast(Context context, String text) {
        Toast result = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.toast_item, null);
        TextView tv = (TextView) view.findViewById(R.id.message_txt);
        tv.setText(text);
        result.setView(view);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.show();
    }
}
