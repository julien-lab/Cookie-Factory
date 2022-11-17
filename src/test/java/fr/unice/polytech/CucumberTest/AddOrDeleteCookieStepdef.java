package fr.unice.polytech.cucumbertest;


import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.*;


public class AddOrDeleteCookieStepdef implements En{

    private Order order;
    private TheCookieFactory tfc;
    private Client client;

    public AddOrDeleteCookieStepdef() {

        Given("a client with no account", () -> {
            client = new Client();
        });

        And("^a new order$", () -> {
            order = new Order(client);
            tfc = new TheCookieFactory();
        });

        When("^the client adds \"([^\"]*)\" of \"([^\"]*)\" cookies$", (String arg0, String arg1) -> {
            order.addOrderLine(tfc.getRecipeByName(arg1), Integer.parseInt(arg0));
        });

        Then("^there is a new orderLine of \"([^\"]*)\" \"([^\"]*)\" in the order$", (String arg0, String arg1) -> {
            assertEquals( 1, order.getOrderLines().size());
            for (OrderLine l : order.getOrderLines()) {
                if(l.getCookieRecipe().getName().equals(arg1)){
                    assertEquals(l.getQuantity(), Integer.parseInt(arg0));
                    break;
                }
            }
        });

        When("^there are/is \"([^\"]*)\" \"([^\"]*)\" cookies and the client deletes \"([^\"]*)\" \"([^\"]*)\"$", (String arg0, String arg1, String arg2, String arg3) -> {
            order.addOrderLine(tfc.getRecipeByName(arg1), Integer.parseInt(arg0));
            for (OrderLine l : order.getOrderLines()) {
                if(l.getCookieRecipe().getName().equals(tfc.getRecipeByName(arg1).getName())){
                    l.deleteCookie(Integer.parseInt(arg2));
                    break;
                }
            }
        });

        Then("^there are/is \"([^\"]*)\" \"([^\"]*)\" in the orderLine of \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {
            for (OrderLine l : order.getOrderLines()) {
                if(l.getCookieRecipe().getName().equals(tfc.getRecipeByName(arg1).getName())){
                    assertEquals(l.getQuantity(), Integer.parseInt(arg0));
                }
            }
        });

        When("^there are/is \"([^\"]*)\" \"([^\"]*)\" cookies and the client deletes all them$", (String arg0, String arg1) -> {
            order.addOrderLine(tfc.getRecipeByName(arg1), Integer.parseInt(arg0));
            order.getOrderLines().get(0).deleteCookie(Integer.parseInt(arg0));
            order.removeOrderLine(arg1);
        });

        Then("^the orderline for that cookie recipe is removed$", () -> {
            assertTrue(order.getOrderLines().isEmpty());
        });

        When("^there are/is \"([^\"]*)\" \"([^\"]*)\" and the client deletes \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {
            order.addOrderLine(tfc.getRecipeByName(arg1), Integer.parseInt(arg0));
            order.getOrderLines().get(0).deleteCookie(Integer.parseInt(arg2));
            order.removeOrderLine(arg1);
        });

        Then("^the orderline fot that cookie recipe is removed$", () -> {
            assertTrue(order.getOrderLines().isEmpty());
        });

    }
}
