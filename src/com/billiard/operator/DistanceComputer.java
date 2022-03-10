package com.billiard.operator;

public class DistanceComputer {
    static {
        System.loadLibrary("webgis_native_c");

        static_init();
    }

    public DistanceComputer(String wkt) {
        init(wkt);
    }
    
    private static native void static_init(); 
    
    private native void init(String wkt);

    public native double getDistanceFromPoint(String wkt);

    // ! To be implemented
    public native double[] getDistanceFromPoints(String wkt);
}