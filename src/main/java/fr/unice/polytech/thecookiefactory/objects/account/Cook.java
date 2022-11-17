package fr.unice.polytech.thecookiefactory.objects.account;

import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.objects.Shop;

import java.util.HashMap;
import java.util.Map;

public class Cook extends EmployeeAccount {

    private final Shop shop;
    private final Map<CookieRecipe,Integer> cookiesToMake;
    private final Map<CookieRecipe, Integer> cookiesReady;



    public Cook(Shop shop) {
        super("Cook");
        this.shop=shop;
        cookiesToMake = new HashMap<>();
        cookiesReady = new HashMap<>();
    }

    public Shop getShop() {
        return this.shop;
    }

    public Map<CookieRecipe, Integer> getCookiesToMake() {
        return this.cookiesToMake;
    }

    public Map<CookieRecipe, Integer> getCookiesReady() {
        return cookiesReady;
    }

    public void addCookiesToMake(CookieRecipe cr, int quantity){
        if(cr != null){
            this.cookiesToMake.merge(cr, quantity, Integer::sum);
        }
    }

    public void bakeAllCookies(){
        for (Map.Entry<CookieRecipe, Integer> cookieToPrepare : this.cookiesToMake.entrySet()) {
            cookiesReady.merge(cookieToPrepare.getKey(), cookieToPrepare.getValue(), Integer::sum);
        }
        cookiesToMake.clear();
    }

    public void removeAllBakedCookies(Order order){
         for(OrderLine ol : order.getOrderLines()){
             cookiesReady.merge(ol.getCookieRecipe(), -cookiesReady.get(ol.getCookieRecipe()), Integer::sum);
         }
    }

}
