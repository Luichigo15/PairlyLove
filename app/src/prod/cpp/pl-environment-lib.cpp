#include <jni.h>
#include <string>

#define DATABASE_CONFIG "{\"database_name\":\"PairlyLove.db\",\"is_encrypted\":\"true\",\"password\":\"d=)01I£zgzAgLwo8N^k?I.[\(\"}"

std::string getEnvironment(){
    return "DEV";
}

extern "C"
JNIEXPORT jstring JNICALL
Java_app_luichigo15_common_environment_L15Environment_getAppEnvironment(JNIEnv *env,jobject thiz){
    return env->NewStringUTF(getEnvironment().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_app_luichigo15_common_environment_L15Environment_getDatabaseConfig(JNIEnv *env,jobject thiz){
    std::string config = DATABASE_CONFIG;
    return env->NewStringUTF(config.c_str());
}
