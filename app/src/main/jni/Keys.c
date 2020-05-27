#include <jni.h>
 
JNIEXPORT jstring JNICALL
Java_com_magulab_test_network_RestAPI_getKey(JNIEnv *env, jobject thiz) {
    return (*env)->  NewStringUTF(env, "2e9c6acf6f50d538e95d5a17284e9e0a");
}

