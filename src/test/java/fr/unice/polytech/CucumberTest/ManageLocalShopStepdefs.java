package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ManageLocalShopStepdefs implements En {

    private TheCookieFactory tfc;
    private Shop shop;
    private Order order;
    private Client client;

    public ManageLocalShopStepdefs() {

        Given("^a local who wants to change opening time and closing time$", () -> {
            tfc = new TheCookieFactory();
            client = new Client();
        });

        When("^the local manager change the opening time of his shop$", () -> {
            shop = tfc.getShops().get(0);
            shop.getLocalManager().setOpeningTime(LocalTime.of(10,0));
        });

        Then("^the opening time is changed$", () -> {
            assertEquals(LocalTime.of(10,0), shop.getOpeningTime());
        });

        When("^the local manager change the closing time of his shop$", () -> {
            shop = tfc.getShops().get(0);
            shop.getLocalManager().setClosingTime(LocalTime.of(16,0));
        });

        Then("^the closing time is changed$", () -> {
            assertEquals(shop.getClosingTime(),LocalTime.of(16,0));
        });

        When("^the local manager access his shop statistics$", () -> {
            order = new Order(null);
            order.setShop(tfc.getShops().get(0));
            order.setPaymentDone(true);
            order.setHasBeenWithdrawn(true);
            tfc.getShops().get(0).addPurchasedOrder(order);
        });

        Then("^the local manager gets his shop statistics$", () -> {
            assertNotNull(tfc.getShops().get(0).getPurchasedOrders());
            assertEquals(1, tfc.getShops().get(0).getPurchasedOrders().size());
            assertEquals(order, tfc.getShops().get(0).getPurchasedOrders().get(0));
        });

    }
}
