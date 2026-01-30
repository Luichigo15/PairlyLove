#include <jni.h>
#include <string>

#define FIREBASE_CONFIG "{\"api_key\":\"AIzaSyB8dV_pCy9Tq7GBHb0yxii3CeFdKBhvgeY\",\"database_url\":\"https://estudio-4f1b0.firebaseio.com\",\"project_id\":\"estudio-4f1b0\",\"storage_bucket\":\"estudio-4f1b0.appspot.com\",\"sender_id\":\"900629978868\",\"app_id\":\"1:900629978868:android:aafa59837d49fa4afa08a1\"}"
#define WEB_CLIENT_ID "900629978868-ipi36lp5kleli16o5tt2da3p96pk3b5b.apps.googleusercontent.com"

extern "C"
JNIEXPORT jstring JNICALL
Java_app_luichigo15_pairly_environment_PLEnvironment_getFirebaseConfig(JNIEnv *env,jobject thiz) {
    std::string config = FIREBASE_CONFIG;
    return env->NewStringUTF(config.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_app_nezawarriors_learningmath_environment_LMEnvironment_getWebClientId(JNIEnv *env,jobject thiz) {
    std::string webClientId = WEB_CLIENT_ID;
    return env->NewStringUTF(webClientId.c_str());
}
