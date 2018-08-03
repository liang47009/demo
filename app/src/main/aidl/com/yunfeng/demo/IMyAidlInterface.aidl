// IMyAidlInterface.aidl
package com.yunfeng.demo;

import com.yunfeng.demo.data.Data;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void send(in Data data);
}
