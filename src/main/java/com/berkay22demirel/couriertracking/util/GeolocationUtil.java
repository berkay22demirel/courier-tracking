package com.berkay22demirel.couriertracking.util;

public class GeolocationUtil {

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    public static int calculateDistance(double startLat, double startLng, double endLat, double endLng) {

        double latDistance = Math.toRadians(startLat - endLat);
        double lngDistance = Math.toRadians(startLng - endLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(startLat))) *
                        (Math.cos(Math.toRadians(endLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));
    }
}
