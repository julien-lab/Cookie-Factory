package fr.unice.polytech.thecookiefactory.objects;
import fr.unice.polytech.lastminutemarcel.LastMinuteMarcel;
import fr.unice.polytech.thecookiefactory.Calculator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {

    private static int orderCount = 1;

    public enum LastMinuteMarcelStatus{
        NOT_ENTRUSTED, ORDER_IN_PREPARATION, WAITING_FOR_RIDER, DELIVERY_IN_PROCESS, DELIVERED, NO_FREE_RIDERS
    }

    private final int orderNumber;
    private Date pickUpDay;
    private LocalTime pickUpTime;
    private List<OrderLine> orderLines;
    private Shop shop;
    private Client client;
    private double priceBeforeTax;
    private double priceAfterTax;
    private boolean paymentDone;
    private boolean hasBeenWithdrawn;
    private boolean loyaltyDiscountUsed;
    private LastMinuteMarcelStatus lastMinuteMarcelStatus;
    private boolean lateLastMinuteMarcelStatusRequest;

    public Order(Client client){
        this.orderNumber = orderCount;
        orderCount++;
        this.orderLines = new ArrayList<>();
        this.shop = new Shop();
        this.client = client;
        this.paymentDone = false;
        this.hasBeenWithdrawn = false;
        this.loyaltyDiscountUsed = false;
        this.priceBeforeTax = 0;
        this.lastMinuteMarcelStatus = LastMinuteMarcelStatus.NOT_ENTRUSTED;
    }

    public Order(Client client, Date pickUpDate, List<OrderLine> orderLines, Shop shop) {
        this.orderNumber = orderCount;
        orderCount++;
        this.paymentDone = false;
        this.hasBeenWithdrawn = false;
        this.pickUpDay = reformatDate(pickUpDate);
        this.orderLines = orderLines;
        this.shop = shop;
        this.client = client;
        this.loyaltyDiscountUsed = false;
        this.priceBeforeTax = 0;
        this.lastMinuteMarcelStatus = LastMinuteMarcelStatus.NOT_ENTRUSTED;
    }

    public boolean hasBeenWithdrawn() {
        return hasBeenWithdrawn;
    }

    public void setHasBeenWithdrawn(boolean hasBeenWithdrawn) {
        this.hasBeenWithdrawn = hasBeenWithdrawn;
    }

    public void pickUpDayTime(Date pickUpDay, LocalTime pickUpTime) {
        pickUpDay = Calculator.getTimeCalendarMidnightOfTheDay(pickUpDay).getTime();
        if(shop.validatePickUpDayTime(pickUpDay, pickUpTime)){
            this.pickUpDay = reformatDate(pickUpDay);
            this.pickUpTime = pickUpTime;
        }
    }

    public void setPickUpDay(Date pickUpDay) {
        if(pickUpDay != null) {
            pickUpDay = Calculator.getTimeCalendarMidnightOfTheDay(pickUpDay).getTime();
        }
        this.pickUpDay = pickUpDay;
    }

    public void setPickUpTime(LocalTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Date getPickUpDay() {
        return pickUpDay;
    }

    public Shop getShop() {
        return shop;
    }

    public boolean isLateLastMinuteMarcelStatusRequest() {
        return lateLastMinuteMarcelStatusRequest;
    }

    public void setLateLastMinuteMarcelStatusRequest(boolean lateLastMinuteMarcelStatusRequest) {
        this.lateLastMinuteMarcelStatusRequest = lateLastMinuteMarcelStatusRequest;
    }

    public void pickShop(Shop shop) {
        if(shop.canDoTheOrder(this)){
            this.shop = shop;
        }
    }

    public boolean isLoyaltyDiscountUsed() {
        return loyaltyDiscountUsed;
    }

    public void addOrderLine(CookieRecipe recipe, int quantity){
        if(recipe.isValid()){
            OrderLine newOL = new OrderLine(recipe, quantity);
            this.orderLines.add(newOL);
            if(this.loyaltyDiscountUsed){
                this.priceBeforeTax+=newOL.getPrice()*0.9;
            }else{
                this.priceBeforeTax+=newOL.getPrice();
            }
            applyTaxOnPrice();
        }
    }

    public void removeOrderLine(String nameOrderLine) {
        for (OrderLine l : orderLines) {
            if (l.getCookieRecipe().getName().equals(nameOrderLine)) {
                orderLines.remove(l);
                if(this.loyaltyDiscountUsed){
                    this.priceBeforeTax-=l.getPrice()*0.9;
                }else{
                    this.priceBeforeTax-=l.getPrice();
                }
                applyTaxOnPrice();
                break;
            }
        }
    }

    public double getPriceBeforeTax() {
        return Calculator.round(priceBeforeTax, 2);
    }

    public double getPriceAfterTax() {
        return Calculator.round(priceAfterTax,2);
    }

    public boolean isPaymentDone() {
        return paymentDone;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public LocalTime getPickUpTime() {
        return pickUpTime;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public Date reformatDate(Date pickUpDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickUpDate);
        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date  = cal.get(Calendar.DATE);
        cal.clear();
        cal.set(year, month, date);
        return cal.getTime();
    }

    public void applyDeliveryFee(double deliveryFee) {
        this.priceBeforeTax = priceBeforeTax + deliveryFee;
        this.priceAfterTax = priceAfterTax + deliveryFee;
    }

    public void applyTaxOnPrice(){
        this.priceAfterTax = this.priceBeforeTax*(1.0 + shop.getPercentageTax());
    }

    // A TESTER
    public double calculateTotalPriceOfOrderWithTaxLastMinuteMarcel(){
        this.priceAfterTax = this.priceBeforeTax*(1.0 + shop.getPercentageTax());
        return Calculator.round(priceAfterTax + LastMinuteMarcel.FIXED_DELIVERY_FEE,2);
    }

    public void applyLoyaltyDiscount(){
        if(this.client.canHaveLoyaltyReduction() && !this.loyaltyDiscountUsed){
            this.priceBeforeTax =  Calculator.round(this.priceBeforeTax*0.9,2);
            this.loyaltyDiscountUsed = true;
        }
    }

    public void purchase(){
        if (this.shop!=null && this.pickUpDay!=null && this.pickUpTime!=null
                && this.getPriceAfterTax() > 0 && this.priceAfterTax <= this.client.getMoneyOnHisCard()
                && this.shop.isPickUpTimeAvailable(this.pickUpDay.getTime(),this.pickUpTime)){
                this.setPaymentDone(true);
                this.client.setOrder(this); //AJOUTER ORDER AU CLIENT ET A LA LISTE DE SES COMMANDES SI IL A UN COMPTE
                this.shop.addPurchasedOrder(this); //AJOUTER ORDER A LA LISTE DES ORDER ACHETE DANS LE SHOP
                this.shop.consumeIngredientsOfTheOrder(this); //RETIRER LES INGREDIENTS UTILISE POUR FAIRE LA COMMANDE DANS LE STORAGE DU SHOP
                if(this.loyaltyDiscountUsed){
                    this.client.useLoyaltyReduction();
                }
                this.shop.scheduleTimeSlot(this.pickUpDay, this.pickUpTime); //AJOUTER LE PICK UP TIME DE LA COMMANDE
        }
    }

    public void changeLastMinuteMarcelStatus(LastMinuteMarcelStatus status){
        this.lastMinuteMarcelStatus = status;
    }

    public LastMinuteMarcelStatus getLastMinuteMarcelStatus() {
        return lastMinuteMarcelStatus;
    }


}