package com.billiard.operator;

public class DistanceComputer {
    static {
        System.loadLibrary("webgis_native_c");
    }

    private double x;
    private double y;

    public DistanceComputer(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public native double getDistanceFromPoint(String wkt);

    public native double[] getDistanceFromPoints(String wkt);
}