#include <stdio.h>
#include "jni.h"

JNIEXPORT void JNICALL Java_main_Game_renderNative(JNIEnv *env, jobject obj) {
    // Implement the rendering logic here
    printf("Rendering from native code...\n");
}