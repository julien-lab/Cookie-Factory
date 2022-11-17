package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.lastminutemarcel.LastMinuteMarcel;
import fr.unice.polytech.lastminutemarcel.objects.Rider;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;

import io.cucumber.java8.En;

import static org.junit.Assert.assertEquals;


public class ChooseLastMinuteMarcelDeliveryStepdefs implements En {
    TheCookieFactory tcf ;
    LastMinuteMarcel lmm ;
    Order order;
    Client client;


    public ChooseLastMinuteMarcelDeliveryStepdefs() {

        Given("^a client with an order$", () -> {
            tcf = new TheCookieFactory();
            lmm = new LastMinuteMarcel();
            order = new Order(client);
            order.addOrderLine(tcf.getRecipeByName("Chocolate / Chili"), 5);
        });

        When("^lastMinuteMarcel receives a delivery demand that it can do$", () -> {
            LastMinuteMarcel.acceptOrder(order, false);
        });
        Then("^the order's lastMinuteMarcelstatus is properly updated$", () -> {
            assertEquals(Order.LastMinuteMarcelStatus.ORDER_IN_PREPARATION, order.getLastMinuteMarcelStatus());
        });
        And("^the first rider who was initially available becomes unavailable$", () -> {
            assertEquals(Rider.Status.UNAVAILABLE, LastMinuteMarcel.getRiders().get(0).getRiderStatus());
        });
        And("^a ride is created and assigned to the rider$", () -> {
            assertEquals(LastMinuteMarcel.getRiders().get(0), LastMinuteMarcel.getRides().get(0).getRider());
        });
        And("^the order's price reflects the standard delivery fee$", () -> {
            assertEquals((7.2+15), order.getPriceAfterTax(), 0);
        });


        When("^lastMinuteMarcel receives a late delivery demand that it can do$", () -> {
            LastMinuteMarcel.acceptOrder(order, true);
        });
        Then("^lastMinuteMarcelstatus is properly updated$", () -> {
            assertEquals( Order.LastMinuteMarcelStatus.ORDER_IN_PREPARATION, order.getLastMinuteMarcelStatus());
        });
        And("^the first rider becomes unavailable$", () -> {
            assertEquals(Rider.Status.UNAVAILABLE, LastMinuteMarcel.getRiders().get(0).getRiderStatus());
        });
        And("^a ride is created and is assigned to the rider$", () -> {
            assertEquals(LastMinuteMarcel.getRiders().get(0), LastMinuteMarcel.getRides().get(0).getRider());
        });
        And("^the order's price reflects the late delivery fee$", () -> {
            assertEquals((7.2+22.5), order.getPriceAfterTax(), 0);
        });


        When("^lastMinuteMarcel receives a delivery demand but has no available rider left$", () -> {
            for(Rider rider : LastMinuteMarcel.getRiders() ){
                rider.setRiderStatus(Rider.Status.UNAVAILABLE);
            }
            LastMinuteMarcel.acceptOrder(order, false);
        });
        Then("^the order cannot be delivered by lastMinuteMarcel$", () -> {
            assertEquals(Order.LastMinuteMarcelStatus.NO_FREE_RIDERS, order.getLastMinuteMarcelStatus());
        });


    }
}
