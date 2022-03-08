package com.billiard.operator;


public class DistanceComputer {
    static {
        System.loadLibrary("webgis_native_c");
    }

    public native double getDistanceFromPoint(double x, double y, String wkt);
}