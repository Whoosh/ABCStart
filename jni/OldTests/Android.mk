LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_CPP_EXTENSION := .cpp
LOCAL_MODULE    := libServer
LOCAL_SRC_FILES := QuerysManager.cpp Token.cpp serverN.cpp 
LOCAL_LDLIBS :=  -llog

include $(BUILD_SHARED_LIBRARY)
