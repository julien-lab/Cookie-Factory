package fr.unice.polytech.thecookiefactorytest;

import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import fr.unice.polytech.thecookiefactory.Calculator;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorTest {

    @Test
    public void roundTest(){
        double a = 15.56565656,b = 78.98797,c = 456.3577,d = 32.21545,e = 9.253652,f = 9.10124,g = 4.0126,h = 9.00,i = 74.256;
        a = Calculator.round(a,2);
        b = Calculator.round(b,2);
        c = Calculator.round(c,2);
        d = Calculator.round(d,2);
        e = Calculator.round(e,2);
        f = Calculator.round(f,2);
        g = Calculator.round(g,2);
        h = Calculator.round(h,2);
        i = Calculator.round(i,2);
        assertEquals(15.57,a);
        assertEquals(78.99,b);
        assertEquals(456.36,c);
        assertEquals(32.22,d);
        assertEquals(9.25,e);
        assertEquals(9.10,f);
        assertEquals(4.01,g);
        assertEquals(9.00,h);
        assertEquals(74.26,i);
    }

    @Test
    public void getMostPopularShopTest(){
        List<Order> orderList = new ArrayList<>();
        Shop shop = new Shop("New York", LocalTime.of(8,30), LocalTime.of(19,30), 0.11);
        Order order;
        for (int i=0;i<60;i++){
            order = new Order(new Client());
            order.setShop(shop);orderList.add(order);
        }
        Shop shop2 = new Shop("San Francisco", LocalTime.of(9,30), LocalTime.of(20,30), 0.11);
        for (int i=0;i<50;i++){
            order = new Order(new Client());
            order.setShop(shop2);
            order.setShop(shop);orderList.add(order);
        }
        Shop shop3 = new Shop("Chicago", LocalTime.of(8,0), LocalTime.of(20,3), 0.11);
        for (int i=0;i<40;i++){
            order = new Order(new Client());
            order.setShop(shop3);
            order.setShop(shop);orderList.add(order);
        }
        Shop shop4 = new Shop("Los Angeles", LocalTime.of(7,30), LocalTime.of(19,0), 0.11);
        for (int i=0;i<30;i++){
            order = new Order(new Client());
            order.setShop(shop4);
            order.setShop(shop);orderList.add(order);
        }
        Shop shop5 = new Shop("Philadelphia", LocalTime.of(10,0), LocalTime.of(20,30), 0.11);
        for (int i=0;i<20;i++){
            order = new Order(new Client());
            order.setShop(shop5);
            order.setShop(shop);orderList.add(order);
        }
        Shop shop6 = new Shop("Washington", LocalTime.of(9,0), LocalTime.of(18,30), 0.11);
        for (int i=0;i<10;i++){
            order = new Order(new Client());
            order.setShop(shop6);
            order.setShop(shop);orderList.add(order);
        }
        assertEquals("New York", Calculator.getMostPopularShop(orderList).getShopName());
    }

}
