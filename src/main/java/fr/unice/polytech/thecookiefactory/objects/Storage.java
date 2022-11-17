package fr.unice.polytech.thecookiefactory.objects;

import fr.unice.polytech.thecookiefactory.ingredients.Dough;
import fr.unice.polytech.thecookiefactory.ingredients.Flavor;
import fr.unice.polytech.thecookiefactory.ingredients.Topping;

import java.util.EnumMap;
import java.util.Map;

public class Storage {

    private Map<Dough, Integer> doughs;
    private Map<Flavor, Integer> flavors;
    private Map<Topping, Integer> toppings;

    public Storage(){
        this.doughs = new EnumMap<>(Dough.class);
        this.flavors = new EnumMap<>(Flavor.class);
        this.toppings = new EnumMap<>(Topping.class);
        this.doughs.put(Dough.PLAIN, 100);
        this.doughs.put(Dough.CHOCOLATE, 100);
        this.doughs.put(Dough.OATMEAL, 100);
        this.doughs.put(Dough.PEANUT_BUTTER, 100);
        this.flavors.put(Flavor.CHILI, 100);
        this.flavors.put(Flavor.CINNAMON, 100);
        this.flavors.put(Flavor.VANILLA, 100);
        this.toppings.put(Topping.MILK_CHOCOLATE, 100);
        this.toppings.put(Topping.MNMS, 100);
        this.toppings.put(Topping.REESES_BUTTERCUP, 100);
        this.toppings.put(Topping.WHITE_CHOCOLATE, 100);
    }

    public Map<Dough, Integer> getDoughs() {
        return doughs;
    }

    public void setDoughs(Map<Dough, Integer> doughs) {
        this.doughs = doughs;
    }

    public Map<Flavor, Integer> getFlavors() {
        return flavors;
    }

    public void setFlavors(Map<Flavor, Integer> flavors) {
        this.flavors = flavors;
    }

    public Map<Topping, Integer> getToppings() {
        return toppings;
    }

    public void setToppings(Map<Topping, Integer> toppings) {
        this.toppings = toppings;
    }

    public void consumeDoughIngredient(Dough doughType, int quantity){
        if(quantity > 0){
            int numberAvailable = doughs.get(doughType);
            if(numberAvailable >= quantity) {
                doughs.put(doughType, numberAvailable - quantity);
            }
        }
    }

    public void consumeFlavorIngredient(Flavor flavorType, int quantity){
        if(quantity > 0) {
            int numberAvailable = flavors.get(flavorType);
            if (numberAvailable >= quantity) {
                flavors.put(flavorType, numberAvailable - quantity);
            }
        }
    }

    public void consumeToppingIngredient(Topping toppingType, int quantity){
        if(quantity > 0) {
            int numberAvailable = toppings.get(toppingType);
            if (numberAvailable >= quantity) {
                toppings.put(toppingType, numberAvailable - quantity);
            }
        }
    }

    public void restockDoughIngredient(Dough doughType, int number){
        if(number > 0) {
            int numberAvailable = doughs.get(doughType);
            doughs.put(doughType, numberAvailable + number);
        }
    }

    public void restockFlavorIngredient(Flavor flavorType, int number){
        if(number > 0) {
            int numberAvailable = flavors.get(flavorType);
            flavors.put(flavorType, numberAvailable + number);
        }
    }

    public void restockToppingIngredient(Topping toppingType, int number){
        if(number > 0) {
            int numberAvailable = toppings.get(toppingType);
            toppings.put(toppingType, numberAvailable + number);
        }
    }

    public boolean isDoughIngredientInStock(Dough doughType, int quantity){
        return doughs.get(doughType) >= quantity;
    }

    public boolean isFlavorIngredientInStock(Flavor flavorType, int quantity){
        return flavors.get(flavorType) >= quantity;
    }

    public boolean isToppingIngredientInStock(Topping toppingType, int quantity){
        return toppings.get(toppingType) >= quantity;
    }


    public boolean hasEnoughIngredients(Map<Dough, Integer> doughsDose, Map<Flavor, Integer> flavorsDose, Map<Topping, Integer> toppingsDose) {
        return this.hasEnoughDoughs(doughsDose) && this.hasEnoughFlavors(flavorsDose) && this.hasEnoughToppings(toppingsDose);
    }

    private boolean hasEnoughToppings(Map<Topping, Integer> toppingsDose) {
        for(Topping topping : toppingsDose.keySet()){
            if(!this.isToppingIngredientInStock(topping, toppingsDose.get(topping))){
                return false;
            }
        }
        return true;
    }

    private boolean hasEnoughFlavors(Map<Flavor, Integer> flavorsDose) {
        for(Flavor flavor : flavorsDose.keySet()){
            if(!this.isFlavorIngredientInStock(flavor, flavorsDose.get(flavor))){
                return false;
            }
        }
        return true;
    }

    private boolean hasEnoughDoughs(Map<Dough, Integer> doughsDose) {
        for(Dough dough : doughsDose.keySet()){
            if(!this.isDoughIngredientInStock(dough, doughsDose.get(dough))){
                return false;
            }
        }
        return true;
    }
}