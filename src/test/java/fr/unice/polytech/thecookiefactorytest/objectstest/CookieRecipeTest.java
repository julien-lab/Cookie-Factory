package fr.unice.polytech.thecookiefactorytest.objectstest;


import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class CookieRecipeTest {

    EnumMap<Dough, Integer> customDoughs = new EnumMap<>(Dough.class);
    EnumMap<Flavor, Integer> customFlavors = new EnumMap<>(Flavor.class);
    EnumMap<Topping, Integer> customToppings = new EnumMap<>(Topping.class);

    @Test
    void calculatePriceTest() {
        EnumMap<Dough, Integer> doughs = new EnumMap<>(Dough.class);
        doughs.put(Dough.OATMEAL, 2);

        // Basic recipes
        CookieRecipe cr1 = new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.MNMS)), Mix.MIXED, Cooking.CHEWY);
        CookieRecipe cr2 = new CookieRecipe("Peanut Butter / Cinnamon", Dough.PEANUT_BUTTER, Flavor.CINNAMON, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CRUNCHY);
        CookieRecipe cr3 = new CookieRecipe("Chocolate / Vanilla", Dough.CHOCOLATE, Flavor.VANILLA, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY);

        cr1.calculatePrice();
        cr2.calculatePrice();
        cr3.calculatePrice();

        assertEquals(1.6, cr1.getPrice());
        assertEquals(1.2, cr2.getPrice());
        assertEquals(1.2, cr3.getPrice());

        // More complicated recipe
        customDoughs.put(Dough.OATMEAL,2);
        customFlavors.put(Flavor.CHILI, 1);
        customToppings.put(Topping.MNMS, 3);
        CookieRecipe customizedRecipe = new CookieRecipe("myCustomRecipe", Mix.MIXED, Cooking.CRUNCHY);
        customizedRecipe.setDoughs(customDoughs);
        customizedRecipe.setFlavors(customFlavors);
        customizedRecipe.setToppings(customToppings);

        customizedRecipe.calculatePrice();

        assertEquals(3.13, customizedRecipe.getPrice());

    }
}