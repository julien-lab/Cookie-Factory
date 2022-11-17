package fr.unice.polytech.thecookiefactory.objects.account;

import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.Shop;

import java.util.ArrayList;
import java.util.List;

public class Cashier extends EmployeeAccount {

    private final List<Order> ordersReady;
    private final Shop shop;

    public Cashier(Shop shop) {
        super("Cashier");
        this.shop = shop;
        ordersReady = new ArrayList<>();
    }

    public Shop getShop() {
        return shop;
    }

    public void giveOrder(Order o){
        if (this.orderIsReady(o)) {
            o.setHasBeenWithdrawn(true);
            this.ordersReady.remove(o);
        }
    }

    public void addOrderReady(Order o){
        this.ordersReady.add(o);
    }

    public boolean orderIsReady(Order o){
        return ordersReady.contains(o);
    }

    public List<Order> getOrdersReady() {
        return ordersReady;
    }
}
