package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HaveDiscountsStepdefs implements En {

    private Order order;
    private TheCookieFactory tfc;
    private ClientAccount clientAccount;
    private Client client;

    public HaveDiscountsStepdefs() {

        Given("^a client with an account which is part of the cookie factory loyalty program$", () -> {
            tfc = new TheCookieFactory();
            clientAccount = new ClientAccount("Georges");
            client = new Client(clientAccount);
            client.getAccount().joinLoyaltyProgram();
        });

        When("^the client is ordering and has ordered at least 30 cookies from previous orders$", () -> {
            clientAccount.setNumberCookiesPurchased(30);
            order = new Order(client);
            order.addOrderLine(tfc.getRecipes().get(3),1);
            order.applyLoyaltyDiscount();
        });

        Then("^the client gets the promised 10% off on his order$", () -> {
            assertEquals(order.getPriceBeforeTax(), order.getOrderLines().get(0).getPrice()*0.9);
        });

        When("^the client is ordering and has ordered less than 30 cookies from previous orders$", () -> {
            clientAccount.setNumberCookiesPurchased(29);
            order = new Order(client);
            order.addOrderLine(tfc.getRecipes().get(3),1);
            order.applyLoyaltyDiscount();
        });

        Then("^the client doesn't get the promised 10% off on his order$", () -> {
            assertEquals(order.getPriceBeforeTax(), order.getOrderLines().get(0).getPrice());
        });

    }
}
