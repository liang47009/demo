//--------------------------------------------------------------------
// 文件名:		AndroidCrashDump.h
// 内  容:		
// 说  明:		
// 创建日期:	2013年2月28日
// 创建人:		
// 版权所有:	苏州蜗牛电子有限公司
//--------------------------------------------------------------------

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
DEFINE_PARAM(STR_crash_server, "http://172.19.35.13:8888/bgmgt/api/file/uploadFile",
			 "The crash server to upload minidumps to.");
static std::string STR_crash_tracelog_server = "http://172.19.35.13:8888/bgmgt/api/file/uploadLog";
#else
DEFINE_PARAM(STR_crash_server, "http://crashlog.mobilegame.woniu.com/crashlog/api/file/uploadFile",
			 "The crash server to upload minidumps to.");
static std::string STR_crash_tracelog_server = "http://crashlog.mobilegame.woniu.com/crashlog/api/file/uploadLog";

#endif

DEFINE_PARAM(STR_product_name, "taiji_android",
             "The product name that the minidump corresponds to.");

DEFINE_PARAM(STR_product_version, "1.0.0", "The version of the product that produced the minidump.");

DEFINE_PARAM(STR_client_id, "id", "The client GUID");

DEFINE_PARAM(STR_minidump_path, "minidump.dmp", "The path of the minidump file.");

DEFINE_PARAM(STR_ptime, "0", "The process uptime in milliseconds.");

DEFINE_PARAM(STR_ctime, "100000", "The cumulative process uptime in milliseconds.");

DEFINE_PARAM(STR_email, "type", "The user's email address.");

DEFINE_PARAM(STR_comments, "nothing", "Extra user comments");

DEFINE_PARAM(STR_proxy_host, "", "Proxy host");

DEFINE_PARAM(STR_proxy_userpasswd, "", "Proxy username/password in user:pass format.");

DEFINE_PARAM(STR_PackageName, "org.cocos2dx.hellocpp", "Application package name, for LibcurlWrapper dlopen libcurl.so.");



//
static bool dumpCallback(const google_breakpad::MinidumpDescriptor& descriptor, void* context, bool succeeded)
{
	LOGD(" =============== >>>>>> Dump path: %s\n", descriptor.path());
    
    if (succeeded) {
        LOGD(" ============== >>>> Dump success /n%s", (char*)context);
    }
    
    google_breakpad::LibcurlWrapper* http_layer = new google_breakpad::LibcurlWrapper(STR_PackageName.c_str());
    
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
                                               http_layer);

    succeeded = g.Upload();
    
    if (succeeded) {
        LOGD(" ============== >>>> Upload success ");
    }
    else{
        LOGD(" ============== >>>> Upload failed ");
    }
    
    return succeeded;
}

//
static bool initAndroidCrashDump(const char* szPath, const char* szVersion, const char* szCrashServerPath, const char* szPhoneType, 
								 const char* szUUID, const char* szDocName, const char* szFreeMemorySize)
{
	LOGD("=============== >>>>>> szPath %s", szPath);

	STR_product_version = szVersion;
	//STR_crash_server = szCrashServerPath;
	STR_email = szPhoneType;
	STR_client_id = szUUID;
	STR_product_name = "taiji_android_";
	STR_product_name.append(szDocName);
	STR_ptime = szFreeMemorySize;

	static google_breakpad::MinidumpDescriptor descriptor(szPath);
	static google_breakpad::ExceptionHandler exceptionhandler(descriptor, NULL, dumpCallback, NULL, true, -1);

	LOGD("=============== >>>>>> initAndroidCrashDump");
}

#endif //


