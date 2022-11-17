package fr.unice.polytech.thecookiefactorytest.objectstest.account;

import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import org.junit.Test;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NationalManagerTest {
    private TheCookieFactory cF ;

    @Test
    public void addNewCookieRecipeTest(){
        cF = new TheCookieFactory();
        assertEquals(7, cF.getRecipes().size());
        CookieRecipe newCr = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        cF.getNationalManager().addNewCookieRecipe(newCr);
        assertEquals(7, cF.getRecipes().size());
        newCr = new CookieRecipe("NEW RECIPE", Dough.OATMEAL, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        cF.getNationalManager().addNewCookieRecipe(newCr);
        assertEquals(8, cF.getRecipes().size());
    }


    @Test
    public void removeCookieRecipeTest(){
        cF = new TheCookieFactory();
        assertEquals(7, cF.getRecipes().size());
        CookieRecipe recipeNotInTCF = new CookieRecipe("Chocolalalalalalalalala", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        cF.getNationalManager().removeCookieRecipe(recipeNotInTCF);
        assertEquals(7, cF.getRecipes().size());
        assertEquals("Chocolate / Chili", cF.getRecipes().get(0).getName());
        cF.getNationalManager().removeCookieRecipe(cF.getRecipes().get(0));
        assertEquals("Oatmeal / Cinnamon", cF.getRecipes().get(0).getName());
        assertEquals(6, cF.getRecipes().size());
    }

    @Test
    public void getStatisticsOnNumberOfCookieOrderedTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Client client = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        Shop shop1 = tfc.getShops().get(0);
        Shop shop2 = tfc.getShops().get(1);

        Order order = new Order(client);
        order.addOrderLine(tfc.getRecipes().get(0), 1);

        Order order2 = new Order(client2);
        client2.setOrder(order2);
        order2.addOrderLine(tfc.getRecipes().get(1), 2);

        Order order3 = new Order(client3);
        client3.setOrder(order3);
        order3.addOrderLine(tfc.getRecipes().get(2), 3);

        shop1.addPurchasedOrder(order);
        shop1.addPurchasedOrder(order2);
        shop2.addPurchasedOrder(order3);

        Map<CookieRecipe, Integer> cookiesOrdered = new HashMap<>();
        cookiesOrdered.put(tfc.getRecipes().get(0),1);
        cookiesOrdered.put(tfc.getRecipes().get(1),2);
        cookiesOrdered.put(tfc.getRecipes().get(2),3);

        assertEquals(cookiesOrdered, tfc.getNationalManager().getStatisticsOnNumberOfCookieOrdered());
    }

    @Test
    public void getStatisticsOnOrdersPickUpTimeTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Client client = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        Shop shop1 = tfc.getShops().get(0);
        Shop shop2 = tfc.getShops().get(1);

        Order order = new Order(client);
        order.addOrderLine(tfc.getRecipes().get(0), 1);
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());

        Order order2 = new Order(client2);
        client2.setOrder(order2);
        order2.addOrderLine(tfc.getRecipes().get(1), 2);
        order2.setPickUpTime(LocalTime.of(11,0));
        order2.setPickUpDay(new Date());

        Order order3 = new Order(client3);
        client3.setOrder(order3);
        order3.addOrderLine(tfc.getRecipes().get(2), 3);
        order3.setPickUpTime(LocalTime.of(12,0));
        order3.setPickUpDay(new Date());

        shop1.addPurchasedOrder(order);
        shop1.addPurchasedOrder(order2);
        shop2.addPurchasedOrder(order3);

        Map<LocalTime, Integer> mostPopularPickUpTime = new HashMap<>();
        mostPopularPickUpTime.put(LocalTime.of(11,0),2);
        mostPopularPickUpTime.put(LocalTime.of(12,0),1);

        assertEquals(mostPopularPickUpTime, tfc.getNationalManager().getStatisticsOnOrdersPickUpTime());
    }

    @Test
    public void getMostPopularShopTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Client client = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        Shop shop1 = tfc.getShops().get(0);
        Shop shop2 = tfc.getShops().get(1);

        Order order = new Order(client);
        order.addOrderLine(tfc.getRecipes().get(0), 1);
        order.setPickUpTime(LocalTime.of(11,0));
        order.setPickUpDay(new Date());
        order.setShop(shop1);

        Order order2 = new Order(client2);
        client2.setOrder(order2);
        order2.addOrderLine(tfc.getRecipes().get(1), 2);
        order2.setPickUpTime(LocalTime.of(11,0));
        order2.setPickUpDay(new Date());
        order2.setShop(shop1);

        Order order3 = new Order(client3);
        client3.setOrder(order3);
        order3.addOrderLine(tfc.getRecipes().get(2), 3);
        order3.setPickUpTime(LocalTime.of(12,0));
        order3.setPickUpDay(new Date());
        order3.setShop(shop2);

        shop1.addPurchasedOrder(order);
        shop1.addPurchasedOrder(order2);
        shop2.addPurchasedOrder(order3);

        assertEquals(shop1.getShopName(), tfc.getNationalManager().getMostPopularShop().getShopName());
    }

}