#include <jni.h>
#include <string>

#define DATABASE_CONFIG "{\"database_name\":\"PairlyLove.db\",\"is_encrypted\":\"true\",\"password\":\"d=)01I£zgzAgLwo8N^k?I.[\(\"}"
#define PUZZLE_URL "https://api.cloudinary.com/v1_1/da9rkovep/"
#define PUZZLE_PRESET "test-android"

std::string getEnvironment(){
    return "PROD";
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
Java_app_luichigo15_pairly_environment_PLEnvironment_getPuzzleUrl(JNIEnv *env,jobject thiz){
    std::string baseUrl = PUZZLE_URL;
    return env->NewStringUTF(baseUrl.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_app_luichigo15_pairly_environment_PLEnvironment_getPuzzlePreset(JNIEnv *env,jobject thiz){
    std::string baseUrl = PUZZLE_PRESET;
    return env->NewStringUTF(baseUrl.c_str());
}
