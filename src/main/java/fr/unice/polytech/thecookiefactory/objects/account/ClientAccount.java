package fr.unice.polytech.thecookiefactory.objects.account;

import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;

import java.util.ArrayList;
import java.util.List;

public class ClientAccount extends Account {
    private String accountName;
    private boolean isPartOfLoyaltyProgram;
    private final List<Order> clientOrders;
    private int numberCookiesPurchased;

    public ClientAccount(String nameAccount){
        super();
        this.accountName = nameAccount;
        this.isPartOfLoyaltyProgram = false;
        this.clientOrders = new ArrayList<>();
        this.numberCookiesPurchased =0;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setNumberCookiesPurchased(int numberCookiesPurchased) {
        this.numberCookiesPurchased = numberCookiesPurchased;
    }

    public void joinLoyaltyProgram() {
        this.isPartOfLoyaltyProgram = true;
    }

    public boolean isPartOfLoyaltyProgram() {
        return isPartOfLoyaltyProgram;
    }

    public List<Order> getClientOrders() {
        return clientOrders;
    }

    public int getNumberCookiesPurchased() {
        return numberCookiesPurchased;
    }

    public void addOrder(Order o){
        clientOrders.add(o);
        for(OrderLine ol : o.getOrderLines()){
            numberCookiesPurchased+=ol.getQuantity();
        }
    }

    public void remove30PurchasedCookies(){
        if(numberCookiesPurchased > 30){
            numberCookiesPurchased-=30;
        }else{
            numberCookiesPurchased=0;
        }
    }

}
