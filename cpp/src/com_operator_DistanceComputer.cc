#include <jni.h>

#include <geos/geom/Geometry.h>
#include <geos/geom/GeometryFactory.h>
#include <geos/io/WKTReader.h>
#include <com_operator_DistanceComputer.h>

#include <string>

using namespace geos;

JNIEXPORT jdouble JNICALL
Java_com_operator_DistanceComputer_getDistanceFromPoint(JNIEnv *env, jobject obj, jdouble x, jdouble y, jstring jwkt)
{
    const char *wkt = env->GetStringUTFChars(jwkt, 0);

    auto gf = geom::GeometryFactory::create();

    io::WKTReader reader(*gf);

    geom::Geometry::Ptr originPoint = reader.read(
        "POINT(" + std::to_string(x) + " " + std::to_string(y) + ")");

    geom::Geometry::Ptr targetPoint = reader.read(wkt);

    double dist = originPoint->distance(targetPoint.get());

    env->ReleaseStringUTFChars(jwkt, wkt);

    return dist;
}