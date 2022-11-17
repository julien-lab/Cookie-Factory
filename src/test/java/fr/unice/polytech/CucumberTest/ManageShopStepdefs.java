package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ManageShopStepdefs implements En {

    private TheCookieFactory tfc;
    private Order order;
    private Order order2;
    private Order order3;
    private HashMap<CookieRecipe,Integer> cookiesToMake;
    private CookieRecipe newRecipe;
    private List<Order> ordersPurchasedAndWithdrawn;

    public ManageShopStepdefs() {
        Given("^3 customers orders purchased and withdrawn$", () -> {
            tfc = new TheCookieFactory();

            order = new Order(null);
            order.setShop(tfc.getShops().get(0));
            order.setPaymentDone(true);
            order.setHasBeenWithdrawn(true);
            tfc.getShops().get(0).addPurchasedOrder(order);

            order2 = new Order(null);
            order.setShop(tfc.getShops().get(1));
            order2.setPaymentDone(true);
            order2.setHasBeenWithdrawn(true);
            tfc.getShops().get(2).addPurchasedOrder(order2);

            order3 = new Order(null);
            order.setShop(tfc.getShops().get(2));
            order3.setPaymentDone(true);
            order3.setHasBeenWithdrawn(true);
            tfc.getShops().get(3).addPurchasedOrder(order3);
        });
        When("^the manager wants to create a new recipe$", () -> {
            newRecipe = new CookieRecipe("Chicochoulala", Dough.OATMEAL, Flavor.VANILLA, new ArrayList<>(Collections.singletonList(Topping.MNMS)), Mix.MIXED, Cooking.CHEWY);
            tfc.getNationalManager().addNewCookieRecipe(newRecipe);
        });
        Then("^a new recipe is created$", () -> {
            assertEquals(tfc.getRecipes().get(7), newRecipe);
        });
        When("^the manager wants to delete the recipe number (\\d+)$", (Integer arg0) -> {
            tfc.getNationalManager().removeCookieRecipe(tfc.getRecipes().get(6));
        });
        Then("^the recipe number (\\d+) is deleted$", (Integer arg0) -> {
            assertEquals(6,tfc.getRecipes().size());
        });
        When("^the National manager retrieve orders that has been purchased and withdrawn$", () -> {
            ordersPurchasedAndWithdrawn = tfc.getOrdersOfAllShops();
        });
        Then("^he gets a list of orders$", () -> {
            assertNotNull(ordersPurchasedAndWithdrawn);
            assertEquals(3, ordersPurchasedAndWithdrawn.size());
            List<Order> orders = new ArrayList<>();
            orders.add(order);
            orders.add(order2);
            orders.add(order3);
            assertEquals(orders, ordersPurchasedAndWithdrawn);
        });

    }
}
