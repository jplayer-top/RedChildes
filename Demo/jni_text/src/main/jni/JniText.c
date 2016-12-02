#include "jni.h"

jstring  Java_com_oblivion_jni_1text_MainActivity_getStringFromC
  (JNIEnv * env, jobject obj){
  return (*env)->NewStringUTF(env, "Hello from JNI !");
  }
