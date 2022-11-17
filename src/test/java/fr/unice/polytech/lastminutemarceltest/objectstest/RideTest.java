package fr.unice.polytech.lastminutemarceltest.objectstest;


import fr.unice.polytech.lastminutemarcel.objects.Ride;
import fr.unice.polytech.lastminutemarcel.objects.Rider;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RideTest {

    Client client;
    Order order;
    Rider rider;
    Ride ride;

    @Test
    public void testDefaultStatus(){
        client = new Client();
        rider = new Rider();
        order = new Order(client);
        ride = new Ride(order, rider);

        assertEquals(Ride.Status.ORDER_IN_PREPARATION,ride.getRideStatus());
    }

    @Test
    public void testChangeStatus(){
        client = new Client();
        rider = new Rider();
        order = new Order(client);
        ride = new Ride(order, rider);

        ride.changeStatus(Ride.Status.WAITING_FOR_RIDER);
        assertEquals(Ride.Status.WAITING_FOR_RIDER,ride.getRideStatus());
        ride.changeStatus(Ride.Status.DELIVERY_IN_PROCESS);
        assertEquals(Ride.Status.DELIVERY_IN_PROCESS,ride.getRideStatus());
        ride.changeStatus(Ride.Status.DELIVERED);
        assertEquals(Ride.Status.DELIVERED,ride.getRideStatus());
    }

}
