package fr.unice.polytech.thecookiefactory.objects.account;

import fr.unice.polytech.thecookiefactory.Calculator;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Shop;

import java.time.LocalTime;
import java.util.Map;

public class LocalManager extends EmployeeAccount {

    private final Shop shop;

    public LocalManager(Shop shop) {
        super("Local Manager");
        this.shop =shop;
    }

    public Shop getShop() {
        return shop;
    }

    public void setOpeningTime(LocalTime openingTime){
        this.shop.setOpeningTime(openingTime);
    }

    public void setClosingTime(LocalTime closingTime){
        this.shop.setClosingTime(closingTime);
    }

    public Map<CookieRecipe, Integer> getStatisticsOnNumberOfCookieOrdered(){
        return Calculator.fromCookieOrderListToCookieOrderMap(this.shop.getPurchasedOrders());
    }

    public Map<LocalTime, Integer> getStatisticsOnOrdersPickUpTime(){
        return Calculator.getStatisticsOnOrdersPickUpTime(this.shop.getPurchasedOrders());
    }



}

