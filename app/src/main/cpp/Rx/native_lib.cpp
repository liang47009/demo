#include <jni.h>
#include <string>
#include <iostream>

#include <android/log.h>

#ifndef LOGI
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "YUNFENG", __VA_ARGS__)
#endif

#include "rxcpp/rx.hpp"
// create alias' to simplify code
// these are owned by the user so that
// conflicts can be managed by the user.
namespace rx=rxcpp;
namespace rxu=rxcpp::util;

extern "C" {

JNIEXPORT void JNICALL
Java_com_yunfeng_rxcpp_MainActivity_nativeRxCpp(JNIEnv *env, jclass, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    freopen(path, "w", stdout);
    env->ReleaseStringUTFChars(path_, path);

    std::cout << "===== start =====" << std::endl;

    auto get_names = []() {
        return rx::observable<>::from<std::string>(
                "Matthew",
                "Aaron"
        );
    };

    std::cout << "===== println stream of std::string =====" << std::endl;
    auto hello_str = [&]() {
        return get_names().map([](std::string n) {
            return "Hello, " + n + "!";
        }).as_dynamic();
    };

    hello_str().subscribe(rxu::println(std::cout));

    std::cout << "===== println stream of std::tuple =====" << std::endl;
    auto hello_tpl = [&]() {
        return get_names().map([](std::string n) {
            return std::make_tuple("Hello, ", n, "! (", n.size(), ")");
        }).as_dynamic();
    };

    hello_tpl().subscribe(rxu::println(std::cout));

    hello_tpl().subscribe(rxu::print_followed_by(std::cout, " and "), rxu::endline(std::cout));
}

}