package fr.unice.polytech.lastminutemarceltest;


import fr.unice.polytech.lastminutemarcel.LastMinuteMarcel;
import fr.unice.polytech.lastminutemarcel.objects.Ride;
import fr.unice.polytech.lastminutemarcel.objects.Rider;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.unice.polytech.lastminutemarcel.LastMinuteMarcel.*;
import static org.junit.Assert.*;


public class LastMinuteMarcelTest {

    private TheCookieFactory tfc;
    private Order order;
    private Client client;
    private LastMinuteMarcel lmm = new LastMinuteMarcel();

    @BeforeEach
    void init(){
        client = new Client();
        order = new Order(client);
    }

    @Test
    public void testFindAvailableRider(){
        Rider rider;
        rider = LastMinuteMarcel.findAvailableRider();
        assertNotEquals(null, rider);
        assertTrue(LastMinuteMarcel.getRiders().contains(rider));
    }

    @Test
    public void testFindAvailableRiderAllRidersBusy(){
        Rider rider;
        for(int i = 0 ; i < LastMinuteMarcel.getRiders().size() ; i++){
            rider = LastMinuteMarcel.findAvailableRider();
            rider.setRiderStatus(Rider.Status.UNAVAILABLE);
        }
        rider = LastMinuteMarcel.findAvailableRider();
        assertNull(rider);
    }

    @Test
    public void testFindRide(){
        Client client1 = new Client();
        Client client2 = new Client();
        Order order1 = new Order(client1);
        Order order2 = new Order(client2);
        LastMinuteMarcel.acceptOrder(order, false);
        LastMinuteMarcel.acceptOrder(order1, true);
        LastMinuteMarcel.acceptOrder(order2, false);
        Ride foundRide = findRide(order);
        Ride foundRide1 = findRide(order1);
        Ride foundRide2 = findRide(order2);
        Assertions.assertEquals(foundRide.getOrder(), order);
        Assertions.assertEquals(foundRide1.getOrder(), order1);
        Assertions.assertEquals(foundRide2.getOrder(), order2);
    }

    @Test
    public void testNonExistingRideFindRide(){
        Order order1 = new Order(client);
        Ride foundRide = findRide(order1);
        assertNull(foundRide);
    }

    @Test
    public void testAskRiderToMove(){
        LastMinuteMarcel.acceptOrder(order, false);
        LastMinuteMarcel.askRiderToMove(order);
        Assertions.assertEquals(Ride.Status.WAITING_FOR_RIDER, findRide(order).getRideStatus() );
    }

    @Test
    public void testNoFreeRidersAskRiderToMove(){
        Rider rider;
        for(int i = 0 ; i < LastMinuteMarcel.getRiders().size() ; i++){
            rider = LastMinuteMarcel.findAvailableRider();
            rider.setRiderStatus(Rider.Status.UNAVAILABLE);
        }
        Order order1 = new Order(client);
        LastMinuteMarcel.acceptOrder(order1, false);
        LastMinuteMarcel.askRiderToMove(order1);
        Assertions.assertEquals(order1.getLastMinuteMarcelStatus(), Order.LastMinuteMarcelStatus.NO_FREE_RIDERS);

    }

    @Test
    public void testApplyLastMinuteMarcelFeeRegular(){
        tfc = new TheCookieFactory();
        Order order1 = new Order(client);
        order1.addOrderLine(tfc.getRecipes().get(0), 1);
        order1.addOrderLine(tfc.getRecipes().get(1), 2);
        Assertions.assertEquals(3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(4.08, order1.getPriceAfterTax());
        LastMinuteMarcel.applyLastMinuteMarcelFee(order1);
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 4.08, order1.getPriceAfterTax());
    }

    @Test
    public void testApplyLastMinuteMarcelFeeLateRequest(){
        tfc = new TheCookieFactory();
        Order order1 = new Order(client);
        order1.addOrderLine(tfc.getRecipes().get(0), 1);
        order1.addOrderLine(tfc.getRecipes().get(1), 2);
        order1.setLateLastMinuteMarcelStatusRequest(true);
        Assertions.assertEquals(3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(4.08, order1.getPriceAfterTax());
        LastMinuteMarcel.applyLastMinuteMarcelFee(order1);
        Assertions.assertEquals((FIXED_DELIVERY_FEE * 1.5) + 3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals((FIXED_DELIVERY_FEE * 1.5) + 4.08, order1.getPriceAfterTax());
    }

    @Test
    public void testAssignRide(){
        Rider rider = findAvailableRider();
        assignRide(order, rider);
        Assertions.assertEquals(Rider.Status.UNAVAILABLE, rider.getRiderStatus());
        Assertions.assertEquals(Order.LastMinuteMarcelStatus.ORDER_IN_PREPARATION, order.getLastMinuteMarcelStatus());
    }

    @Test
    public void testBusyRiderAssignRide(){
        Rider rider = findAvailableRider();
        rider.setRiderStatus(Rider.Status.UNAVAILABLE);
        assignRide(order, rider);
        Assertions.assertEquals(Rider.Status.UNAVAILABLE, rider.getRiderStatus());
        Assertions.assertEquals(Order.LastMinuteMarcelStatus.NOT_ENTRUSTED, order.getLastMinuteMarcelStatus());
    }

    @Test
    public void testWrongOrderStatusAssignRide(){
        Rider rider = findAvailableRider();
        order.changeLastMinuteMarcelStatus(Order.LastMinuteMarcelStatus.DELIVERY_IN_PROCESS);
        assignRide(order, rider);
        Assertions.assertEquals(Rider.Status.AVAILABLE, rider.getRiderStatus());
        Assertions.assertEquals(Order.LastMinuteMarcelStatus.DELIVERY_IN_PROCESS, order.getLastMinuteMarcelStatus());
    }

    @Test
    public void testAddRide(){
        tfc = new TheCookieFactory();

        Order order1 = new Order(client);
        order1.addOrderLine(tfc.getRecipes().get(0), 1);
        order1.addOrderLine(tfc.getRecipes().get(1), 2);
        Rider rider = findAvailableRider();
        Assertions.assertEquals(3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(4.08, order1.getPriceAfterTax());
        LastMinuteMarcel.addRide(order1, rider, false);

        Ride ride = findRide(order1);

        Assertions.assertFalse(order1.isLateLastMinuteMarcelStatusRequest());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 4.08, order1.getPriceAfterTax());
        Assertions.assertEquals(ride, rider.getCurrentRide());
    }

    @Test
    public void testAcceptOrder(){
        tfc = new TheCookieFactory();
        Order order1 = new Order(client);
        order1.addOrderLine(tfc.getRecipes().get(0), 1);
        order1.addOrderLine(tfc.getRecipes().get(1), 2);

        Assertions.assertTrue(LastMinuteMarcel.acceptOrder(order1, false));
        Ride ride = findRide(order1);
        Rider rider = ride.getRider();
        Assertions.assertFalse(order1.isLateLastMinuteMarcelStatusRequest());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 4.08, order1.getPriceAfterTax());
        Assertions.assertEquals(ride, rider.getCurrentRide());
        Assertions.assertEquals(Rider.Status.UNAVAILABLE, rider.getRiderStatus());

    }

    @Test
    public void testNoAvailableRiderAcceptOrder(){
        tfc = new TheCookieFactory();
        Order order1 = new Order(client);
        order1.addOrderLine(tfc.getRecipes().get(0), 1);
        order1.addOrderLine(tfc.getRecipes().get(1), 2);
        for(int i = 0 ; i < LastMinuteMarcel.getRiders().size() ; i++){
            Rider busyRider = LastMinuteMarcel.findAvailableRider();
            busyRider.setRiderStatus(Rider.Status.UNAVAILABLE);
        }

        Assertions.assertFalse(LastMinuteMarcel.acceptOrder(order1, false));
        Assertions.assertNull(findRide(order1));
        Assertions.assertEquals(Order.LastMinuteMarcelStatus.NO_FREE_RIDERS, order1.getLastMinuteMarcelStatus());

        Assertions.assertEquals(3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(4.08, order1.getPriceAfterTax());
    }


}









