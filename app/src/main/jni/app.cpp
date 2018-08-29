#include <jni.h>
#include <unistd.h>
#include <android/log.h>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)

extern "C"
{

JNIEXPORT void JNICALL Java_com_yunfeng_nativefork_MainActivity_fork(JNIEnv *env, jclass type) {
    pid_t fpid; //fpid表示fork函数返回的值
    int count = 0;
    fpid = fork();
    if (fpid < 0) {
        LOGI("error in fork!");
    } else if (fpid == 0) {
        LOGI("i am the child process, my process id is %d, fpid: %d", getpid(), fpid);
        count++;
    } else {
        LOGI("i am the parent process, my process id is %d, fpid: %d", getpid(), fpid);
        count++;
    }
    LOGI("统计结果是: %d", count);
}

}