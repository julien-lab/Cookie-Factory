package fr.unice.polytech.thecookiefactorytest.objectstest;

import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderLineTest {

    private final TheCookieFactory tfc = new TheCookieFactory();

    @Test
    public void orderLineTest(){
        OrderLine oL = new OrderLine(tfc.getRecipes().get(0),2);
        assertEquals(2.4,oL.getPrice());
    }

    @Test
    public void addCookieTest(){
        OrderLine oL = new OrderLine(tfc.getRecipes().get(0),2);
        oL.addCookie(2);
        assertEquals(4.8,oL.getPrice());
    }

    @Test
    public void deleteCookieTest(){
        OrderLine oL = new OrderLine(tfc.getRecipes().get(0),2);
        oL.deleteCookie(1);
        assertEquals(1.2,oL.getPrice());
        oL.deleteCookie(2);
        assertEquals(0,oL.getPrice());
    }

}
