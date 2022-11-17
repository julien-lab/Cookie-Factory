package fr.unice.polytech.thecookiefactory.ingredients;

public enum Topping {
    WHITE_CHOCOLATE(0.2), MILK_CHOCOLATE(0.3), MNMS(0.4), REESES_BUTTERCUP(0.3);

    private final double price;

    private Topping(double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
