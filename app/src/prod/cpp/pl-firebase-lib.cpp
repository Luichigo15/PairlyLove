#include <jni.h>
#include <string>

#define FIREBASE_CONFIG "{\"api_key\":\"AIzaSyAh1suAlk2UAUtFBIM8R4eygXME1SnPc2Q\",\"database_url\":\"https://test-ddfc5.firebaseio.com\",\"project_id\":\"test-ddfc5\",\"storage_bucket\":\"test-ddfc5.firebasestorage.app\",\"sender_id\":\"136273179169\",\"app_id\":\"1:136273179169:android:3a1ee8906e79b1dc16a960\"}"

extern "C"
JNIEXPORT jstring JNICALL
Java_app_luichigo15_pairly_environment_PLEnvironment_getFirebaseConfig(JNIEnv *env,jobject thiz) {
    std::string config = FIREBASE_CONFIG;
    return env->NewStringUTF(config.c_str());
}
