package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.*;

import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class ChooseOrderShopStepDef implements En {
    private Order order;
    private Shop shop1;
    private Shop shop2;

    public ChooseOrderShopStepDef() {

        Given("^an Order with cookies$", () -> {
            order =new Order(new Client());
            order.addOrderLine(new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY), 2);
            order.addOrderLine(new CookieRecipe("Oatmeal / Cinnamon", Dough.OATMEAL, Flavor.CINNAMON, new ArrayList<>(Collections.singletonList(Topping.MNMS)), Mix.TOPPED, Cooking.CRUNCHY), 3);
            order.setShop(null);
        });
        And("^2 shops with different stocks of ingredients$", () -> {
            shop1 = new Shop();
            shop2 = new Shop();

            Storage storageShop2 = shop2.getStorage();
            storageShop2.consumeDoughIngredient(Dough.CHOCOLATE, 100);
        });

        When("^the client choose a shop which can do his order$", () -> {
            order.pickShop(shop1);
        });
        Then("^the shop is added to the order$", () -> {
            assertNotNull(order.getShop());
            assertEquals(shop1 , order.getShop());
        });


        When("^the client choose a shop which can't do his order$", () -> {
            order.pickShop(shop2);
        });
        Then("^the shop isn't added to the order$", () -> {
            assertNull(order.getShop());
            assertNotEquals(shop2, order.getShop());
        });
    }
}
