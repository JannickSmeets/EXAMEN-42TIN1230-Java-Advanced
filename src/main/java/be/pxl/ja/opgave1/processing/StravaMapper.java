package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Activity;
import be.pxl.ja.opgave1.model.ActivityTracker;
import be.pxl.ja.opgave1.model.ActivityType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StravaMapper extends Mapper {

    private final String DATE_PATTERN = "dd/MM/yyyy";

    @Override
    public Activity apply(final String[] csv) {
        String[] personTokens = csv[0].split("\\s+");
        String customerNumber = personTokens[personTokens.length - 1];
        LocalDate activityDate = LocalDate.parse(csv[1], DateTimeFormatter.ofPattern(DATE_PATTERN));
        ActivityType activityType = ActivityType.valueOf(csv[2].toUpperCase());
        double distance = parseDistance(activityType, csv[3]);

        return ActivityFactory.create(customerNumber, activityType, activityDate, distance, ActivityTracker.STRAVA);
    }

}
