
#include "Clazz.h"

#include <android/log.h>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)

Clazz::Clazz() {

}

Clazz::~Clazz() {

}

void Clazz::test() {
    LOGI("Clazz::test()");
}
