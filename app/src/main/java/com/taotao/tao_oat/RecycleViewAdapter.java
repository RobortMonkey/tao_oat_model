package com.taotao.tao_oat;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotao.tao_oat.eventbus.EventBusActivity;
import com.taotao.tao_oat.fourcomponents.FourComponentsActivity;
import com.taotao.tao_oat.modle.ItemBean;
import com.taotao.tao_oat.viewpagedemo.ViewPageActivity;

import java.util.List;

/**
 * @package com.taotao.tao_oat
 * @file RecycleViewAdapter
 * @date 2018/8/12  下午10:19
 * @autor wangxiongfeng
 * @org www.orbyun.com
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemBean> list;
    private Activity activity;

    public RecycleViewAdapter(List<ItemBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);

        MyViewHold hold = new MyViewHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHold mholder = (MyViewHold) holder;
        mholder.bindDate(list.get(position));
        mholder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(view.getContext(), ViewPageActivity.class);
                        activity.startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(view.getContext(), FourComponentsActivity.class);
                        activity.startActivity(intent);

                        break;
                    case 2:
                        intent.setClass(view.getContext(), EventBusActivity.class);
                        activity.startActivity(intent);

                        break;
                    case 3:


                        break;
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() - 1;
    }

    class MyViewHold extends RecyclerView.ViewHolder {

        private final TextView mItemName;
        private View mView;

        public MyViewHold(View itemView) {
            super(itemView);
            mView = itemView;
            mItemName = (TextView) itemView.findViewById(R.id.item_name);
        }

        public View getView() {
            return mView;
        }

        public void setView(View view) {
            mView = view;
        }

        public void bindDate(ItemBean str) {
            mItemName.setText(str.getName());

        }
    }


}
