package fr.unice.polytech.thecookiefactory.objects.account;

import fr.unice.polytech.lastminutemarcel.LastMinuteMarcel;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.objects.Shop;

import java.util.ArrayList;
import java.util.List;

public class OrderSupervisor extends EmployeeAccount {

    private final List<Order> ordersToPrepare;
    private final List<Order> ordersInPreparation;
    private final Shop shop;

    public OrderSupervisor(Shop shop) {
        super("Order Supervisor");
        this.shop =shop;
        ordersToPrepare = new ArrayList<>();
        ordersInPreparation = new ArrayList<>();
    }

    public void addOrderToPrepare(Order o){
        this.ordersToPrepare.add(o);
    }

    public void sendCookiesOfAllOrderlinesOfTheOrder(Order order){
        for(OrderLine ol : order.getOrderLines()){
            shop.getCook().addCookiesToMake(ol.getCookieRecipe(), ol.getQuantity());
        }
        this.ordersInPreparation.add(order);
    }

    public void sendCookiesOfAllOrdersToBePrepared(){
        List<Order> orderToRemove = new ArrayList<>();
        for(Order o : this.ordersToPrepare){
            this.sendCookiesOfAllOrderlinesOfTheOrder(o);
            orderToRemove.add(o);
        }
        this.ordersToPrepare.removeAll(orderToRemove);
    }

    public void sendCookiesOfTheOldestOrder(){
        Order o = this.getTheOldestOrderToPrepare();
        if(o != null){
            this.sendCookiesOfAllOrderlinesOfTheOrder(o);
            this.ordersToPrepare.remove(o);
        }
    }

    public Order getTheOldestOrderToPrepare(){
        if(this.ordersToPrepare.isEmpty()){
            return null;
        }else{
            return this.ordersToPrepare.get(0);
        }
    }

    public List<Order> getOrdersReady(){
        List<Order> ordersReady = new ArrayList<>();
        for(Order o : ordersInPreparation){
            boolean allCookiesAreDone = true;
            for(OrderLine ol : o.getOrderLines()){
                int orderLineQuantity = ol.getQuantity();
                if(this.shop.getCook().getCookiesReady().get(ol.getCookieRecipe()) < orderLineQuantity){
                    allCookiesAreDone = false;
                    break;
                }
            }
            if(allCookiesAreDone){
                this.shop.getCook().removeAllBakedCookies(o); //enlever nombre de cookie dans cookie ready
                ordersReady.add(o);
            }
        }
        return ordersReady;
    }

    public void sendOrdersReadyToCashier(List<Order> ordersReady){
        for(Order order : ordersReady){
            this.shop.getCashier().addOrderReady(order);
            this.ordersInPreparation.remove(order);
        }
    }

    public boolean entrustToLastMinuteMarcel(Order order, Boolean isLateRequest){
        return LastMinuteMarcel.acceptOrder(order, isLateRequest); // Retourne false lorsqu'aucun rider n'est disponible
    }

    public void notifyLastMinuteMarcelOrderReady(Order order){
        LastMinuteMarcel.askRiderToMove(order);
    }

    public List<Order> getOrdersToPrepare() {
        return ordersToPrepare;
    }

    public List<Order> getOrdersInPreparation() {
        return ordersInPreparation;
    }
}
