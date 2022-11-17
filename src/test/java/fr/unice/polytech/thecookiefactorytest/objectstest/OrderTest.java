package fr.unice.polytech.thecookiefactorytest.objectstest;

import fr.unice.polytech.thecookiefactory.ingredients.Dough;
import fr.unice.polytech.thecookiefactory.ingredients.Flavor;
import fr.unice.polytech.thecookiefactory.ingredients.Topping;
import fr.unice.polytech.thecookiefactory.objects.*;
import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {


    private Order order;
    private Shop shop;


    private TheCookieFactory tfc = new TheCookieFactory();
    private Client client = new Client();
    private Order order1;
    private Shop shop1;

    private final LocalTime openingTime = LocalTime.of(10, 0);
    private final LocalTime closingTime = LocalTime.of(19, 0);

    @Before public void initialize() {
        TheCookieFactory tcf = new TheCookieFactory();
        client = new Client();
        order = new Order(client);
        order.setClient(client);

    }

    @Test
    public void addOrderLine() {
        shop1 = new Shop("Saint Philippe", openingTime, closingTime, 0.2);
        order1 = new Order(null,new Date(26 / 10 / 20), new ArrayList<OrderLine>(), shop1);

        order1.addOrderLine(tfc.getRecipes().get(0), 4);
        client.setOrder(order1);
        assertEquals(1,order1.getOrderLines().size());
        assertEquals(tfc.getRecipes().get(0),order1.getOrderLines().get(0).getCookieRecipe());
        assertEquals(4,order1.getOrderLines().get(0).getQuantity());
    }

    @Test
    public void orderTest() {
        shop1 = new Shop("Saint Philippe", openingTime, closingTime, 0.2);
        order1 = new Order(null,new Date(26 / 10 / 20), new ArrayList<OrderLine>(), shop1);

        assertEquals(0,order1.getOrderLines().size());
        assertEquals("Saint Philippe",order1.getShop().getShopName());
        assertEquals(0.0,order1.getPriceBeforeTax());
    }


    @Test
    public void calculateTotalPriceOfOrderlinesTest() {
        Order order = new Order(null);
        assertEquals(0., order.getPriceBeforeTax());
        order.addOrderLine(tfc.getRecipes().get(0), 2);
        order.addOrderLine(tfc.getRecipes().get(1), 5);
        order.addOrderLine(tfc.getRecipes().get(2), 4);
        order.addOrderLine(tfc.getRecipes().get(3), 1);
        assertEquals(12.0, order.getPriceBeforeTax());
    }


    @Test
    public void applyTaxOnPriceTest() {
        Order order = new Order(client);
        shop1 = new Shop("Saint Philippe", openingTime, closingTime, 0.2);
        order.setShop(shop1);
        assertEquals(0., order.getPriceBeforeTax());
        order.addOrderLine(tfc.getRecipes().get(0), 2);
        order.addOrderLine(tfc.getRecipes().get(1), 5);
        order.addOrderLine(tfc.getRecipes().get(2), 4);
        order.addOrderLine(tfc.getRecipes().get(3), 1);
        assertEquals(12.0, order.getPriceBeforeTax());
        assertEquals(14.4, order.getPriceAfterTax());
        order.getShop().setPercentageTax(0.4);
        order.applyTaxOnPrice();
        assertEquals(16.8, order.getPriceAfterTax());

    }

    @Test
    public void applyLoyaltyDiscountTest() {
        Client c = new Client(new ClientAccount("TEST"));
        c.getAccount().setNumberCookiesPurchased(30);
        c.getAccount().joinLoyaltyProgram();
        Order order = new Order(c);

        order.addOrderLine(tfc.getRecipes().get(0), 2);
        order.addOrderLine(tfc.getRecipes().get(1), 5);
        order.addOrderLine(tfc.getRecipes().get(2), 4);
        order.addOrderLine(tfc.getRecipes().get(3), 1);

        assertEquals(12.0, order.getPriceBeforeTax());
        assertFalse(order.isLoyaltyDiscountUsed());
        order.applyLoyaltyDiscount();
        assertEquals(10.8, order.getPriceBeforeTax());
        assertTrue(order.isLoyaltyDiscountUsed());
        order.applyLoyaltyDiscount();
        assertTrue(order.isLoyaltyDiscountUsed());
        assertEquals(10.8, order.getPriceBeforeTax());
    }

    @Test
    public void removeOrderLineTest() {
        Order order = new Order(null);
        order.addOrderLine(tfc.getRecipes().get(0), 2);
        order.addOrderLine(tfc.getRecipes().get(1), 5);
        order.addOrderLine(tfc.getRecipes().get(2), 4);
        assertEquals(3,order.getOrderLines().size());
        assertEquals("Chocolate / Chili",order.getOrderLines().get(0).getCookieRecipe().getName());
        order.removeOrderLine("Chocolate / Chili");
        assertEquals(2,order.getOrderLines().size());
        assertEquals("Oatmeal / Cinnamon",order.getOrderLines().get(0).getCookieRecipe().getName());
        order.removeOrderLine("INCONNU");
        assertEquals(2,order.getOrderLines().size());
    }

    @Test
    public void purchaseTest() throws ParseException {
        client = new Client();
        shop1 = new Shop("Saint Philippe", openingTime, closingTime, 0.2);
        order = new Order(client,new Date(26 / 10 / 20), new ArrayList<OrderLine>(), shop1);
        order.addOrderLine(tfc.getRecipes().get(0), 2);
        order.addOrderLine(tfc.getRecipes().get(1), 5);
        order.addOrderLine(tfc.getRecipes().get(2), 4);
        order.setPickUpTime(LocalTime.of(13,0));
        order.setPickUpDay(new Date());

        order.purchase();
        assertTrue(order.isPaymentDone());
        assertEquals(order,order.getClient().getOrder());
        assertEquals(order,order.getShop().getPurchasedOrders().get(0));
        assertEquals(order.getShop().getOrderSupervisor().getOrdersToPrepare().get(0),order);
        assertEquals(98,order.getShop().getStorage().getDoughs().get(Dough.CHOCOLATE));
        assertEquals(95,order.getShop().getStorage().getDoughs().get(Dough.OATMEAL));
        assertEquals(96,order.getShop().getStorage().getDoughs().get(Dough.PLAIN));
        assertEquals(98,order.getShop().getStorage().getFlavors().get(Flavor.CHILI));
        assertEquals(95,order.getShop().getStorage().getFlavors().get(Flavor.CINNAMON));
        assertEquals(96,order.getShop().getStorage().getFlavors().get(Flavor.VANILLA));
        assertEquals(98,order.getShop().getStorage().getToppings().get(Topping.MILK_CHOCOLATE));
        assertEquals(95,order.getShop().getStorage().getToppings().get(Topping.MNMS));
        assertEquals(96,order.getShop().getStorage().getToppings().get(Topping.WHITE_CHOCOLATE));
    }


}
