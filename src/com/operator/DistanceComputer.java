package com.operator;

/**
 * DistanceComputer
 */
public class DistanceComputer {
    static {
        System.loadLibrary("computer");
    }

    public native double getDistanceFromPoint(double x, double y, String wkt);
}