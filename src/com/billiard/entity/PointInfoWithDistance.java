package com.billiard.entity;

public class PointInfoWithDistance {
    String name;
    double lon, lat, dist;

    public PointInfoWithDistance(String name, double lon, double lat, double dist) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.dist = dist;
    }
}
