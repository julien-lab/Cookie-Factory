package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.Calculator;
import io.cucumber.java8.En;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BestOfCookiesStepdefs implements En {

    private TheCookieFactory tfc;
    private Client client;
    private Order order;
    private Order order2;
    private CookieRecipe cookieRecipe;

    public BestOfCookiesStepdefs() {

        Given("^many orders placed$", () -> {
            client = new Client();
            tfc = new TheCookieFactory();
            for (int i=0;i<100;i++){
                Order order = new Order(client,new Date(),
                        Collections.singletonList(new OrderLine(tfc.getRecipes().get(0), 5)),
                        tfc.getShops().get(0));
                order.setPaymentDone(true);
                tfc.getShops().get(0).addPurchasedOrder(order);
                Order order2 = new Order(client,new Date(),
                        Collections.singletonList(new OrderLine(tfc.getRecipes().get(1), 4)),
                        tfc.getShops().get(0));
                order2.setPaymentDone(true);
                tfc.getShops().get(0).addPurchasedOrder(order2);
                Order order3 = new Order(client,new Date(),
                        Collections.singletonList(new OrderLine(tfc.getRecipes().get(2), 3)),
                        tfc.getShops().get(0));
                order3.setPaymentDone(true);
                tfc.getShops().get(0).addPurchasedOrder(order3);
                Order order4 = new Order(client,new Date(),
                        Collections.singletonList(new OrderLine(tfc.getRecipes().get(3), 2)),
                        tfc.getShops().get(0));
                order4.setPaymentDone(true);
                tfc.getShops().get(0).addPurchasedOrder(order4);
                Order order5 = new Order(client,new Date(),
                        Collections.singletonList(new OrderLine(tfc.getRecipes().get(4), 1)),
                        tfc.getShops().get(0));
                order5.setPaymentDone(true);
                tfc.getShops().get(0).addPurchasedOrder(order5);
            }
            for (int i=0;i<200;i++) {
                Order order = new Order(client,new Date(),
                        Collections.singletonList(new OrderLine(tfc.getRecipes().get(5), 6)),
                        tfc.getShops().get(0));
                order.setPaymentDone(true);
                tfc.getShops().get(1).addPurchasedOrder(order);
            }
        });
        When("^the local manager ask for the most popular cookie of his shop from the last 30 days$", () -> {
            cookieRecipe = tfc.getShops().get(0).setGetMostPopularCookie();
        });
        Then("^he gets the most popular of his shop$", () -> {
            assertEquals(tfc.getRecipes().get(0), cookieRecipe);
        });
        When("^the national manager ask for the most popular cookie of his entire production chain$", () -> {
            tfc.retrieveAllOrdersFromTheLast30Days();
        });
        Then("^he gets the most popular cookie recipe of his entire production chain$", () -> {
            assertEquals(tfc.getRecipes().get(5), tfc.setMostPopularCookieOfTheCookieFactory());
        });
        When("^the client orders the best cookie of the cookie factory$", () -> {
            tfc.setDiscountsForCookiesBestOf();
            order = new Order(client,new Date(),
                    Collections.singletonList(new OrderLine(tfc.getRecipes().get(5), 6)),
                    tfc.getShops().get(0));
        });
        Then("^he gets a discount on his order$", () -> {
            double price = new OrderLine(tfc.getRecipes().get(5), 6).getPrice()*(1+tfc.getShops().get(0).getPercentageTax());
            price = Calculator.round(price,2);
            assertNotEquals(order.getPriceAfterTax(),price , 0.0);
        });
        When("^the client orders the best cookie of his shop$", () -> {
            tfc.setDiscountsForCookiesBestOf();
            order2 = new Order(client,new Date(),
                    Collections.singletonList(new OrderLine(tfc.getRecipes().get(0), 6)),
                    tfc.getShops().get(0));
        });
        Then("^he gets a discount on his Order$", () -> {
            double price = new OrderLine(tfc.getRecipes().get(0), 6).getPrice()*(1+tfc.getShops().get(0).getPercentageTax());
            price = Calculator.round(price,2);
            assertNotEquals(order2.getPriceAfterTax(),price , 0.0);
        });

    }

}
