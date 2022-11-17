package fr.unice.polytech.cucumbertest;


import fr.unice.polytech.thecookiefactory.ingredients.Dough;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;

import static org.junit.Assert.*;

public class KitchenBalancingStepdef implements En{

    private Order order;
    private Order order2;
    private TheCookieFactory tfc;


    public KitchenBalancingStepdef() {

        Given("^some shops and an order$", () -> {
            tfc = new TheCookieFactory();
            order = new Order(null);
            order.pickShop(tfc.getShops().get(0));
            order2 = new Order(null);
            TheCookieFactory tfc = new TheCookieFactory();
            ArrayList<OrderLine> ols = new ArrayList<>();

            ols.add(new OrderLine( tfc.getRecipes().get(0), 2));
            ols.add(new OrderLine( tfc.getRecipes().get(1), 5));
            ols.add(new OrderLine( tfc.getRecipes().get(2), 4));
            ols.add(new OrderLine( tfc.getRecipes().get(3), 1));
            order2.setOrderLines(ols);
            order2.pickShop(tfc.getShops().get(4));

            EnumMap<Dough, Integer> doughs = new EnumMap<>(Dough.class);
            doughs.put(Dough.OATMEAL, 10);
            doughs.put(Dough.CHOCOLATE, 1);
            doughs.put(Dough.PLAIN, 0);
            order2.getShop().getStorage().setDoughs(doughs);
        });

        When("^the client try to choose a pick up date$", () -> {
            order.getShop().setTechnicalIssue(true);
            order.pickUpDayTime(new Date(), LocalTime.of(11, 0));
        });

        Then("^the order can't be validated$", () -> {
            assertNull(order.getPickUpTime());
            assertNull(order.getPickUpDay());
        });

        And("^an other open shop close to this one is proposed$", () -> {
            assertEquals(order.getShop().closeShopAvailable(order,new Date(),LocalTime.of(11, 0)).getShopName(), order.getShop().getNextShops().get(0).getShopName());
        });

        And("^the client can chose this new shop and validate his order$", () -> {
            order.pickShop(order.getShop().closeShopAvailable(order,new Date(),LocalTime.of(11, 0)));
            assertTrue(order.getShop().validatePickUpDayTime(new Date(), LocalTime.of(11, 0)));
        });

        When("^the client try to validate his order$", () -> {
            order2.pickUpDayTime(new Date(), LocalTime.of(12,0));
        });

        Then("^he can't$", () -> {
            assertFalse(order2.getShop().canDoTheOrder(order2));
        });

        And("^a close shop with the ingredient is proposed$", () -> {
            assertEquals(order2.getShop().closeShopAvailable(order2,new Date(),LocalTime.of(11, 0)).getShopName(), order2.getShop().getNextShops().get(0).getShopName());
        });

        And("^the client can validate his order with this shop$", () -> {
            order2.pickShop(order2.getShop().closeShopAvailable(order2,new Date(),LocalTime.of(11, 0)));
            assertTrue(order2.getShop().canDoTheOrder(order2));
        });

    }

}
