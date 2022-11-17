package fr.unice.polytech.lastminutemarceltest.objectstest;

import fr.unice.polytech.lastminutemarcel.objects.Ride;
import fr.unice.polytech.lastminutemarcel.objects.Rider;


import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RiderTest {

    private Rider rider;
    private Order order;
    private Ride ride;

    @BeforeEach
    void init(){
        Client client = new Client();
        order = new Order(client);
        rider = new Rider();
    }

    @Test
    public void testDefaultStatus(){
        Assertions.assertEquals(rider.getRiderStatus(), Rider.Status.AVAILABLE);
    }

    @Test
    public void testFailedRetrieveOrderFromShop(){
        ride = new Ride(order, rider);
        rider.setCurrentRide(ride);
        rider.retrieveOrderFromShop();

        assertEquals(Ride.Status.ORDER_IN_PREPARATION, rider.getCurrentRide().getRideStatus());
        assertEquals(Order.LastMinuteMarcelStatus.NOT_ENTRUSTED, rider.getCurrentRide().getOrder().getLastMinuteMarcelStatus());
    }

    @Test
    public void testRetrieveOrderFromShop(){
        ride = new Ride(order, rider);
        rider.setCurrentRide(ride);
        rider.getCurrentRide().changeStatus(Ride.Status.WAITING_FOR_RIDER);
        rider.getCurrentRide().getOrder().changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.WAITING_FOR_RIDER);
        rider.retrieveOrderFromShop();

        assertEquals(Ride.Status.DELIVERY_IN_PROCESS, rider.getCurrentRide().getRideStatus());
        assertEquals(Order.LastMinuteMarcelStatus.DELIVERY_IN_PROCESS, rider.getCurrentRide().getOrder().getLastMinuteMarcelStatus());
    }

    @Test
    public void testFailedDeliveryCompleted(){
        ride = new Ride(order, rider);
        rider.setCurrentRide(ride);
        rider.retrieveOrderFromShop();
        rider.deliveryCompleted();
        assertEquals(Ride.Status.ORDER_IN_PREPARATION, ride.getRideStatus());
    }

    @Test
    public void testDeliveryCompleted(){
        ride = new Ride(order, rider);
        rider.setCurrentRide(ride);
        rider.getCurrentRide().changeStatus(Ride.Status.DELIVERY_IN_PROCESS);
        rider.getCurrentRide().getOrder().changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.DELIVERY_IN_PROCESS);
        rider.deliveryCompleted();

        assertEquals(Ride.Status.DELIVERED, ride.getRideStatus());
        assertEquals(Rider.Status.AVAILABLE, rider.getRiderStatus());
        assertEquals(Order.LastMinuteMarcelStatus.DELIVERED, ride.getOrder().getLastMinuteMarcelStatus());
    }


}





