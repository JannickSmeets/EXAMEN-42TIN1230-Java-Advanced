package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Activity;
import be.pxl.ja.opgave1.model.ActivityType;

import java.util.function.Function;

public abstract class Mapper implements Function<String[], Activity> {

    protected static double parseDistance(final ActivityType activityType, final String value) {
        double distance = Double.parseDouble(value);

        if (activityType != ActivityType.SWIMMING) {
            distance *= 1000.0; // KM
        }

        return distance;
    }

}
