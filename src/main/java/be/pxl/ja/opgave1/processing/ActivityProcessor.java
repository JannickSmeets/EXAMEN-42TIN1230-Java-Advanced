package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Activity;
import be.pxl.ja.opgave1.model.ActivityTracker;
import be.pxl.ja.opgave1.model.Customer;
import be.pxl.ja.opgave1.repository.CustomerRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class ActivityProcessor {

    private static final Map<ActivityTracker, Mapper> MAPPERS = new HashMap<>();

    static {
        MAPPERS.put(ActivityTracker.STRAVA, new StravaMapper());
        MAPPERS.put(ActivityTracker.ENDOMODO, new EndomodoMapper());
    }

    private final CustomerRepository customerRepository;

    public ActivityProcessor(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Activity> processActivities(final Path activityFile, final Path errorFile) {
        final ActivityTracker tracker = getFileType(activityFile.getFileName());
        final Function<String[], Activity> mapper = MAPPERS.get(tracker);

        if (tracker == null || mapper == null) {
            logError(errorFile, activityFile.getFileName(), "INVALID FILENAME");
            return Collections.emptyList();
        }

        final List<Activity> activities = new ArrayList<>();
        final List<String> lines = readAllLines(activityFile);

        for (final String line : lines) {
            final String[] csvTokens = line.split(";");

            try {
                final Activity activity = mapper.apply(csvTokens);

                final Customer customer = customerRepository.getByCustomerNumber(activity.getCustomerNumber());
                if (customer == null) {
                    final String errorMessage = String.format("UNKOWN CUSTOMER: %s", line);
                    logError(errorFile, activityFile, errorMessage);
                    continue;
                }

                if (processActivity(activity, customer)) {
                    activities.add(activity);
                }
            } catch (Exception e) {
                logError(errorFile, activityFile, e.getMessage());
            }

        }

        return activities;
    }

    private boolean processActivity(final Activity activity, final Customer customer) {
        // Assign points to the customer.
        final int pointsForActivity = calculatePointsForActivity(activity);

        customer.addPoints(pointsForActivity);
        activity.setAssignedPoints(pointsForActivity);

        return true;
    }

    public static int calculatePointsForActivity(final Activity activity) {
        final double distanceInKm = activity.getDistance() / 1000.0;
        final int points = (int) Math.floor(distanceInKm) * activity.getActivityType().getPointsPerKm();

        return points;
    }

    private static List<String> readAllLines(final Path activityFile) {
        try {
            return Files.readAllLines(activityFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private static void logError(final Path errorFile, final Path fileName, final String errorMessage) {
        final LocalDateTime now = LocalDateTime.now();
        final String errorLog = String.format("%s - %s - %s", now, fileName, errorMessage);

        // Print error for debugging purposes.
        // System.err.println(errorLog);

        ensurePathExists(errorFile);

        try (FileWriter fileWriter = new FileWriter(errorFile.toString(), true)) {
            fileWriter.append(errorLog);
            fileWriter.append(System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Cannot write to error file: " + errorFile);
            e.printStackTrace();
        }

    }

    private static void ensurePathExists(final Path filePath) {
        try {
            final Path directory = filePath.getParent();
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ActivityTracker getFileType(final Path fileNamePath) {
        final String fileName = fileNamePath.toString().toUpperCase();

        for (final ActivityTracker value : ActivityTracker.values()) {
            if (fileName.contains(value.name())) {
                return value;
            }
        }

        return null;
    }

}
