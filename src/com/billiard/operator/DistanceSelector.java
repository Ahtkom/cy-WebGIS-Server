package com.billiard.operator;

import java.util.ArrayList;

import com.billiard.entity.PointInfo;
import com.billiard.entity.PointInfoWithDistance;
import com.google.gson.Gson;

/**
 * [{"name": str, "wkt": str}, ...] && {"wkt": str}
 * -> [{"name": str, "lon": double, "lat": double, "dist": double}, ...]
 * 
 * @example
 *          DistanceSelector ds = new DistanceSelector(0, 0, 1000, json);
 *          String newJson = ds.select();
 */
public class DistanceSelector {

    double threshold;

    ArrayList<PointInfo> pointInfos;

    ArrayList<PointInfoWithDistance> pointInfoWithDistances = new ArrayList<PointInfoWithDistance>();

    DistanceComputer distanceComputer;

    public DistanceSelector(String wkt, double threshold, ArrayList<PointInfo> pointInfos) {
        this.distanceComputer = new DistanceComputer(wkt);
        this.threshold = threshold;
        this.pointInfos = pointInfos;
    }

    private void selectByDistance() {
        for (int i = 0; i < pointInfos.size(); i++) {
            double dist = distanceComputer.getDistanceFromPoint(pointInfos.get(i).getWkt());

            if (dist <= threshold) {
                String[] lonlat = getLonLatFromPoint(pointInfos.get(i).getWkt());

                pointInfoWithDistances.add(
                        new PointInfoWithDistance(
                                pointInfos.get(i).getName(),
                                Double.parseDouble(lonlat[0]),
                                Double.parseDouble(lonlat[1]),
                                dist));
            }
        }
    }

    private String[] getLonLatFromPoint(String wkt) {
        return wkt.substring(6).replace(")", "").split(" ");
    }

    public String select() {
        selectByDistance();
        return new Gson().toJson(pointInfoWithDistances);
    }
}