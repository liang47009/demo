package com.yunfeng.mvvm;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.PropertyChangeRegistry;

/**
 * obb
 * Created by xll on 2018/10/16.
 */
public class StudentViewModle extends ViewModel implements Observable {
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    private ObservableField<String> stu_name = new ObservableField<String>();

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }
}
