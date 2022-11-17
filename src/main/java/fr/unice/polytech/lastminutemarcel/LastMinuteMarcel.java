package fr.unice.polytech.lastminutemarcel;

import fr.unice.polytech.lastminutemarcel.objects.Ride;
import fr.unice.polytech.lastminutemarcel.objects.Rider;
import fr.unice.polytech.thecookiefactory.objects.Order;

import java.util.ArrayList;
import java.util.List;

public class LastMinuteMarcel {

    private static List<Ride> rides ;
    private static List<Rider> riders;
    public static final int FIXED_DELIVERY_FEE = 15;

    public LastMinuteMarcel() {
        riders = createRiders();
        rides = new ArrayList<>();
    }


    public static boolean acceptOrder(Order order, Boolean isLateRequest){
        Rider rider = findAvailableRider();
        if(rider == null){
            order.changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.NO_FREE_RIDERS);
            return false;
        }
        addRide(order, rider, isLateRequest);
        return true;
    }

    public static void addRide(Order order, Rider rider, boolean isLateRequest) {
        assignRide(order, rider);
        order.setLateLastMinuteMarcelStatusRequest(isLateRequest);
        applyLastMinuteMarcelFee(order);
        Ride ride = new Ride(order, rider);
        rides.add(ride);
        rider.setCurrentRide(ride);
    }

    public static void applyLastMinuteMarcelFee(Order order) {
        if(order.isLateLastMinuteMarcelStatusRequest()){
            order.applyDeliveryFee(FIXED_DELIVERY_FEE * 1.5);
        }
        else{
            order.applyDeliveryFee(FIXED_DELIVERY_FEE);
        }
    }

    public static void askRiderToMove(Order order) {
        Ride ride = findRide(order);
        if (ride != null){
            ride.changeStatus(Ride.Status.WAITING_FOR_RIDER);
        }
    }

    public static Ride findRide(Order order){
        Ride foundRide = null;
        for(Ride ride : rides){
            if(ride.getOrder().equals(order)){
                foundRide = ride;
                break;
            }
        }
        return foundRide;
    }

    private List<Rider> createRiders() {
        riders = new ArrayList<>();
        riders.add(new Rider("Presley ","Joseph"));
        riders.add(new Rider("Aryan","Giles"));
        riders.add(new Rider("Sabina ","Wagstaff"));
        riders.add(new Rider("Jun ","Bourne"));
        riders.add(new Rider("Nawal ","Moore"));
        riders.add(new Rider("Liam ","Wilson"));
        riders.add(new Rider("Tyler","Drake"));
        return riders;
    }

    public static void assignRide(Order order, Rider rider){
        if (rider.getRiderStatus().equals(Rider.Status.AVAILABLE) && order.getLastMinuteMarcelStatus().equals(Order.LastMinuteMarcelStatus.NOT_ENTRUSTED)) {
            rider.setRiderStatus(Rider.Status.UNAVAILABLE);
            order.changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.ORDER_IN_PREPARATION);
        }
    }

    public static Rider findAvailableRider(){
        Rider availableRider = null;
        for(Rider rider : riders){
            if(rider.getRiderStatus().equals(Rider.Status.AVAILABLE)){
                availableRider = rider;
                break;
            }
        }
        return availableRider;
    }

    public static List<Rider> getRiders() {
        return riders;
    }

    public static ArrayList<Ride> getRides() {
        return (ArrayList<Ride>) rides;
    }
}
