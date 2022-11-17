package fr.unice.polytech.thecookiefactorytest.objectstest;

import fr.unice.polytech.thecookiefactory.Calculator;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop newYork;
    private Shop philadelphia;
    private Shop washington;
    private Order order;
    private Client client;

    EnumMap<Dough, Integer> customDoughs = new EnumMap<Dough, Integer>(Dough.class);
    EnumMap<Flavor, Integer> customFlavors = new EnumMap<Flavor, Integer>(Flavor.class);
    EnumMap<Topping, Integer> customToppings = new EnumMap<Topping, Integer>(Topping.class);

    @Before
    public void initialize() {
        newYork = new Shop("New York", LocalTime.of(8,30), LocalTime.of(19,30), 0.11);
        philadelphia = new Shop("Philadelphia", LocalTime.of(10,00), LocalTime.of(20,30), 0.11);
        washington = new Shop("Washington", LocalTime.of(9,00), LocalTime.of(18,30), 0.11);
        client = new Client();
        order = new Order(client);
        TheCookieFactory tfc = new TheCookieFactory();
        ArrayList<OrderLine> ols = new ArrayList<>();
        ols.add(new OrderLine( tfc.getRecipes().get(0), 2));
        ols.add(new OrderLine( tfc.getRecipes().get(1), 5));
        ols.add(new OrderLine( tfc.getRecipes().get(2), 4));
        ols.add(new OrderLine( tfc.getRecipes().get(3), 1));

        customDoughs.put(Dough.OATMEAL,2);
        customFlavors.put(Flavor.CHILI, 1);
        customToppings.put(Topping.MNMS, 3);
        CookieRecipe customizedRecipe = new CookieRecipe("myCustomRecipe", Mix.MIXED, Cooking.CRUNCHY);
        customizedRecipe.setDoughs(customDoughs);
        customizedRecipe.setFlavors(customFlavors);
        customizedRecipe.setToppings(customToppings);

        ols.add(new OrderLine(customizedRecipe, 2));
        order.setOrderLines(ols);
    }


    @Test
    public void closeShopAvailableTest(){
        newYork.getNextShops().add(philadelphia);
        newYork.getNextShops().add(washington);
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());

        assertEquals(philadelphia.getShopName(),newYork.closeShopAvailable(order,new Date(),LocalTime.of(11,0)).getShopName());
    }

    @Test
    public void closeShopAvailableTestTechnicalIssue(){
        newYork.getNextShops().add(philadelphia);
        newYork.getNextShops().add(washington);
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());

        philadelphia.setTechnicalIssue(true);

        assertEquals(washington.getShopName(),newYork.closeShopAvailable(order,new Date(),LocalTime.of(11,0)).getShopName());
    }

    @Test
    public void closeShopAvalaibleTestIngredient(){
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());
        philadelphia.getNextShops().add(washington);
        philadelphia.getNextShops().add(newYork);
        EnumMap<Topping, Integer> toppings = new EnumMap<>(Topping.class);
        toppings.put(Topping.WHITE_CHOCOLATE, 10);
        toppings.put(Topping.MNMS, 5);
        toppings.put(Topping.MILK_CHOCOLATE, 1);
        toppings.put(Topping.REESES_BUTTERCUP, 1);
        washington.getStorage().setToppings(toppings);
        System.out.println(philadelphia.closeShopAvailable(order,new Date(),LocalTime.of(11,0)));
        assertEquals(newYork.getShopName(),philadelphia.closeShopAvailable(order,new Date(),LocalTime.of(11,0)).getShopName());
    }

    @Test
    public void canDoTheOrderTest(){
        assertTrue(newYork.canDoTheOrder(order));

        EnumMap<Dough, Integer> doughs = new EnumMap<>(Dough.class);
        doughs.put(Dough.OATMEAL, 10);
        doughs.put(Dough.CHOCOLATE, 1);
        doughs.put(Dough.PLAIN, 0);
        doughs.put(Dough.PEANUT_BUTTER, 0);
        philadelphia.getStorage().setDoughs(doughs);
        assertFalse(philadelphia.canDoTheOrder(order));

        EnumMap<Topping, Integer> toppings = new EnumMap<>(Topping.class);
        toppings.put(Topping.WHITE_CHOCOLATE, 10);
        toppings.put(Topping.MNMS, 5);
        toppings.put(Topping.MILK_CHOCOLATE, 1);
        toppings.put(Topping.REESES_BUTTERCUP, 1);
        washington.getStorage().setToppings(toppings);
        assertFalse(washington.canDoTheOrder(order));

        // further tests
        Order order2 = new Order(client);
        EnumMap<Dough, Integer> doughs2 = new EnumMap<>(Dough.class);
        doughs2.put(Dough.OATMEAL, 10);
        doughs2.put(Dough.CHOCOLATE, 10);
        doughs2.put(Dough.PEANUT_BUTTER,10);
        doughs2.put(Dough.PLAIN, 10);
        washington.getStorage().setDoughs(doughs2);

        EnumMap<Flavor, Integer> flavors2 = new EnumMap<>(Flavor.class);
        flavors2.put(Flavor.CHILI, 10);
        flavors2.put(Flavor.CINNAMON, 10);
        flavors2.put(Flavor.VANILLA, 10);
        washington.getStorage().setFlavors(flavors2);

        EnumMap<Topping, Integer> toppings2 = new EnumMap<>(Topping.class);
        toppings2.put(Topping.WHITE_CHOCOLATE, 10);
        toppings2.put(Topping.MNMS, 10);
        toppings2.put(Topping.MILK_CHOCOLATE, 10);
        toppings2.put(Topping.REESES_BUTTERCUP, 10);
        washington.getStorage().setToppings(toppings2);

        CookieRecipe customizedRecipe1 = new CookieRecipe("myCustomRecipe1", Mix.MIXED, Cooking.CHEWY);
        EnumMap<Dough, Integer> customDoughs1 = new EnumMap<>(Dough.class);
        EnumMap<Flavor, Integer> customFlavors1 = new EnumMap<>(Flavor.class);
        EnumMap<Topping, Integer> customToppings1 = new EnumMap<>(Topping.class);
        customDoughs1.put(Dough.OATMEAL,3);
        customDoughs1.put(Dough.CHOCOLATE,3);
        customDoughs1.put(Dough.PEANUT_BUTTER,3);
        customDoughs1.put(Dough.PLAIN,3);
        customFlavors1.put(Flavor.CHILI, 3);
        customFlavors1.put(Flavor.VANILLA, 3);
        customFlavors1.put(Flavor.CINNAMON, 3);
        customToppings1.put(Topping.WHITE_CHOCOLATE, 3);
        customToppings1.put(Topping.MNMS, 3);
        customToppings1.put(Topping.MILK_CHOCOLATE, 3);
        customizedRecipe1.setDoughs(customDoughs1);
        customizedRecipe1.setFlavors(customFlavors1);
        customizedRecipe1.setToppings(customToppings1);

        CookieRecipe customizedRecipe2 = new CookieRecipe("myCustomRecipe2", Mix.MIXED, Cooking.CHEWY);
        EnumMap<Dough, Integer> customDoughs2 = new EnumMap<>(Dough.class);
        EnumMap<Flavor, Integer> customFlavors2 = new EnumMap<>(Flavor.class);
        EnumMap<Topping, Integer> customToppings2 = new EnumMap<>(Topping.class);
        customDoughs2.put(Dough.OATMEAL,6);
        customDoughs2.put(Dough.CHOCOLATE,6);
        customDoughs2.put(Dough.PEANUT_BUTTER,6);
        customDoughs2.put(Dough.PLAIN,6);
        customFlavors2.put(Flavor.CHILI, 6);
        customFlavors2.put(Flavor.VANILLA, 6);
        customFlavors2.put(Flavor.CINNAMON, 6);
        customToppings2.put(Topping.WHITE_CHOCOLATE, 6);
        customToppings2.put(Topping.MNMS, 7);
        customToppings2.put(Topping.MILK_CHOCOLATE, 6);
        customizedRecipe2.setDoughs(customDoughs2);
        customizedRecipe2.setFlavors(customFlavors2);
        customizedRecipe2.setToppings(customToppings2);

        order2.addOrderLine(customizedRecipe1, 1);
        order2.addOrderLine(customizedRecipe2,1);

        assertTrue(washington.canDoTheOrder(order2));

        CookieRecipe customizedRecipe3 = new CookieRecipe("myCustomRecipe3", Mix.MIXED, Cooking.CHEWY);
        EnumMap<Dough, Integer> customDoughs3 = new EnumMap<>(Dough.class);
        EnumMap<Flavor, Integer> customFlavors3 = new EnumMap<>(Flavor.class);
        EnumMap<Topping, Integer> customToppings3 = new EnumMap<>(Topping.class);
        customDoughs3.put(Dough.OATMEAL,1);
        customDoughs3.put(Dough.CHOCOLATE,1);
        customDoughs3.put(Dough.PEANUT_BUTTER,1);
        customDoughs3.put(Dough.PLAIN,1);
        customFlavors3.put(Flavor.CHILI, 1);
        customFlavors3.put(Flavor.VANILLA, 1);
        customFlavors3.put(Flavor.CINNAMON, 1);
        customToppings3.put(Topping.WHITE_CHOCOLATE, 1);
        customToppings3.put(Topping.MNMS, 1); // this brings the total number of  needed mnms toppings to 11, but the washington shop only has 10 in stock
        customToppings3.put(Topping.MILK_CHOCOLATE, 1);
        customizedRecipe3.setDoughs(customDoughs3);
        customizedRecipe3.setFlavors(customFlavors3);
        customizedRecipe3.setToppings(customToppings3);

        order2.addOrderLine(customizedRecipe3, 1);
        assertFalse(washington.canDoTheOrder(order2));
    }


    @Test
    public void consumeIngredientsOfTheOrderTest(){
        newYork.consumeIngredientsOfTheOrder(order);
        assertEquals(98, newYork.getStorage().getDoughs().get(Dough.CHOCOLATE));
        assertEquals( 96, newYork.getStorage().getDoughs().get(Dough.PLAIN));
        assertEquals(90, newYork.getStorage().getDoughs().get(Dough.OATMEAL));
        assertEquals(100, newYork.getStorage().getDoughs().get(Dough.PEANUT_BUTTER));
        assertEquals(95, newYork.getStorage().getFlavors().get(Flavor.VANILLA));
        assertEquals(95, newYork.getStorage().getFlavors().get(Flavor.CINNAMON));
        assertEquals(96, newYork.getStorage().getFlavors().get(Flavor.CHILI));
        assertEquals(98, newYork.getStorage().getToppings().get(Topping.MILK_CHOCOLATE));
        assertEquals(89, newYork.getStorage().getToppings().get(Topping.MNMS));
        assertEquals(96, newYork.getStorage().getToppings().get(Topping.WHITE_CHOCOLATE));
        assertEquals(99, newYork.getStorage().getToppings().get(Topping.REESES_BUTTERCUP));

        assertEquals(100, philadelphia.getStorage().getDoughs().get(Dough.CHOCOLATE));
        assertEquals(100, philadelphia.getStorage().getDoughs().get(Dough.PLAIN));
        assertEquals(100, philadelphia.getStorage().getDoughs().get(Dough.OATMEAL));
        assertEquals(100, philadelphia.getStorage().getDoughs().get(Dough.PEANUT_BUTTER));
        assertEquals(100, philadelphia.getStorage().getFlavors().get(Flavor.VANILLA));
        assertEquals(100, philadelphia.getStorage().getFlavors().get(Flavor.CINNAMON));
        assertEquals(100, philadelphia.getStorage().getFlavors().get(Flavor.CHILI));
        assertEquals(100, philadelphia.getStorage().getToppings().get(Topping.MILK_CHOCOLATE));
        assertEquals(100, philadelphia.getStorage().getToppings().get(Topping.MNMS));
        assertEquals(100, philadelphia.getStorage().getToppings().get(Topping.WHITE_CHOCOLATE));
        assertEquals(100, philadelphia.getStorage().getToppings().get(Topping.REESES_BUTTERCUP));
    }

    @Test
    public void isPickUpTimeAvailableTest(){
        Client client = new Client();
        TheCookieFactory tfc = new TheCookieFactory();
        Order order0 = new Order(new Client());
        client.setOrder(order0);
        order0.addOrderLine(tfc.getRecipes().get(0), 1);
        order0.pickShop(tfc.getShops().get(0));
        assertTrue(tfc.getShops().get(0).isPickUpTimeAvailable(Calculator.getTimeCalendarMidnightOfTheDay(new Date()).getTime().getTime(),LocalTime.of(11,0)));
    }

    @Test
    public void validatePickUpDayTimeTest(){
        Client client = new Client();
        TheCookieFactory tfc = new TheCookieFactory();
        Order order0 = new Order(new Client());
        client.setOrder(order0);
        order0.addOrderLine(tfc.getRecipes().get(0), 1);
        order0.pickShop(tfc.getShops().get(0));
        order0.pickUpDayTime(new Date(),LocalTime.of(11,0));
        assertTrue(order0.getShop().validatePickUpDayTime(order0.getPickUpDay(),order0.getPickUpTime()));
    }

    @Test
    public void ordersFromTheLast30DaysTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Order order = new Order(new Client());
        order.addOrderLine(tfc.getRecipes().get(0), 1);
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());

        Order order2 = new Order(new Client());
        order2.addOrderLine(tfc.getRecipes().get(1), 1);
        order2.setPickUpTime(LocalTime.of(11,0));
        order2.setPickUpDay(new Date());

        Order order3 = new Order(new Client());
        order3.addOrderLine(tfc.getRecipes().get(1), 1);
        order3.setPickUpTime(LocalTime.of(11,0));
        order3.setPickUpDay(new Date());

        order.purchase();
        order2.purchase();
        order3.purchase();

        tfc.getShops().get(0).addPurchasedOrder(order);
        tfc.getShops().get(0).addPurchasedOrder(order2);
        tfc.getShops().get(0).addPurchasedOrder(order3);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);

        assertEquals(orderList, tfc.getShops().get(0).ordersFromTheLast30Days());
    }

    @Test
    public void setAndGetMostPopularCookieTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Order order = new Order(new Client());
        order.addOrderLine(tfc.getRecipes().get(0), 1);
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());

        Order order2 = new Order(new Client());
        order2.addOrderLine(tfc.getRecipes().get(1), 1);
        order2.setPickUpTime(LocalTime.of(11,0));
        order2.setPickUpDay(new Date());

        Order order3 = new Order(new Client());
        order3.addOrderLine(tfc.getRecipes().get(1), 1);
        order3.setPickUpTime(LocalTime.of(11,0));
        order3.setPickUpDay(new Date());

        order.purchase();
        order2.purchase();
        order3.purchase();

        tfc.getShops().get(0).addPurchasedOrder(order);
        tfc.getShops().get(0).addPurchasedOrder(order2);
        tfc.getShops().get(0).addPurchasedOrder(order3);

        assertEquals(tfc.getRecipes().get(1), tfc.getShops().get(0).setGetMostPopularCookie());
    }

    @Test
    public void scheduleTimeSlotTest(){
        TheCookieFactory tfc = new TheCookieFactory();
        Date date = Calculator.getTimeCalendarMidnightOfTheDay(new Date()).getTime();
        tfc.getShops().get(0).scheduleTimeSlot(date,LocalTime.of(10,0));
        assertFalse(tfc.getShops().get(0).getSchedule().get(date.getTime()).isTimeSlotAvailable(LocalTime.of(10,0)));
    }

}
