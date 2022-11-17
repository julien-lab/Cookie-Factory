package fr.unice.polytech.thecookiefactorytest.objectstest.account;

import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import fr.unice.polytech.thecookiefactory.objects.account.LocalManager;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalManagerTest {

    Shop shop = new Shop("Shop");
    LocalManager manager = new LocalManager(shop);

    @Test
    void testSetOpeningTime(){
        LocalTime time = LocalTime.of(10, 10);
        manager.setOpeningTime(time);
        assertEquals(time, shop.getOpeningTime());
    }

    @Test
    void testSetClosingTime(){
        LocalTime time = LocalTime.of(10, 10);
        manager.setClosingTime(time);
        assertEquals(time, shop.getClosingTime());
    }

    @Test
    void getStatisticsOnNumberOfCookieOrderedTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Client client = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        Shop shop1 = tfc.getShops().get(0);

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
        shop1.addPurchasedOrder(order3);

        Map<CookieRecipe, Integer> cookiesOrdered = new HashMap<>();
        cookiesOrdered.put(tfc.getRecipes().get(0),1);
        cookiesOrdered.put(tfc.getRecipes().get(1),2);
        cookiesOrdered.put(tfc.getRecipes().get(2),3);

        assertEquals(cookiesOrdered, shop1.getLocalManager().getStatisticsOnNumberOfCookieOrdered());
    }

    @Test
    void getStatisticsOnOrdersPickUpTimeTest(){
        TheCookieFactory tfc = new TheCookieFactory();

        Client client = new Client();
        Client client2 = new Client();
        Client client3 = new Client();

        Shop shop1 = tfc.getShops().get(0);

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
        shop1.addPurchasedOrder(order3);

        Map<LocalTime, Integer> mostPopularPickUpTime = new HashMap<>();
        mostPopularPickUpTime.put(LocalTime.of(11,0),2);
        mostPopularPickUpTime.put(LocalTime.of(12,0),1);

        assertEquals(mostPopularPickUpTime, shop1.getLocalManager().getStatisticsOnOrdersPickUpTime());
    }
}
