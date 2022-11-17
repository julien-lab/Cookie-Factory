package fr.unice.polytech.thecookiefactorytest.objectstest.account;

import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountTest {
    private ClientAccount clientAccount;

    @BeforeEach
    void init(){
        clientAccount = new ClientAccount("test");
    }

    @Test
    void testClientNotInLoyaltyProgram(){
        assertFalse(clientAccount.isPartOfLoyaltyProgram());
    }

    @Test
    void testJoinLoyaltyProgram() {
        clientAccount.joinLoyaltyProgram();
        assertTrue(clientAccount.isPartOfLoyaltyProgram());
    }

    @Test
    void testAddOrder() {
        Order o1 = new Order(null);

        assertEquals(0, clientAccount.getNumberCookiesPurchased());
        assertEquals(0, clientAccount.getClientOrders().size());

        TheCookieFactory tfc = new TheCookieFactory();
        ArrayList<OrderLine> ols = new ArrayList<>();
        ols.add(new OrderLine( tfc.getRecipes().get(0), 2));
        ols.add(new OrderLine( tfc.getRecipes().get(1), 5));
        o1.setOrderLines(ols);
        clientAccount.addOrder(o1);
        assertEquals(1, clientAccount.getClientOrders().size());
        assertEquals(7, clientAccount.getNumberCookiesPurchased());

        Order o2 = new Order(null);
        ols = new ArrayList<>();
        ols.add(new OrderLine( tfc.getRecipes().get(0), 2));
        ols.add(new OrderLine( tfc.getRecipes().get(1), 5));
        ols.add(new OrderLine( tfc.getRecipes().get(2), 4));
        ols.add(new OrderLine( tfc.getRecipes().get(3), 1));
        o2.setOrderLines(ols);

        clientAccount.addOrder(o2);
        assertEquals(2, clientAccount.getClientOrders().size());
        assertEquals(19, clientAccount.getNumberCookiesPurchased());
    }

    @Test
    void testRemove30PurchasedCookies() {
        clientAccount = new ClientAccount("test");
        assertEquals(0, clientAccount.getNumberCookiesPurchased());
        clientAccount.remove30PurchasedCookies();
        assertEquals(0, clientAccount.getNumberCookiesPurchased());
        clientAccount.setNumberCookiesPurchased(30);
        clientAccount.remove30PurchasedCookies();
        assertEquals(0, clientAccount.getNumberCookiesPurchased());
        clientAccount.setNumberCookiesPurchased(31);
        clientAccount.remove30PurchasedCookies();
        assertEquals(1, clientAccount.getNumberCookiesPurchased());
    }
}