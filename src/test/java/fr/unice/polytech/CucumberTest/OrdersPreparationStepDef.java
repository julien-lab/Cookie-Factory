package fr.unice.polytech.cucumbertest;


import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrdersPreparationStepDef implements En{

    private TheCookieFactory tfc;
    private Order order;
    private Order order2;
    private Order order3;
    private HashMap<CookieRecipe,Integer> cookiesToMake;
    private Client client;
    private Client client2;
    private Client client3;

    private Shop shop1;

    public OrdersPreparationStepDef() {

        Given("^3 clients orders$", () -> {
            tfc = new TheCookieFactory();
            client = new Client();
            client2 = new Client();
            client3 = new Client();

            shop1= tfc.getShops().get(0);

            order = new Order(client);
            order.addOrderLine(tfc.getRecipes().get(0), 1);
            order.setPickUpTime(LocalTime.of(11,0));
            order.setPickUpDay(new Date());

            order2 = new Order(client2);
            client2.setOrder(order2);
            order2.addOrderLine(tfc.getRecipes().get(1), 2);
            order2.setPickUpTime(LocalTime.of(12,0));
            order2.setPickUpDay(new Date());

            order3 = new Order(client3);
            client3.setOrder(order3);
            order3.addOrderLine(tfc.getRecipes().get(2), 3);
            order3.setPickUpTime(LocalTime.of(13,0));
            order3.setPickUpDay(new Date());

            order.pickShop(shop1);
            order2.pickShop(shop1);
            order3.pickShop(shop1);
        });

        When("^Order supervisor send cookies to do to the cook$", () -> {
            order.purchase();
            order2.purchase();
            order3.purchase();

            shop1.getOrderSupervisor().sendCookiesOfAllOrdersToBePrepared();

        });

        Then("^Cook get cookies to do$", () -> {
            assertEquals(Integer.valueOf(1), shop1.getCook().getCookiesToMake().get(tfc.getRecipes().get(0)));
            assertEquals(Integer.valueOf(2), shop1.getCook().getCookiesToMake().get(tfc.getRecipes().get(1)));
            assertEquals(Integer.valueOf(3), shop1.getCook().getCookiesToMake().get(tfc.getRecipes().get(2)));
            assertEquals(0, shop1.getOrderSupervisor().getOrdersToPrepare().size());
            assertEquals(3, shop1.getOrderSupervisor().getOrdersInPreparation().size());
            assertEquals(0, shop1.getCook().getCookiesReady().size());
        });

        When("^Cook bake cookies to prepare$", () -> {
            order.purchase();
            order2.purchase();
            order3.purchase();

            shop1.getOrderSupervisor().sendCookiesOfAllOrdersToBePrepared();
            shop1.getCook().bakeAllCookies();
        });

        Then("^Cookies are ready$", () -> {
            assertEquals(Integer.valueOf(1), shop1.getCook().getCookiesReady().get(tfc.getRecipes().get(0)));
            assertEquals(Integer.valueOf(2), shop1.getCook().getCookiesReady().get(tfc.getRecipes().get(1)));
            assertEquals(Integer.valueOf(3), shop1.getCook().getCookiesReady().get(tfc.getRecipes().get(2)));
            assertEquals(0, shop1.getOrderSupervisor().getOrdersToPrepare().size());
            assertEquals(3, shop1.getOrderSupervisor().getOrdersInPreparation().size());
            assertEquals(3, shop1.getCook().getCookiesReady().size());
        });

        When("^Order Supervisor check order finish and send them to cashier$", () -> {
            order.purchase();
            order2.purchase();
            order3.purchase();

            shop1.getOrderSupervisor().sendCookiesOfAllOrdersToBePrepared();
            shop1.getCook().bakeAllCookies();
            List<Order> ordersReady =  shop1.getOrderSupervisor().getOrdersReady();
            shop1.getOrderSupervisor().sendOrdersReadyToCashier(ordersReady);
        });

        Then("^Cashier get orders ready to be take by the client$", () -> {
            assertEquals(0, shop1.getOrderSupervisor().getOrdersToPrepare().size());
            assertEquals(0, shop1.getOrderSupervisor().getOrdersInPreparation().size());

            assertEquals(Integer.valueOf(0), shop1.getCook().getCookiesReady().get(tfc.getRecipes().get(0)));
            assertEquals(Integer.valueOf(0), shop1.getCook().getCookiesReady().get(tfc.getRecipes().get(1)));
            assertEquals(Integer.valueOf(0), shop1.getCook().getCookiesReady().get(tfc.getRecipes().get(2)));

            assertEquals(3, shop1.getCashier().getOrdersReady().size());
            assertEquals(order, shop1.getCashier().getOrdersReady().get(0));
            assertEquals(order2, shop1.getCashier().getOrdersReady().get(1));
            assertEquals(order3, shop1.getCashier().getOrdersReady().get(2));
        });
    }

}
