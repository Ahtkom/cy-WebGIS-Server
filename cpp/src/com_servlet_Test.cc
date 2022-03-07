#include <com_servlet_Test.h>
#include <string>

JNIEXPORT jstring JNICALL
Java_com_servlet_Test_getString(JNIEnv *env, jobject obj, jstring str)
{
    auto c = env->GetStringUTFChars(str, 0);

    std::string s = c;

    env->ReleaseStringUTFChars(str, c);

    jstring jstr = env->NewStringUTF(("c++" + s).c_str());

    return jstr;
}