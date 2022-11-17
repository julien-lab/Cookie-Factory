package fr.unice.polytech.thecookiefactorytest.objectstest.account;

import fr.unice.polytech.thecookiefactory.objects.account.ClientAccount;
import fr.unice.polytech.thecookiefactory.objects.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private Client client;

    @Test
    void isPartOfLoyaltyProgram() {
        client = new Client();
        assertFalse(client.isPartOfLoyaltyProgram());
        ClientAccount ca = new ClientAccount("titouan");
        client.setAccount(ca);
        assertFalse(client.isPartOfLoyaltyProgram());
        ca.joinLoyaltyProgram();
        assertTrue(client.isPartOfLoyaltyProgram());
    }

    @Test
    void canHaveLoyaltyReduction() {
        client = new Client();
        assertFalse(client.canHaveLoyaltyReduction());
        ClientAccount ca = new ClientAccount("titouan");
        client.setAccount(ca);
        assertFalse(client.canHaveLoyaltyReduction());
        ca.joinLoyaltyProgram();
        assertFalse(client.canHaveLoyaltyReduction());
        ca.setNumberCookiesPurchased(30);
        assertTrue(client.canHaveLoyaltyReduction());
    }

    @Test
    void useLoyaltyReduction() {
        client = new Client();
        ClientAccount ca = new ClientAccount("titouan");
        client.setAccount(ca);
        ca.joinLoyaltyProgram();

        ca.setNumberCookiesPurchased(29);
        assertFalse(client.useLoyaltyReduction());
        assertEquals(29, ca.getNumberCookiesPurchased());

        ca.setNumberCookiesPurchased(30);
        assertTrue(client.useLoyaltyReduction());
        assertEquals(0, ca.getNumberCookiesPurchased());
    }
}