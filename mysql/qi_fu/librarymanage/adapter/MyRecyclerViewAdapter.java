package com.mysql.qi_fu.librarymanage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysql.qi_fu.librarymanage.R;
import com.mysql.qi_fu.librarymanage.info.ChatInfo;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by qi_fu on 2017/2/10.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {
    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = MyRecyclerViewAdapter.class.getSimpleName();
    private List<ChatInfo> list;

    public MyRecyclerViewAdapter(List<ChatInfo> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
//        Logger.d(TAG, "onBindViewHolder, i: " + i + ", viewHolder: " + viewHolder);
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = i;
        ChatInfo chatInfo = list.get(i);
        holder.nameTv.setText(chatInfo.getTitle());
        holder.ageTv.setText(chatInfo.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public View rootView;
        public TextView nameTv;
        public TextView ageTv;
        public int position;

        public PersonViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.tv_big_size);
            ageTv = (TextView) itemView.findViewById(R.id.tv_smart_size);
            rootView = itemView.findViewById(R.id.ll_item_contain);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

}
