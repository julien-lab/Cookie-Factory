package fr.unice.polytech.thecookiefactory.objects;


import fr.unice.polytech.thecookiefactory.Calculator;


public class OrderLine {

    private CookieRecipe cookie;
    private int quantity;
    private double price;

    public OrderLine(CookieRecipe cookie, int quantity) {
        this.cookie = cookie;
        this.quantity = quantity;
        this.price = Calculator.round(cookie.getPrice()*quantity,2); // on arrondit pour avoir max deux chiffres après la virgule
    }

    public CookieRecipe getCookieRecipe() {
        return cookie;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        price = Calculator.round(price,2);
        return price;
    }

    public void setCookie(CookieRecipe cookie) {
        this.cookie = cookie;
    }

    public void deleteCookie(int n){
        quantity = quantity-n;
        if(quantity<0) quantity=0;
        price= Calculator.round(quantity*cookie.getPrice(),2);
    }

    public void addCookie(int n){
        quantity = quantity+n;
        price= Calculator.round(quantity*cookie.getPrice(),2);
    }


}
