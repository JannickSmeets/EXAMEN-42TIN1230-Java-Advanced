package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Activity;
import be.pxl.ja.opgave1.model.ActivityTracker;
import be.pxl.ja.opgave1.model.ActivityType;

import java.time.LocalDate;

public class ActivityFactory {

    public static Activity create(final String customerNumber, final ActivityType activityType,
                                  final LocalDate activityDate, final double distance,
                                  final ActivityTracker activityTracker) {

        final Activity activity = new Activity();

        activity.setCustomerNumber(customerNumber);
        activity.setActivityType(activityType);
        activity.setActivityDate(activityDate);
        activity.setDistance(distance);
        activity.setTracker(activityTracker);

        activity.setTracker(ActivityTracker.STRAVA);

        return activity;
    }

}
