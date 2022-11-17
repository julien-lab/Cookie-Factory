package fr.unice.polytech.thecookiefactory.ingredients;

public enum Dough {
    PLAIN(0.5), CHOCOLATE(0.6), PEANUT_BUTTER(0.7), OATMEAL(0.5);

    private final double price;

    private Dough(double price){
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
