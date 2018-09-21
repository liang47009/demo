LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

PROJECT_FILES := $(wildcard $(LOCAL_PATH)/src/main/cpp/*.cpp)
PROJECT_FILES += $(wildcard $(LOCAL_PATH)/src/main/cpp/renderer/*.cpp)

LOCAL_MODULE:= gui

LOCAL_SRC_FILES := $(PROJECT_FILES)

LOCAL_C_INCLUDES := src/main/cpp src/main/cpp/external

LOCAL_CFLAGS := -DVK_USE_PLATFORM_ANDROID_KHR

LOCAL_CPPFLAGS := -std=c++11
LOCAL_CPPFLAGS += -D__STDC_LIMIT_MACROS
LOCAL_CPPFLAGS += -DVK_NO_PROTOTYPES
LOCAL_CPPFLAGS += -DVK_USE_PLATFORM_ANDROID_KHR

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_EXPORT_LDLIBS    := -llog -landroid -lEGL -lGLESv2

LOCAL_STATIC_LIBRARIES := cpufeatures android_native_app_glue ndk_helper freeimage

include $(BUILD_SHARED_LIBRARY)

$(call import-add-path,$(LOCAL_PATH))
$(call import-module, freeimage)
$(call import-module,android/native_app_glue)
$(call import-module,android/ndk_helper)
$(call import-module,android/cpufeatures)
