#include <com_billiard_operator_DistanceComputer.h>

#include <geos/geom/Geometry.h>
#include <geos/geom/GeometryFactory.h>
#include <geos/io/WKTReader.h>

#include <string>

using namespace geos;

jclass distance_computer_class;
// jfieldID x_fid;
// jfieldID y_fid;
// double x;
// double y;
auto gf = geom::GeometryFactory::create();
io::WKTReader reader(*gf);
geom::Geometry::Ptr origin_point;

JNIEXPORT void JNICALL
Java_com_billiard_operator_DistanceComputer_static_1init
  (JNIEnv *env, jclass clazz)
{
    distance_computer_class = clazz;
}

JNIEXPORT void JNICALL
Java_com_billiard_operator_DistanceComputer_init
  (JNIEnv *env, jobject distance_computer, jstring jwkt)
{
    const char *wkt = env->GetStringUTFChars(jwkt, 0);

    origin_point = reader.read(wkt);
    
    env->ReleaseStringUTFChars(jwkt, wkt);
}

JNIEXPORT jdouble JNICALL
Java_com_billiard_operator_DistanceComputer_getDistanceFromPoint
  (JNIEnv *env, jobject distance_computer, jstring jwkt)
{
    const char *wkt = env->GetStringUTFChars(jwkt, 0);

    geom::Geometry::Ptr target_point = reader.read(wkt);

    double distance = origin_point->distance(target_point.get());

    env->ReleaseStringUTFChars(jwkt, wkt);

    return distance;
}