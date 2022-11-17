package fr.unice.polytech.thecookiefactorytest.objectstest.account;


import fr.unice.polytech.thecookiefactory.objects.account.Cashier;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CashierTest {

    private Cashier cashier;
    private Shop shop = new Shop("Shop");

    @BeforeEach
    void init(){
        cashier = new Cashier(shop);
    }

    @Test
    void orderReadyIsEmpty(){
        assertTrue(cashier.getOrdersReady().isEmpty());
    }

    @Test
    void orderReadyIsNotEmpty(){
        Order order = new Order(null);
        cashier.addOrderReady(order);
        assertFalse(cashier.getOrdersReady().isEmpty());
    }

    @Test
    void testAddOrderReady(){
        Order order = new Order(null);
        cashier.addOrderReady(order);
        assertEquals(order, cashier.getOrdersReady().get(0));
    }

    @Test
    void testMultipleAddOrderReady(){
        Order order1 = new Order(null);
        Order order2 = new Order(null);
        Order order3 = new Order(null);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        cashier.addOrderReady(order1);
        cashier.addOrderReady(order2);
        cashier.addOrderReady(order3);
        assertTrue(cashier.getOrdersReady().containsAll(orders));
    }

    @Test
    void testOrderIsReady(){
        Order order = new Order(null);
        cashier.addOrderReady(order);
        assertTrue(cashier.orderIsReady(order));
    }

    @Test
    void testOrderIsNotReady(){
        Order order = new Order(null);
        assertFalse(cashier.orderIsReady(order));
    }

    @Test
    void testGiveOrder(){
        Order order = new Order(null);
        cashier.addOrderReady(order);
        cashier.giveOrder(order);
        assertTrue(order.hasBeenWithdrawn());
        assertFalse(cashier.orderIsReady(order));
    }

    @Test
    void testOrderNotGiven(){
        Order order = new Order(null);
        cashier.addOrderReady(order);
        assertFalse(order.hasBeenWithdrawn());
        assertTrue(cashier.orderIsReady(order));
    }

}
