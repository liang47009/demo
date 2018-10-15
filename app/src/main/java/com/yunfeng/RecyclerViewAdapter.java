package com.yunfeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yunfeng.demo.R;

import java.util.List;

/**
 * recycle
 * Created by xll on 2018/10/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Class<? extends Activity>> activitys;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.actitvitys_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Class<?> qulifi = activitys.get(position);
        final String className = qulifi.getName();
        String testName = className.split("\\.")[2];// get test case's name

        holder.tv_activity_name.setText(testName);
        holder.tv_clazz_name.setText(className);
        holder.btn_activity_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, qulifi);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activitys.size();
    }

    public void setData(List<Class<? extends Activity>> activitys) {
        this.activitys = activitys;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_activity_name;
        TextView tv_clazz_name;
        Button btn_activity_name;

        ViewHolder(View itemView) {
            super(itemView);
            tv_activity_name = itemView.findViewById(R.id.activity_name);
            tv_clazz_name = itemView.findViewById(R.id.activity_class);
            btn_activity_name = itemView.findViewById(R.id.start_activity);
        }
    }
}
