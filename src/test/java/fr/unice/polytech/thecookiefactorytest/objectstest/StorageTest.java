package fr.unice.polytech.thecookiefactorytest.objectstest;

import fr.unice.polytech.thecookiefactory.ingredients.Dough;
import fr.unice.polytech.thecookiefactory.ingredients.Flavor;
import fr.unice.polytech.thecookiefactory.ingredients.Topping;
import fr.unice.polytech.thecookiefactory.objects.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private Storage storage = new Storage();

    @Test
    void consumeDoughIngredient() {
        assertEquals(100,storage.getDoughs().get(Dough.PEANUT_BUTTER));
        assertEquals(100,storage.getDoughs().get(Dough.CHOCOLATE));
        assertEquals(100,storage.getDoughs().get(Dough.PLAIN));
        assertEquals(100,storage.getDoughs().get(Dough.OATMEAL));

        storage.consumeDoughIngredient(Dough.CHOCOLATE, 3);
        storage.consumeDoughIngredient(Dough.PLAIN, 2);
        storage.consumeDoughIngredient(Dough.OATMEAL, 4);
        storage.consumeDoughIngredient(Dough.PEANUT_BUTTER, 8);
        storage.consumeDoughIngredient(Dough.PEANUT_BUTTER, -8);

        assertEquals(92,storage.getDoughs().get(Dough.PEANUT_BUTTER));
        assertEquals(97,storage.getDoughs().get(Dough.CHOCOLATE));
        assertEquals(98,storage.getDoughs().get(Dough.PLAIN));
        assertEquals(96,storage.getDoughs().get(Dough.OATMEAL));
    }

    @Test
    void consumeFlavorIngredient() {
        assertEquals(100,storage.getFlavors().get(Flavor.VANILLA));
        assertEquals(100,storage.getFlavors().get(Flavor.CINNAMON));
        assertEquals(100,storage.getFlavors().get(Flavor.CHILI));

        storage.consumeFlavorIngredient(Flavor.VANILLA, 3);
        storage.consumeFlavorIngredient(Flavor.CINNAMON, 2);
        storage.consumeFlavorIngredient(Flavor.CHILI, 4);
        storage.consumeFlavorIngredient(Flavor.CHILI, -4);

        assertEquals(97,storage.getFlavors().get(Flavor.VANILLA));
        assertEquals(98,storage.getFlavors().get(Flavor.CINNAMON));
        assertEquals(96,storage.getFlavors().get(Flavor.CHILI));
    }

    @Test
    void consumeToppingIngredient() {
        assertEquals(100,storage.getToppings().get(Topping.MNMS));
        assertEquals(100,storage.getToppings().get(Topping.MILK_CHOCOLATE));
        assertEquals(100,storage.getToppings().get(Topping.WHITE_CHOCOLATE));
        assertEquals(100,storage.getToppings().get(Topping.REESES_BUTTERCUP));

        storage.consumeToppingIngredient(Topping.MNMS, 3);
        storage.consumeToppingIngredient(Topping.MILK_CHOCOLATE, 2);
        storage.consumeToppingIngredient(Topping.WHITE_CHOCOLATE, 4);
        storage.consumeToppingIngredient(Topping.REESES_BUTTERCUP, 18);
        storage.consumeToppingIngredient(Topping.REESES_BUTTERCUP, -18);

        assertEquals(97,storage.getToppings().get(Topping.MNMS));
        assertEquals(98,storage.getToppings().get(Topping.MILK_CHOCOLATE));
        assertEquals(96,storage.getToppings().get(Topping.WHITE_CHOCOLATE));
        assertEquals(82,storage.getToppings().get(Topping.REESES_BUTTERCUP));
    }

    @Test
    void restockDoughIngredient() {
        assertEquals(100,storage.getDoughs().get(Dough.CHOCOLATE));
        assertEquals(100,storage.getDoughs().get(Dough.OATMEAL));
        storage.restockDoughIngredient(Dough.CHOCOLATE, 12);
        storage.restockDoughIngredient(Dough.OATMEAL, -12);
        assertEquals(112,storage.getDoughs().get(Dough.CHOCOLATE));
        assertEquals(100,storage.getDoughs().get(Dough.OATMEAL));
    }

    @Test
    void restockFlavorIngredient() {
        assertEquals(100,storage.getFlavors().get(Flavor.VANILLA));
        assertEquals(100,storage.getFlavors().get(Flavor.CHILI));
        storage.restockFlavorIngredient(Flavor.VANILLA, 22);
        storage.restockFlavorIngredient(Flavor.CHILI, -22);
        assertEquals(122,storage.getFlavors().get(Flavor.VANILLA));
        assertEquals(100,storage.getFlavors().get(Flavor.CHILI));
    }

    @Test
    void restockToppingIngredient() {
        assertEquals(100,storage.getToppings().get(Topping.WHITE_CHOCOLATE));
        assertEquals(100,storage.getToppings().get(Topping.REESES_BUTTERCUP));
        storage.restockToppingIngredient(Topping.WHITE_CHOCOLATE, 12);
        storage.restockToppingIngredient(Topping.REESES_BUTTERCUP, -12);
        assertEquals(112,storage.getToppings().get(Topping.WHITE_CHOCOLATE));
        assertEquals(100,storage.getToppings().get(Topping.REESES_BUTTERCUP));
    }

    @Test
    void isDoughIngredientInStock() {
        assertTrue(storage.isDoughIngredientInStock(Dough.CHOCOLATE,100));
        assertFalse(storage.isDoughIngredientInStock(Dough.CHOCOLATE,101));
        assertTrue(storage.isDoughIngredientInStock(Dough.OATMEAL,1));
    }

    @Test
    void isFlavorIngredientInStock() {
        assertTrue(storage.isFlavorIngredientInStock(Flavor.VANILLA,100));
        assertFalse(storage.isFlavorIngredientInStock(Flavor.VANILLA,101));
        assertTrue(storage.isFlavorIngredientInStock(Flavor.CINNAMON,1));
    }

    @Test
    void isToppingIngredientInStock() {
        assertTrue(storage.isToppingIngredientInStock(Topping.MNMS,100));
        assertFalse(storage.isToppingIngredientInStock(Topping.MNMS,101));
        assertTrue(storage.isToppingIngredientInStock(Topping.MILK_CHOCOLATE,1));
    }
}