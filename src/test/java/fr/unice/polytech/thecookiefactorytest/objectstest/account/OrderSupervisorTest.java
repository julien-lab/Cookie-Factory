package fr.unice.polytech.thecookiefactorytest.objectstest.account;

import fr.unice.polytech.lastminutemarcel.LastMinuteMarcel;
import fr.unice.polytech.lastminutemarcel.objects.Ride;
import fr.unice.polytech.lastminutemarcel.objects.Rider;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import fr.unice.polytech.thecookiefactory.objects.account.Cashier;
import fr.unice.polytech.thecookiefactory.objects.account.Cook;
import fr.unice.polytech.thecookiefactory.objects.account.OrderSupervisor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static fr.unice.polytech.lastminutemarcel.LastMinuteMarcel.FIXED_DELIVERY_FEE;
import static fr.unice.polytech.lastminutemarcel.LastMinuteMarcel.findRide;
import static org.junit.jupiter.api.Assertions.*;

public class OrderSupervisorTest {

    Shop shop = new Shop("Shop");
    OrderSupervisor supervisor;
    Order order1;
    Cook cook;
    TheCookieFactory tcf = new TheCookieFactory();
    LastMinuteMarcel lmm = new LastMinuteMarcel();

    @BeforeEach
    void init(){
        supervisor = new OrderSupervisor(shop);
        order1 = new Order(null);
        cook = new Cook(shop);
        shop.setCook(cook);
    }

    @Test
    void testOrdersToPrepareIsEmpty(){
        assertTrue(supervisor.getOrdersToPrepare().isEmpty());
    }

    @Test
    void testOrdersInPreparationIsEmpty(){
        assertTrue(supervisor.getOrdersInPreparation().isEmpty());
    }

    @Test
    void testOneAddOrderToPrepare(){
        supervisor.addOrderToPrepare(order1);
        assertTrue(supervisor.getOrdersToPrepare().contains(order1));
    }

    @Test
    void testMultipleAddOrderToPrepare(){
        Order order2 = new Order(null);
        Order order3 = new Order(null);
        supervisor.addOrderToPrepare(order1);
        supervisor.addOrderToPrepare(order2);
        supervisor.addOrderToPrepare(order3);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        assertTrue(supervisor.getOrdersToPrepare().containsAll(orders));
    }

    @Test
    void testSendCookiesOfAllOrderlinesOfTheOrder(){
        CookieRecipe recipe1 = tcf.getRecipeByName("Oatmeal / Vanilla");
        CookieRecipe recipe2= tcf.getRecipeByName("Oatmeal / Cinnamon");
        CookieRecipe recipe3= tcf.getRecipeByName("Plain / Chili");

        order1.addOrderLine(recipe1, 5);
        order1.addOrderLine(recipe2, 10);
        order1.addOrderLine(recipe3, 15);
        supervisor.sendCookiesOfAllOrderlinesOfTheOrder(order1);
        assertTrue(supervisor.getOrdersInPreparation().contains(order1));
        assertTrue(cook.getCookiesToMake().containsKey(recipe1));
        assertTrue(cook.getCookiesToMake().containsKey(recipe2));
        assertTrue(cook.getCookiesToMake().containsKey(recipe3));
    }

    @Test
    void testSendCookiesOfAllOrdersToBePrepared(){
        CookieRecipe recipe1 = tcf.getRecipeByName("Oatmeal / Vanilla");
        CookieRecipe recipe2= tcf.getRecipeByName("Oatmeal / Cinnamon");
        CookieRecipe recipe3= tcf.getRecipeByName("Plain / Chili");
        order1.addOrderLine(recipe1, 5);
        order1.addOrderLine(recipe2, 10);
        order1.addOrderLine(recipe3, 15);
        supervisor.addOrderToPrepare(order1);
        supervisor.sendCookiesOfAllOrdersToBePrepared();
        assertTrue(supervisor.getOrdersToPrepare().isEmpty());
        assertTrue(supervisor.getOrdersInPreparation().contains(order1));
        assertTrue(cook.getCookiesToMake().containsKey(recipe1));
        assertTrue(cook.getCookiesToMake().containsKey(recipe2));
        assertTrue(cook.getCookiesToMake().containsKey(recipe3));
    }

    @Test
    void testSendCookiesOfTheOldestOrder(){
        CookieRecipe recipe1 = tcf.getRecipeByName("Oatmeal / Vanilla");
        CookieRecipe recipe2= tcf.getRecipeByName("Oatmeal / Cinnamon");
        CookieRecipe recipe3= tcf.getRecipeByName("Plain / Chili");
        order1.addOrderLine(recipe1, 5);
        order1.addOrderLine(recipe2, 10);
        order1.addOrderLine(recipe3, 15);
        Order order2 = new Order(null);
        Order order3 = new Order(null);

        supervisor.addOrderToPrepare(order1);
        supervisor.addOrderToPrepare(order2);
        supervisor.addOrderToPrepare(order3);

        supervisor.sendCookiesOfTheOldestOrder();

        assertTrue(supervisor.getOrdersInPreparation().contains(order1));
        assertTrue(cook.getCookiesToMake().containsKey(recipe1));
        assertTrue(cook.getCookiesToMake().containsKey(recipe2));
        assertTrue(cook.getCookiesToMake().containsKey(recipe3));
    }

    @Test
    void testOneSendOrdersReadyToCashier(){
        Cashier cashier = new Cashier(shop);
        shop.setCashier(cashier);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);

        supervisor.sendOrdersReadyToCashier(orders);
        assertTrue(cashier.orderIsReady(order1));
        assertFalse(supervisor.getOrdersToPrepare().contains(order1));
    }

    @Test
    void testMultipleSendOrdersReadyToCashier(){
        Order order2 = new Order(null);
        Order order3 = new Order(null);
        Cashier cashier = new Cashier(shop);
        shop.setCashier(cashier);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        supervisor.sendOrdersReadyToCashier(orders);
        assertTrue(cashier.orderIsReady(order1));
        assertTrue(cashier.orderIsReady(order2));
        assertTrue(cashier.orderIsReady(order3));
        assertFalse(supervisor.getOrdersToPrepare().containsAll(orders));
    }

    @Test
    public void testEntrustToLastMinuteMarcelRegular(){

        order1.addOrderLine(tcf.getRecipes().get(0), 1);
        order1.addOrderLine(tcf.getRecipes().get(1), 2);

        Assertions.assertTrue(supervisor.entrustToLastMinuteMarcel(order1, false));
        Ride ride = findRide(order1);
        Rider rider = ride.getRider();
        Assertions.assertFalse(order1.isLateLastMinuteMarcelStatusRequest());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(FIXED_DELIVERY_FEE + 4.08, order1.getPriceAfterTax());
        Assertions.assertEquals(ride, rider.getCurrentRide());
        Assertions.assertEquals(Rider.Status.UNAVAILABLE, rider.getRiderStatus());
    }

    @Test
    public void testEntrustToLastMinuteMarcelLate(){

        order1.addOrderLine(tcf.getRecipes().get(0), 1);
        order1.addOrderLine(tcf.getRecipes().get(1), 2);

        Assertions.assertTrue(supervisor.entrustToLastMinuteMarcel(order1, true));
        Ride ride = findRide(order1);
        Rider rider = ride.getRider();
        Assertions.assertTrue(order1.isLateLastMinuteMarcelStatusRequest());
        Assertions.assertEquals((1.5 * FIXED_DELIVERY_FEE) + 3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals((1.5 * FIXED_DELIVERY_FEE) + 4.08, order1.getPriceAfterTax());
        Assertions.assertEquals(ride, rider.getCurrentRide());
        Assertions.assertEquals(Rider.Status.UNAVAILABLE, rider.getRiderStatus());
    }

    @Test
    public void testNoAvailableEntrustToLastMinuteMarcel(){
        order1.addOrderLine(tcf.getRecipes().get(0), 1);
        order1.addOrderLine(tcf.getRecipes().get(1), 2);
        for(int i = 0 ; i < LastMinuteMarcel.getRiders().size() ; i++){
            Rider busyRider = LastMinuteMarcel.findAvailableRider();
            busyRider.setRiderStatus(Rider.Status.UNAVAILABLE);
        }

        Assertions.assertFalse(supervisor.entrustToLastMinuteMarcel(order1, false));
        Assertions.assertNull(findRide(order1));
        Assertions.assertEquals(Order.LastMinuteMarcelStatus.NO_FREE_RIDERS, order1.getLastMinuteMarcelStatus());

        Assertions.assertEquals(3.4, order1.getPriceBeforeTax());
        Assertions.assertEquals(4.08, order1.getPriceAfterTax());
    }

    @Test
    public void testNotifyLastMinuteMarcelOrderReady(){
        order1.addOrderLine(tcf.getRecipes().get(0), 1);
        order1.addOrderLine(tcf.getRecipes().get(1), 2);
        supervisor.entrustToLastMinuteMarcel(order1, true);
        supervisor.notifyLastMinuteMarcelOrderReady(order1);
        Assertions.assertEquals(Ride.Status.WAITING_FOR_RIDER, findRide(order1).getRideStatus());
    }

    @Test
    public void testWrongOrderNotifyLastMinuteMarcelOrderReady(){
        order1.addOrderLine(tcf.getRecipes().get(0), 1);
        order1.addOrderLine(tcf.getRecipes().get(1), 2);
        supervisor.notifyLastMinuteMarcelOrderReady(order1);
        Assertions.assertNull(findRide(order1));
        Assertions.assertEquals(Order.LastMinuteMarcelStatus.NOT_ENTRUSTED, order1.getLastMinuteMarcelStatus());
    }

    @Test
    public void getTheOldestOrderToPrepareTest(){
        Order order = new Order(new Client());
        Order order2 = new Order(new Client());
        Order order3 = new Order(new Client());

        TheCookieFactory tfc = new TheCookieFactory();
        CookieRecipe cookie1 = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.MNMS)), Mix.MIXED, Cooking.CHEWY);
        CookieRecipe cookie2 = new CookieRecipe("Oatmeal / Cinnamon", Dough.OATMEAL, Flavor.CINNAMON, new ArrayList<>(Collections.singletonList(Topping.MNMS)), Mix.TOPPED, Cooking.CRUNCHY);
        CookieRecipe cookie3 = new CookieRecipe("Plain / Vanilla", Dough.PLAIN, Flavor.VANILLA, new ArrayList<>(Collections.singletonList(Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);

        order.addOrderLine(cookie1, 10);
        order2.addOrderLine(cookie2, 15);
        order3.addOrderLine(cookie3, 20);

        tfc.getShops().get(0).getOrderSupervisor().addOrderToPrepare(order);
        tfc.getShops().get(0).getOrderSupervisor().addOrderToPrepare(order2);
        tfc.getShops().get(0).getOrderSupervisor().addOrderToPrepare(order3);
        assertEquals(order,tfc.getShops().get(0).getOrderSupervisor().getTheOldestOrderToPrepare());
    }

    @Test
    public void getOrdersReadyTest(){
        Order order = new Order(new Client());
        Order order2 = new Order(new Client());
        Order order3 = new Order(new Client());

        TheCookieFactory tfc = new TheCookieFactory();
        CookieRecipe cookie1 = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.MNMS)), Mix.MIXED, Cooking.CHEWY);
        CookieRecipe cookie2 = new CookieRecipe("Peanut Butter / Cinnamon", Dough.PEANUT_BUTTER, Flavor.CINNAMON, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CRUNCHY);
        CookieRecipe cookie3 = new CookieRecipe("Chocolate / Vanilla", Dough.CHOCOLATE, Flavor.VANILLA, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);

        order.addOrderLine(cookie1, 10);
        order2.addOrderLine(cookie2, 15);
        order3.addOrderLine(cookie3, 20);

        tfc.getShops().get(0).getOrderSupervisor().sendCookiesOfAllOrderlinesOfTheOrder(order);
        tfc.getShops().get(0).getOrderSupervisor().sendCookiesOfAllOrderlinesOfTheOrder(order2);
        tfc.getShops().get(0).getOrderSupervisor().sendCookiesOfAllOrderlinesOfTheOrder(order3);

        tfc.getShops().get(0).getCook().bakeAllCookies();

        List<Order> ordersReady = new ArrayList<>();
        ordersReady.add(order);
        ordersReady.add(order2);
        ordersReady.add(order3);

        order.purchase();
        order2.purchase();
        order3.purchase();

        assertEquals(ordersReady,tfc.getShops().get(0).getOrderSupervisor().getOrdersReady());
    }

}









