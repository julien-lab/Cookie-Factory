package fr.unice.polytech.cucumbertest;

import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.Client;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Storage;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import io.cucumber.java8.En;

import java.util.EnumMap;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class CreateCustomizedCookieRecipeStepdef implements En {

    private TheCookieFactory tcf;
    private Storage tcfIngredients;
    private Client client;
    private Order order;
    private CookieRecipe customizedRecipe;

    public CreateCustomizedCookieRecipeStepdef() {

        Given("^a client who wants to create a customized cookie recipe$", () -> {
            tcf = new TheCookieFactory();
            client = new Client();
        });

        When("^the client creates his customized recipe and that recipe is valid$", () -> {
            EnumMap<Dough, Integer> customDoughs = new EnumMap<Dough, Integer>(Dough.class);
            EnumMap<Flavor, Integer> customFlavors = new EnumMap<Flavor, Integer>(Flavor.class);
            EnumMap<Topping, Integer> customToppings = new EnumMap<Topping, Integer>(Topping.class);
            customDoughs.put(Dough.OATMEAL,2);
            customFlavors.put(Flavor.CHILI, 1);
            customToppings.put(Topping.MNMS, 3);

            customizedRecipe = new CookieRecipe("myCustomRecipe", Mix.MIXED, Cooking.CRUNCHY);
            customizedRecipe.setDoughs(customDoughs);
            customizedRecipe.setFlavors(customFlavors);
            customizedRecipe.setToppings(customToppings);
        });

        Then("^the recipe is ready to be added to an order$", () -> {
            assertTrue(customizedRecipe.isValid());
        });

        When("^the client creates his customized recipe and that recipe is invalid$", () -> {
            EnumMap<Dough, Integer> customDoughs = new EnumMap<Dough, Integer>(Dough.class);
            EnumMap<Flavor, Integer> customFlavors = new EnumMap<Flavor, Integer>(Flavor.class);
            EnumMap<Topping, Integer> customToppings = new EnumMap<Topping, Integer>(Topping.class);
            customFlavors.put(Flavor.CHILI, 1);
            customToppings.put(Topping.MNMS, 3);
            customizedRecipe = new CookieRecipe("myCustomRecipe", Mix.MIXED, Cooking.CRUNCHY);
            customizedRecipe.setDoughs(customDoughs);
            customizedRecipe.setFlavors(customFlavors);
            customizedRecipe.setToppings(customToppings);
        });

        Then("^the recipe is deleted$", () -> {
            assertFalse(customizedRecipe.isValid());
        });

    }
}
