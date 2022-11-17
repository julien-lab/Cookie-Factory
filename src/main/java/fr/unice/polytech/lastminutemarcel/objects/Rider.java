package fr.unice.polytech.lastminutemarcel.objects;

import fr.unice.polytech.thecookiefactory.objects.Order;

public class Rider {

    private static int riderCount = 1;

    public enum Status{
        AVAILABLE, UNAVAILABLE
    }

    private final int riderNumber;
    private String riderName;
    private String riderLastName;
    private Status riderStatus;
    private Ride currentRide;

    public Rider() {
        this.riderNumber = riderCount++;
        this.riderStatus = Status.AVAILABLE;
    }

    public Rider(String riderName, String riderLastName) {
        this.riderNumber = riderCount++;
        this.riderName = riderName;
        this.riderLastName = riderLastName;
        this.riderStatus = Status.AVAILABLE;
    }

    public int getRiderNumber() {
        return riderNumber;
    }

    public Status getRiderStatus() {
        return riderStatus;
    }

    public void setRiderStatus(Status riderStatus) {
        this.riderStatus = riderStatus;
    }

    public static int getRiderCount() {
        return riderCount;
    }

    public static void setRiderCount(int riderCount) {
        Rider.riderCount = riderCount;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderLastName() {
        return riderLastName;
    }

    public void setRiderLastName(String riderLastName) {
        this.riderLastName = riderLastName;
    }

    public void retrieveOrderFromShop(){
        if(currentRide.getRideStatus().equals(Ride.Status.WAITING_FOR_RIDER) && currentRide.getOrder().getLastMinuteMarcelStatus().equals(Order.LastMinuteMarcelStatus.WAITING_FOR_RIDER)) {
            currentRide.changeStatus(Ride.Status.DELIVERY_IN_PROCESS);
            currentRide.getOrder().changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.DELIVERY_IN_PROCESS);
        }
    }

    public void deliveryCompleted(){
        if(currentRide.getRideStatus().equals(Ride.Status.DELIVERY_IN_PROCESS) && currentRide.getOrder().getLastMinuteMarcelStatus().equals(Order.LastMinuteMarcelStatus.DELIVERY_IN_PROCESS)) {
            currentRide.changeStatus(Ride.Status.DELIVERED);
            currentRide.getOrder().changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.DELIVERED);
            currentRide.getOrder().setHasBeenWithdrawn(true);
            riderStatus = Status.AVAILABLE;
            currentRide = null;
        }
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public void setCurrentRide(Ride currentRide) {
        this.currentRide = currentRide;
    }
}
