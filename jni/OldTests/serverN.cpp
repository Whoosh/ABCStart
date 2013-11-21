#include "Token.h"
#include "QuerysManager.h"
#include <jni.h>

#define jParams JNIEnv *env, jclass thisz
Token token;

extern "C" {

    void Java_nativeLibs_Server_logOut(jParams) {
        //token.printToken();
    }

    jstring Java_nativeLibs_Server_getTest(jParams) {
        return env->NewStringUTF("nothing here");
    }

    bool Java_nativeLibs_Server_setToken(jParams, jstring name, jstring password) {
        //ReleaseStringUTFChars(env, jstring, const char);
        return  token.setToken(env->GetStringUTFChars(name,0), env->GetStringUTFChars(password,0));
    }

    void Java_nativeLibs_Server_addProxy(jParams, jstring address, jstring port) {

    }

};
