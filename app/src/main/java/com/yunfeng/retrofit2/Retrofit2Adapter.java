package com.yunfeng.retrofit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunfeng.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * a
 * Created by xll on 2018/9/27.
 */
public class Retrofit2Adapter extends BaseAdapter {
    private List<Contributor> list = new ArrayList(4);

    private Context mContext;

    public Retrofit2Adapter(Context context) {
        mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.retrofit2_list_item, null);
        }
        TextView login = convertView.findViewById(R.id.retrofit_login);
        TextView contribu = convertView.findViewById(R.id.retrofit_contributions);

        Contributor c = list.get(position);
        login.setText(c.login);
        contribu.setText(String.valueOf(c.contributions));

        return convertView;
    }

    public void addData(Contributor contributor) {
        list.add(contributor);
    }
}
