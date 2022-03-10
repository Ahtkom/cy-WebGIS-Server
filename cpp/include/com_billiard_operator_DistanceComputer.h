/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_billiard_operator_DistanceComputer */

#ifndef _Included_com_billiard_operator_DistanceComputer
#define _Included_com_billiard_operator_DistanceComputer
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_billiard_operator_DistanceComputer
 * Method:    static_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_billiard_operator_DistanceComputer_static_1init
  (JNIEnv *, jclass);

/*
 * Class:     com_billiard_operator_DistanceComputer
 * Method:    init
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_billiard_operator_DistanceComputer_init
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_billiard_operator_DistanceComputer
 * Method:    getDistanceFromPoint
 * Signature: (Ljava/lang/String;)D
 */
JNIEXPORT jdouble JNICALL Java_com_billiard_operator_DistanceComputer_getDistanceFromPoint
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_billiard_operator_DistanceComputer
 * Method:    getDistanceFromPoints
 * Signature: (Ljava/lang/String;)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_billiard_operator_DistanceComputer_getDistanceFromPoints
  (JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif
