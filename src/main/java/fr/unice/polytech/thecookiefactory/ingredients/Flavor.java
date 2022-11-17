package fr.unice.polytech.thecookiefactory.ingredients;

public enum Flavor {
    VANILLA(0.1), CINNAMON(0.2), CHILI(0.3);

    private final double price;

    private Flavor(double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
