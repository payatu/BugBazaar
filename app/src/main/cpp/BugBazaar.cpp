#include <jni.h>
#include <string.h>
#include <malloc.h>

// Simple XOR obfuscation key
#define KEY 0x55

// Function to perform XOR obfuscation
void obfuscate(char *str) {
    char *ptr = str;
    while (*ptr != '\0') {
        *ptr ^= KEY;
        ptr++;
    }
}

// Function to perform XOR deobfuscation
void deobfuscate(char *str) {
    char *ptr = str;
    while (*ptr != '\0') {
        *ptr ^= KEY;
        ptr++;
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_BugBazaar_Models_CredentialsLoader_getUsername(JNIEnv *env, jobject /* this */) {
    char username[] = "admin";
    obfuscate(username);
    return env->NewStringUTF(username);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_BugBazaar_Models_CredentialsLoader_getPassword(JNIEnv *env, jobject /* this */) {
    char password[] = "BugBazaarSec";
    obfuscate(password);
    return env->NewStringUTF(password);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_BugBazaar_Models_CredentialsLoader_deobfuscateString(JNIEnv *env, jobject /* this */, jstring jstr) {
    const char *str = env->GetStringUTFChars(jstr, nullptr);
    char *deobfuscated = strdup(str);
    deobfuscate(deobfuscated);
    jstring result = env->NewStringUTF(deobfuscated);
    free(deobfuscated);
    return result;
}
