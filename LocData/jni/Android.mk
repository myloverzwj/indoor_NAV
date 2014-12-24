LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := loc
LOCAL_SRC_FILES := test.cpp parse.c
LOCAL_LDLIBS  += -llog

include $(BUILD_SHARED_LIBRARY)