LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE:= gui
LOCAL_SRC_FILES:= src/main/cpp/com_yunfeng_gui_JNILib.cpp\
                  src/main/cpp/renderer/vecmath.cpp\
                  src/main/cpp/renderer/Renderer.cpp\
                  src/main/cpp/renderer/utils.cpp\
                  src/main/cpp/renderer/Square.cpp\
                  src/main/cpp/renderer/Triangle.cpp
LOCAL_C_INCLUDES := src/main/cpp src/main/cpp/include
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)
LOCAL_EXPORT_LDLIBS    := -llog -landroid -lEGL -lGLESv2

LOCAL_STATIC_LIBRARIES := cpufeatures android_native_app_glue ndk_helper freeimage

include $(BUILD_SHARED_LIBRARY)
$(call import-add-path,$(LOCAL_PATH))
$(call import-module, freeimage)
$(call import-module,android/native_app_glue)
$(call import-module,android/ndk_helper)
$(call import-module,android/cpufeatures)
