package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Activity;
import be.pxl.ja.opgave1.model.ActivityTracker;
import be.pxl.ja.opgave1.model.ActivityType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EndomodoMapper extends Mapper {

    private static final String DATE_PATTERN = "yyyyMMdd";

    @Override
    public Activity apply(final String[] csv) {
        LocalDate activityDate = LocalDate.parse(csv[0], DateTimeFormatter.ofPattern(DATE_PATTERN));
        String customerNumber = csv[1];
        ActivityType activityType = ActivityType.valueOf(csv[2].toUpperCase());
        double distance = parseDistance(activityType, csv[3]);

        return ActivityFactory.create(customerNumber, activityType, activityDate, distance, ActivityTracker.ENDOMODO);
    }

}
