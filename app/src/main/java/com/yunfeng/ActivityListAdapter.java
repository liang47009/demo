package com.yunfeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yunfeng.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ss
 * Created by xll on 2018/9/30.
 */
public class ActivityListAdapter extends BaseAdapter {
    private List<Class<? extends Activity>> activitys = new ArrayList<>();

    private final Context mContext;

    public ActivityListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return activitys.size();
    }

    @Override
    public Object getItem(int position) {
        return activitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.actitvitys_adapter, null);

            final Class activity = activitys.get(position);
            final String className = activity.getName();

            TextView name = convertView.findViewById(R.id.activity_name);
            name.setText(className);
            TextView clazz = convertView.findViewById(R.id.activity_class);
            clazz.setText(className);
            Button button = convertView.findViewById(R.id.start_activity);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, activity);
                    mContext.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    public void addActivity(Class<? extends Activity> activity) {
        activitys.add(activity);
    }
}
