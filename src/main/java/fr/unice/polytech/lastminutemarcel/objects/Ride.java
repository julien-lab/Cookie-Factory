package fr.unice.polytech.lastminutemarcel.objects;

import fr.unice.polytech.thecookiefactory.objects.Order;

public class Ride {

    private static int rideCount = 1;

    public enum Status{
        ORDER_IN_PREPARATION, WAITING_FOR_RIDER, DELIVERY_IN_PROCESS, DELIVERED
    }

    private final int rideNumber;
    private final Order order;
    private final Rider rider;
    private Status rideStatus;

    public Ride(Order order, Rider rider) {
        this.rideNumber = rideCount++;
        this.order = order;
        this.rider = rider;
        this.rideStatus = Status.ORDER_IN_PREPARATION;
    }

    public int getRideNumber() {
        return rideNumber;
    }

    public void changeStatus(Status newStatus){
        this.rideStatus = newStatus;
    }

    public Status getRideStatus() {
        return rideStatus;
    }

    public Order getOrder() {
        return order;
    }

    public Rider getRider() {
        return rider;
    }
}
