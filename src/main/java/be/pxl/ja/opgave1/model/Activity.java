package be.pxl.ja.opgave1.model;

import java.time.LocalDate;

public class Activity {

    private String customerNumber;
    private ActivityType activityType;
    private double distance;
    private LocalDate activityDate;
    private ActivityTracker tracker;
    private int assignedPoints;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public void setTracker(ActivityTracker tracker) {
        this.tracker = tracker;
    }

    public ActivityTracker getTracker() {
        return tracker;
    }

    public int getAssignedPoints() {
        return assignedPoints;
    }

    public void setAssignedPoints(int assignedPoints) {
        this.assignedPoints = assignedPoints;
    }
}
