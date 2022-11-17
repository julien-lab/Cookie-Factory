package fr.unice.polytech.thecookiefactorytest;

import fr.unice.polytech.thecookiefactory.Calculator;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;
import org.junit.Test;

import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

public class TheCookieFactoryTest {
    TheCookieFactory tfc;

    @Test
    public void createClientAccount(){
        tfc = new TheCookieFactory();
        assertEquals(0, tfc.getClientAccounts().size());
        tfc.createClientAccount("Titouan");
        assertEquals(1, tfc.getClientAccounts().size());
        assertEquals("Titouan", tfc.getClientAccounts().get(0).getAccountName());

    }


    @Test
    public void getClientAccount(){
        tfc = new TheCookieFactory();
        assertNull(tfc.getClientAccount("Titouan"));
        tfc.createClientAccount("Titouan");
        ClientAccount ca = tfc.getClientAccount("Titouan");
        assertEquals("Titouan" , ca.getAccountName());
    }


    @Test
    public void recipeAlreadyExist(){
        tfc = new TheCookieFactory();
        CookieRecipe newCr = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI,new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        assertTrue(tfc.recipeAlreadyExist(newCr));
        newCr = new CookieRecipe("NEW RECIPE", Dough.OATMEAL, Flavor.CHILI,new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        assertFalse(tfc.recipeAlreadyExist(newCr));
        newCr = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        assertFalse(tfc.recipeAlreadyExist(newCr));
        newCr = new CookieRecipe("NEW RECIPE", Dough.OATMEAL, Flavor.CHILI, new ArrayList<>(Arrays.asList(Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        assertFalse(tfc.recipeAlreadyExist(newCr));
    }

    @Test
    public void setMostPopularCookieOfTheCookieFactoryTest(){
        tfc = new TheCookieFactory();

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
        tfc.getShops().get(1).addPurchasedOrder(order2);
        tfc.getShops().get(2).addPurchasedOrder(order3);

        assertEquals(tfc.getRecipes().get(1), tfc.setMostPopularCookieOfTheCookieFactory());
    }

    @Test
    public void retrieveAllOrdersFromTheLast30DaysTest(){
        tfc = new TheCookieFactory();

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
        tfc.getShops().get(1).addPurchasedOrder(order2);
        tfc.getShops().get(2).addPurchasedOrder(order3);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);

        assertEquals(orderList, tfc.retrieveAllOrdersFromTheLast30Days());
    }

    @Test
    public void setDiscountsForCookiesBestOfTest(){

        Client client = new Client();
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
        tfc.setDiscountsForCookiesBestOf();
        Order order = new Order(new Client(),new Date(),
                Collections.singletonList(new OrderLine(tfc.getRecipes().get(5), 6)),
                tfc.getShops().get(0));
        double price = new OrderLine(tfc.getRecipes().get(5), 6).getPrice()*(1+ tfc.getShops().get(0).getPercentageTax());
        price = Calculator.round(price,2);
        assertNotEquals(order.getPriceAfterTax(),price , 0.0);
    }

}
