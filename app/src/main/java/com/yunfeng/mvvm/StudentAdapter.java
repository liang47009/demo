package com.yunfeng.mvvm;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunfeng.Const;
import com.yunfeng.demo.R;
import com.yunfeng.demo.databinding.MvvmAdapterBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ua
 * Created by rufi on 6/5/15.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private static final int USER_COUNT = 8;
    private Random random = new Random();

    @NonNull
    private List<Student> mUsers = new ArrayList<>(8);

    public StudentAdapter() {
        for (int i = 0; i < USER_COUNT; i++) {
            Student user = new Student(i, random.nextInt() + "_student");
            mUsers.add(user);
        }
    }

    public void addData(Student user) {
        mUsers.add(user);
    }

    public void changeValue() {
        for (Student stu : mUsers) {
            String userName = "student---" + random.nextInt();
            stu.setName(userName);
            Log.d(Const.TAG, userName);
        }
    }

    public static class StudentHolder extends RecyclerView.ViewHolder {
        private MvvmAdapterBinding mBinding;

        public StudentHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull Student user) {
            mBinding.setStu(user);
        }
    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mvvm_adapter, viewGroup, false);
        return new StudentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {
        holder.bind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void click(Student stu, int position) {
        Log.d(Const.TAG, "stu click");
    }
}