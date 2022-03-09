package com.billiard.entity;

public class PointInfo {
    private String name;
    private String wkt;

    public PointInfo(String name, String wkt) {
        this.setName(name);
        this.setWkt(wkt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }
}
