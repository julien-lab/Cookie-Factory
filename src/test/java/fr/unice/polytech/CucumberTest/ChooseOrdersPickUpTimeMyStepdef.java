package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import java.time.LocalTime;
import java.util.Date;

import static org.junit.Assert.*;

public class ChooseOrdersPickUpTimeMyStepdef implements En {

    private Order order;
    private TheCookieFactory tfc;
    private Order order2;
    private Client client;
    private Client client2;

    public ChooseOrdersPickUpTimeMyStepdef() {

        Given("^two orders with cookies$", () -> {
            client = new Client();
            client2 = new Client();
            tfc = new TheCookieFactory();
            order = new Order(new Client());
            client.setOrder(order);
            order.addOrderLine(tfc.getRecipes().get(0), 1);
            order.pickShop(tfc.getShops().get(0));
            order2 = new Order(new Client());
            client2.setOrder(order2);
            order2.addOrderLine(tfc.getRecipes().get(0), 1);
            order2.pickShop(tfc.getShops().get(0));
        });

        When("^the client sets order pick-up time and day$", () -> {
            order.pickUpDayTime(new Date(),LocalTime.of(11,0));
        });

        Then("^the order can be validated$", () -> {
            assertTrue(order.getShop().validatePickUpDayTime(order.getPickUpDay(),order.getPickUpTime()));
        });

        When("^the client sets a wrong order pick-up time$", () -> {
            order.pickUpDayTime(new Date(),LocalTime.of(23,0));
        });

        Then("^the order cannot be validated$", () -> {
            assertNull(order.getPickUpDay());
            assertNull(order.getPickUpTime());
        });

        When("^the clients set the same pick-up time for their orders$", () -> {
            order.pickUpDayTime(new Date(),LocalTime.of(11,0));
            order2.pickUpDayTime(new Date(),LocalTime.of(11,0));
        });

        Then("^one client won't be able to take this time slot$", () -> {
            order.purchase();
            assertTrue(order.isPaymentDone());
            order2.purchase();
            assertFalse(order2.isPaymentDone());
        });

    }
}
