#include <jni.h>
#include <string>

#define DATABASE_CONFIG "{\"database_name\":\"LearningMath.db\",\"is_encrypted\":\"false\"}"
#define BASE_URL "https://courtesy-throat-power-listening.trycloudflare.com"

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

extern "C"
JNIEXPORT jstring JNICALL
Java_app_nezawarriors_learningmath_environment_LMEnvironment_getBaseUrl(JNIEnv *env,jobject thiz){
    std::string baseUrl = BASE_URL;
    return env->NewStringUTF(baseUrl.c_str());
}
