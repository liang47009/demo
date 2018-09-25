package com.yunfeng.mvvm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunfeng.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * a
 * Created by xll on 2018/9/25.
 */
public class MvvmAdapter extends BaseAdapter {

    private List list = new ArrayList(8);
    private LayoutInflater mInflater;

    public MvvmAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        list.add(new Student(1, "sadf"));
        list.add(new Student(2, "sadf"));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.mvvm_adapter, null);
        }
        TextView tv_name = (TextView) convertView.findViewById(R.id.mvvm_tv);
        ImageView iv_image = (ImageView) convertView.findViewById(R.id.mvvm_iv);
        Object obj = list.get(position);
        tv_name.setText(obj.getClass().getName());
        iv_image.setBackgroundResource(R.mipmap.dianzan_press);
        return convertView;
    }

    /**
     * @param stu
     * @param position
     */
    public void click(Student stu, int position) {
        Log.d("app", stu + "-----" + position);
    }
}
