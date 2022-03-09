#include <com_billiard_operator_DistanceComputer.h>

#include <geos/geom/Geometry.h>
#include <geos/geom/GeometryFactory.h>
#include <geos/io/WKTReader.h>

#include <string>

using namespace geos;

JNIEXPORT jdouble JNICALL Java_com_billiard_operator_DistanceComputer_getDistanceFromPoint
  (JNIEnv *env, jobject distance_computer, jstring jwkt)
{
    static jclass distance_computer_class = env->FindClass("com/billiard/operator/DistanceComputer");
    static jfieldID x_fid = env->GetFieldID(distance_computer_class, "x", "D");
    static jfieldID y_fid = env->GetFieldID(distance_computer_class, "y", "D");
    static auto gf = geom::GeometryFactory::create();
    static io::WKTReader reader(*gf);

    double x = env->GetDoubleField(distance_computer, x_fid);
    double y = env->GetDoubleField(distance_computer, y_fid);
    
    geom::Geometry::Ptr origin_point = reader.read(
        "POINT(" + std::to_string(x) + " " + std::to_string(y) + ")");

    const char *wkt = env->GetStringUTFChars(jwkt, 0);

    geom::Geometry::Ptr target_point = reader.read(wkt);

    double distance = origin_point->distance(target_point.get());

    env->ReleaseStringUTFChars(jwkt, wkt);

    return distance;
}