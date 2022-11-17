package fr.unice.polytech.thecookiefactorytest.objectstest.account;

import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;
import fr.unice.polytech.thecookiefactory.objects.account.Cook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CookTest {

    Cook cook;
    Shop shop = new Shop("Shop");
    TheCookieFactory tcf = new TheCookieFactory();

    @BeforeEach
    void init(){
        cook = new Cook(shop);
    }

    @Test
    void testCookiesToMakeIsEmpty(){
        assertTrue(cook.getCookiesToMake().isEmpty());
    }

    @Test
    void testCookiesReadyIsEmpty(){
        assertTrue(cook.getCookiesReady().isEmpty());
    }

    @Test
    void testOneAddCookiesToMake(){
        CookieRecipe cookie = tcf.getRecipeByName("Oatmeal / Vanilla");
        cook.addCookiesToMake(cookie, 10);
        assertFalse(cook.getCookiesToMake().isEmpty());
        assertTrue(cook.getCookiesToMake().containsKey(cookie));
    }

    @Test
    void testMultipleAddCookiesToMake(){
        CookieRecipe cookie1 = tcf.getRecipeByName("Oatmeal / Vanilla");
        CookieRecipe cookie2 = tcf.getRecipeByName("Oatmeal / Cinnamon");
        CookieRecipe cookie3 = tcf.getRecipeByName("Plain / Chili");
        ArrayList<CookieRecipe> cookies = new ArrayList<>();
        cookies.add(cookie1);
        cookies.add(cookie3);
        cookies.add(cookie2);
        cook.addCookiesToMake(cookie1, 10);
        cook.addCookiesToMake(cookie2, 15);
        cook.addCookiesToMake(cookie3, 14);
        assertTrue(cook.getCookiesToMake().containsKey(cookie1));
        assertTrue(cook.getCookiesToMake().containsKey(cookie2));
        assertTrue(cook.getCookiesToMake().containsKey(cookie3));
    }

    @Test
    public void testOneBakeAllCookies(){
        CookieRecipe cookie1 = tcf.getRecipeByName("Oatmeal / Vanilla");
        cook.addCookiesToMake(cookie1, 10);
        cook.bakeAllCookies();
        assertTrue(cook.getCookiesToMake().isEmpty());
        assertTrue(cook.getCookiesReady().containsKey(cookie1));
    }

    @Test
    public void testMultipleBakeAllCookies(){
        CookieRecipe cookie1 = tcf.getRecipeByName("Oatmeal / Vanilla");
        CookieRecipe cookie2 = tcf.getRecipeByName("Oatmeal / Cinnamon");
        CookieRecipe cookie3 = tcf.getRecipeByName("Plain / Chili");
        cook.addCookiesToMake(cookie1, 10);
        cook.addCookiesToMake(cookie2, 10);
        cook.addCookiesToMake(cookie3, 10);
        cook.bakeAllCookies();
        assertTrue(cook.getCookiesToMake().isEmpty());
        assertTrue(cook.getCookiesReady().containsKey(cookie1));
        assertTrue(cook.getCookiesReady().containsKey(cookie2));
        assertTrue(cook.getCookiesReady().containsKey(cookie3));
    }

    @Test
    void removeAllBakedCookiesTest(){
        Order order = new Order(new Client());

        CookieRecipe cookie1 = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.MNMS)), Mix.MIXED, Cooking.CHEWY);
        CookieRecipe cookie2 = new CookieRecipe("Oatmeal / Cinnamon", Dough.OATMEAL, Flavor.CINNAMON, new ArrayList<>(Arrays.asList(Topping.MNMS)), Mix.TOPPED, Cooking.CRUNCHY);
        CookieRecipe cookie3 = new CookieRecipe("Plain / Vanilla", Dough.PLAIN, Flavor.VANILLA, new ArrayList<>(Arrays.asList(Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);
        order.addOrderLine(cookie1, 10);
        order.addOrderLine(cookie2, 15);
        order.addOrderLine(cookie3, 20);

        cook.addCookiesToMake(cookie1, 10);
        cook.addCookiesToMake(cookie2, 15);
        cook.addCookiesToMake(cookie3, 20);
        cook.bakeAllCookies();

        cook.removeAllBakedCookies(order);
        assertEquals(0,cook.getCookiesToMake().size());
    }

}





