package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.*;
import io.cucumber.java8.En;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PurchaseOrderStepdef implements En {

    private Client client;
    private Order order;
    private Shop shop;
    private LocalTime time;
    private Date date;


    public PurchaseOrderStepdef() {

        Given("^a client$", () -> {
            client= new Client();
        });

        And("^an order with cookie$", () -> {
            order = new Order(client);
            order.addOrderLine(new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY), 2);
            order.addOrderLine(new CookieRecipe("Oatmeal / Cinnamon", Dough.OATMEAL, Flavor.CINNAMON, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.TOPPED, Cooking.CRUNCHY), 3);
            order.setShop(null);
        });

        And("^a shop$", () -> {
            shop = new Shop();
        });

        And("^a Local Time and a day", () -> {
            time = LocalTime.of(11, 0);
            date = new Date();
        });

        When("^the client has chosen a shop and a pickup time before paying his order and has enough money$", ()-> {
            order.pickShop(shop);
            order.setPickUpTime(time);
            order.setPickUpDay(date);
            order.purchase();
        });

        Then("^Order is added to the shop$", () -> {
            assertEquals(order, shop.getPurchasedOrders().get(0));
        });

        And("^Ingredients of the order is consumed in the storage shop's$", () -> {
            assertEquals(Integer.valueOf(98),shop.getStorage().getDoughs().get(Dough.CHOCOLATE));
            assertEquals(Integer.valueOf(97),shop.getStorage().getDoughs().get(Dough.OATMEAL));
            assertEquals(Integer.valueOf(98),shop.getStorage().getFlavors().get(Flavor.CHILI));
            assertEquals(Integer.valueOf(97),shop.getStorage().getFlavors().get(Flavor.CINNAMON));
            assertEquals(Integer.valueOf(95),shop.getStorage().getToppings().get(Topping.MILK_CHOCOLATE));
        });

        And("^Order is added in the list of order to prepare of the OrderSupervisor$", () -> {
            assertEquals(order, shop.getOrderSupervisor().getOrdersToPrepare().get(0));
        });

        And("^Order pick up time is of the time availables$", () -> {
            assertFalse(shop.getSchedule().get(order.getPickUpDay().getTime()).isTimeSlotAvailable(time));
        });

        And("^Order is added to the client's list of orders$", () -> {
            assertEquals(order, client.getOrder());
        });

        And("^The payment of the order is marked as done$", () -> {
            assertTrue(order.isPaymentDone());
        });

        When("^the client tries to pay his order without a shop$", ()-> {
            order.setShop(null);
            order.setPickUpTime(time);
            order.setPickUpDay(date);
            order.purchase();
        });

        Then("^the order isn't purchased 1$", () -> {
            assertFalse(order.isPaymentDone());
        });

        When("^the client tries to pay his order without a pick up time$", ()-> {
            order.pickShop(shop);
            order.setPickUpDay(null);
            order.setPickUpTime(null);
            order.purchase();
        });

        Then("^the order isn't purchased 2$", () -> {
            assertFalse(order.isPaymentDone());
        });

        When("^the client tries to pay his order but doesn't have enough money$", ()-> {
            order.pickShop(shop);
            order.setPickUpTime(time);
            order.setPickUpDay(date);
            client.setMoneyOnHisCard(1);
            order.purchase();
        });

        Then("^the order isn't purchased 3$", () -> {
            assertFalse(order.isPaymentDone());
        });

    }

}
