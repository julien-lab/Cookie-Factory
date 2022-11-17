package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.objects.account.Cashier;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import static org.junit.Assert.*;

public class WithdrawnOrderStepdef implements En{

    private TheCookieFactory cookieFactory;
    private Order order1;
    private Order order2;
    private Client client1;
    private Client client2;
    private Cashier cashier1;
    private Cashier cashier2;

    public WithdrawnOrderStepdef() {

        Given("^Clients of orders$", () -> {
            client1 = new Client();
            client2 = new Client();
        });

        And("^Cashiers of different shops$", () -> {
            cookieFactory = new TheCookieFactory();
            cashier1 = cookieFactory.getShops().get(0).getCashier();
            cashier2 = cookieFactory.getShops().get(1).getCashier();
        });

        And("^Orders ready to be withdrawn$", () -> {
            order1 = new Order(client1);
            order2 = new Order(client2);
            client1.setOrder(order1);
            client2.setOrder(order2);
            cashier1.addOrderReady(order1);
            cashier2.addOrderReady(order2);
        });

        When("^the client asks his order in the good shop$", () -> {
            cashier1.giveOrder(client1.getOrder());
        });

        Then("^the cashier gives him and the order is remove from the list of orders ready$", () -> {
            assertTrue(order1.hasBeenWithdrawn());
            assertFalse(cashier1.getOrdersReady().contains(order1));
        });

        When("^the client asks his order in the wrong shop$", () -> {
            cashier1.giveOrder(client2.getOrder());
        });

        Then("^the cashier does not give him and the order is still in the list of orders ready of the good shop$", () -> {
            assertFalse(order2.hasBeenWithdrawn());
            assertTrue(cashier2.getOrdersReady().contains(order2));
        });

    }
}
