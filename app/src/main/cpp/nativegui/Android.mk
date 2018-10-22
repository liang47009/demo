LOCAL_PATH:= $(call my-dir)
cmd-strip = $(TOOLCHAIN_PREFIX)strip --strip-all -x $1

include $(CLEAR_VARS)
LOCAL_MODULE    := curl
LOCAL_SRC_FILES := ../../../../jniLibs/$(TARGET_ARCH_ABI)/libcurl.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := crypto
LOCAL_SRC_FILES := ../../../../jniLibs/$(TARGET_ARCH_ABI)/libcrypto.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := ssl
LOCAL_SRC_FILES := ../../../../jniLibs/$(TARGET_ARCH_ABI)/libssl.a
include $(PREBUILT_STATIC_LIBRARY)

#==============guinative=============
include $(CLEAR_VARS)
FM_MOVIE_M_PATH = ../../../../fm_movie_m/
LOCAL_C_INCLUDES := \
$(LOCAL_PATH)/../../../../fm_movie_m/\
$(LOCAL_PATH)/../../../../fm_movie_m/../extern/ogles2/include

LOCAL_MODULE    := libguinative
LOCAL_SRC_FILES :=  com_yunfeng_gui_JNILib.cpp
					renderer/vecmath.cpp
					renderer/GLESRenderer.cpp
					renderer/utils.cpp
					renderer/Square.cpp
					renderer/MatrixStat.cpp
					renderer/Triangle.cpp

LOCAL_LDLIBS := \
-landroid \
-lGLESv2 \
-lEGL \
-llog \
-lz

LOCAL_SHARED_LIBRARIES := 

include $(BUILD_SHARED_LIBRARY)
