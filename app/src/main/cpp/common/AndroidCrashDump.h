#ifndef __ANDROIDCRASHDUMP_H__
#define __ANDROIDCRASHDUMP_H__

#include <android/log.h>
#include <string>
#include <map>
#include "client/linux/handler/exception_handler.h"
#include "common/linux/curl_helper.h"
#include "common/using_std_string.h"

#define  LOG_TAG    "AndroidCrashDump"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

using namespace google_breakpad;

extern "C" {
std::map<string, string> parameters_;
CurlHelper curlHelper;

bool initAndroidCrashDump(const char *szPath);
void addParame(const char *key, const char *value);
bool dumpCallback(const MinidumpDescriptor &descriptor, void *context, bool succeeded);
}

const char *const PRODUCT = "prod";
const char *const VERSION = "ver";
const char *const GUIDSTR = "guid";
const char *const PTIME = "ptime";
const char *const CTIME = "ctime";
const char *const EMAIL = "email";
const char *const COMMENTS = "comments";
const char *const CRASHSERVER = "crash_server";
const char *const PROXYHOST = "proxy_host";
const char *const PROXYPWD = "proxy_pwd";

//
void addParame(const char *key, const char *value) {
    parameters_[key] = value;
}

//
bool initAndroidCrashDump(const char *szPath) {
    LOGI("=============== >>>>>> szPath %s", szPath);
    MinidumpDescriptor descriptor(szPath);
    ExceptionHandler exceptionhandler(descriptor, NULL, dumpCallback, NULL, true, -1);
    LOGI("=============== >>>>>> initAndroidCrashDump");
    parameters_[PRODUCT] = "demo";
    parameters_[VERSION] = "1.0.0";
    parameters_[GUIDSTR] = "guid";
    parameters_[PTIME] = "1927432789";
    parameters_[CTIME] = "1290489057";
    parameters_[EMAIL] = "xiayunfeng2012@gmail.com";
    parameters_[COMMENTS] = "custom";
    parameters_[CRASHSERVER] = "http://www.baidu.com";
    parameters_[PROXYHOST] = "proxy_host";
    parameters_[PROXYPWD] = "proxy_pwd";
//    http_layer_.reset(new LibcurlWrapper());
//    http_layer_->Init();
    curlHelper.Init();
    return true;
}

//
bool dumpCallback(const MinidumpDescriptor &descriptor, void *context, bool succeeded) {
    LOGI(" =============== >>>>>> Dump path: %s\n", descriptor.path());
    if (succeeded) {
        LOGI(" ============== >>>> Dump success %p \n", context);
    }
    string crash_server_ = parameters_[CRASHSERVER];
    LOGI(" ============== >>>> Sending request to  %s \n", crash_server_.c_str());
    int http_status_code = -1;
    string resp_head = "";
    string resp_body = "";

    curlHelper.AddFile(descriptor.path(), "upload_file_minidump");

    bool success = curlHelper.SendRequest(crash_server_,
                                            parameters_,
                                            &http_status_code,
                                            &resp_head,
                                            &resp_body);
    LOGI(" ============== >>>> upload ret: %s , %s\n", resp_head.c_str(), resp_body.c_str());
    return success;
}

#endif //


