package com.taotao.tao_oat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @package com.taotao.tao_oat
 * @file RecycleViewAdapter
 * @date 2018/8/12  下午10:19
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> list;

    public RecycleViewAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);

        MyViewHold hold = new MyViewHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        public MyViewHold(View itemView) {
            super(itemView);
        }

        public void bindDate(String str) {

        }
    }
}
