package com.operator;

import java.util.ArrayList;

import com.google.gson.Gson;


/**
 * [{"name": str, "wkt": str}, ...] && {"wkt": str}
 * -> [{"name": str, "lon": double, "lat": double, "dist": double}, ...]
 * 
 * @example
 * DistanceSelector ds = new DistanceSelector(0, 0, 1000, json);
 * String newJson = ds.select();
 */
public class DistanceSelector {

    double x, y, threshold;

    ArrayList<PointInfo> pointInfos;

    ArrayList<PointInfoWithDistance> pointInfoWithDistances = new ArrayList<PointInfoWithDistance>();

    DistanceComputer distanceComputer = new DistanceComputer();

    public DistanceSelector(double x, double y, double threshold, ArrayList<PointInfo> pointInfos) {
        this.x = x;
        this.y = y;
        this.threshold = threshold;
        this.pointInfos = pointInfos;
    }

    private void selectByDistance() {
        for (int i = 0; i < pointInfos.size(); i++) {

            double dist = distanceComputer.getDistanceFromPoint(x, y, pointInfos.get(i).wkt);

            if (dist <= threshold) {
                String[] lonlat = getLonLatFromPoint(pointInfos.get(i).wkt).split(" ");

                pointInfoWithDistances.add(
                    new PointInfoWithDistance(
                                pointInfos.get(i).name,
                                Double.parseDouble(lonlat[0]),
                                Double.parseDouble(lonlat[1]),
                                dist)
                );
            }
        }
    }

    private String getLonLatFromPoint(String wkt) {
        return wkt.substring(6).replace(")", "");
    }

    public String select() {
        selectByDistance();
        return new Gson().toJson(pointInfoWithDistances);
    }
}

class PointInfoWithDistance {
    String name;
    double lon, lat, dist;

    PointInfoWithDistance(String name, double lon, double lat, double dist) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.dist = dist;
    }
}