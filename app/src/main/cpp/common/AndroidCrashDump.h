#ifndef __ANDROIDCRASHDUMP_H__
#define __ANDROIDCRASHDUMP_H__

#include <android/log.h>
#include <string>
#include "client/linux/handler/exception_handler.h"
#include "common/linux/google_crashdump_uploader.h"
#include "common/using_std_string.h"

#define  LOG_TAG    "AndroidCrashDump"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

#define  DEFINE_PARAM(_NAME, _VALUE, _DIS) \
static std::string _NAME(_VALUE)

#ifdef PLATFORM_DEBUG
DEFINE_PARAM(STR_crash_server, "http://www.baidu.com", "The crash server to upload minidumps to.");
static std::string STR_crash_tracelog_server = "http://www.baidu.com";
#else
DEFINE_PARAM(STR_crash_server, "http://www.baidu.com", "The crash server to upload minidumps to.");
static std::string STR_crash_tracelog_server = "www.baidu.com";

#endif

DEFINE_PARAM(STR_product_name, "demo",
             "The product name that the minidump corresponds to.");

DEFINE_PARAM(STR_product_version, "1.0.0",
             "The version of the product that produced the minidump.");

DEFINE_PARAM(STR_client_id, "id", "The client GUID");

DEFINE_PARAM(STR_minidump_path, "minidump.dmp", "The path of the minidump file.");

DEFINE_PARAM(STR_ptime, "0", "The process uptime in milliseconds.");

DEFINE_PARAM(STR_ctime, "100000", "The cumulative process uptime in milliseconds.");

DEFINE_PARAM(STR_email, "type", "The user's email address.");

DEFINE_PARAM(STR_comments, "nothing", "Extra user comments");

DEFINE_PARAM(STR_proxy_host, "", "Proxy host");

DEFINE_PARAM(STR_proxy_userpasswd, "", "Proxy username/password in user:pass format.");

DEFINE_PARAM(STR_PackageName, "org.cocos2dx.hellocpp",
             "Application package name, for LibcurlWrapper dlopen libcurl.so.");
//
static bool
dumpCallback(const google_breakpad::MinidumpDescriptor &descriptor, void *context, bool succeeded) {
    LOGD(" =============== >>>>>> Dump path: %s\n", descriptor.path());

    if (succeeded) {
        LOGD(" ============== >>>> Dump success /n%s", (char *) context);
    }

//    google_breakpad::LibcurlWrapper *http_layer = new google_breakpad::LibcurlWrapper();
    google_breakpad::GoogleCrashdumpUploader g(STR_product_name,
                                               STR_product_version,
                                               STR_client_id,
                                               STR_ptime,
                                               STR_ctime,
                                               STR_email,
                                               STR_comments,
                                               descriptor.path(),
                                               STR_crash_server,
                                               STR_proxy_host,
                                               STR_proxy_userpasswd,
                                               NULL);
    int http_status_code;
    std::string http_response_header;
    std::string http_response_bod;
    succeeded = g.Upload(&http_status_code, &http_response_header, &http_response_bod);
    if (succeeded) {
        LOGD(" ============== >>>> Upload success ");
    } else {
        LOGD(" ============== >>>> Upload failed ");
    }
    return succeeded;
}

//
static bool
initAndroidCrashDump(const char *szPath, const char *szVersion, const char *szCrashServerPath,
                     const char *szPhoneType,
                     const char *szUUID, const char *szDocName, const char *szFreeMemorySize) {
    LOGD("=============== >>>>>> szPath %s", szPath);
    STR_product_version = szVersion;
    //STR_crash_server = szCrashServerPath;
    STR_email = szPhoneType;
    STR_client_id = szUUID;
    STR_product_name = "demo";
    STR_product_name.append(szDocName);
    STR_ptime = szFreeMemorySize;

    static google_breakpad::MinidumpDescriptor descriptor(szPath);
    static google_breakpad::ExceptionHandler exceptionhandler(descriptor, NULL, dumpCallback, NULL,
                                                              true, -1);
    LOGD("=============== >>>>>> initAndroidCrashDump");
}

#endif //


