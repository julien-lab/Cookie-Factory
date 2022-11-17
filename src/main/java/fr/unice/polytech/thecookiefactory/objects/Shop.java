package fr.unice.polytech.thecookiefactory.objects;

import fr.unice.polytech.thecookiefactory.ingredients.Dough;
import fr.unice.polytech.thecookiefactory.ingredients.Flavor;
import fr.unice.polytech.thecookiefactory.ingredients.Topping;
import fr.unice.polytech.thecookiefactory.objects.account.Cashier;
import fr.unice.polytech.thecookiefactory.objects.account.Cook;
import fr.unice.polytech.thecookiefactory.objects.account.LocalManager;
import fr.unice.polytech.thecookiefactory.objects.account.OrderSupervisor;
import fr.unice.polytech.thecookiefactory.Calculator;

import java.time.LocalTime;
import java.util.*;


public class Shop {

    private String shopName;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private double tax ;
    private Map<Long, DaySaleSchedule> schedule;
    private OrderSupervisor orderSupervisor;
    private Cashier cashier;
    private Cook cook;
    private LocalManager localManager;
    private final List<Shop> nextShops = new ArrayList<>();
    private boolean technicalIssue = false;
    private final List<Order> purchasedOrders;
    private final Storage storage;
    private CookieRecipe cookieBestOfOfCookieFactory;
    private final Boolean entrustsLastMinuteMarcel;

    public Shop(){
        this.shopName = "";
        this.purchasedOrders = new ArrayList<>();
        this.storage = new Storage();
        this.entrustsLastMinuteMarcel = false;
        this.schedule = setUpSchedule(LocalTime.of(8,30), LocalTime.of(19,30));
        this.cook = new Cook( this);
        this.orderSupervisor = new OrderSupervisor(this);
        this.tax = 0.2;
    }

    public Shop(String shopName){
        this.shopName = shopName;
        this.purchasedOrders = new ArrayList<>();
        this.storage = new Storage();
        this.entrustsLastMinuteMarcel = false;
        this.tax = 0.2;
    }

    public Shop(String shopName, LocalTime openingTime, LocalTime closingTime, double tax){
        this.shopName = shopName;
        this.openingTime=openingTime;
        this.closingTime=closingTime;
        this.tax=tax;
        this.schedule = setUpSchedule(openingTime, closingTime);
        this.cashier = new Cashier(this);
        this.localManager = new LocalManager(this);
        this.cook = new Cook( this);
        this.orderSupervisor = new OrderSupervisor(this);
        this.purchasedOrders = new ArrayList<>();
        this.storage = new Storage();
        this.entrustsLastMinuteMarcel = false;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void setCook(Cook cook) {
        this.cook = cook;
    }

    public Cook getCook() {
        return cook;
    }

    public Storage getStorage() {
        return storage;
    }

    public LocalManager getLocalManager() {
        return localManager;
    }

    public void setOpeningTime(LocalTime openingTime){
        this.openingTime=openingTime;
    }

    public void setClosingTime(LocalTime closingTime){
        this.closingTime=closingTime;
    }

    public LocalTime getOpeningTime(){
        return this.openingTime;
    }

    public LocalTime getClosingTime(){
        return this.closingTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getPercentageTax() {
        return tax;
    }

    public void setPercentageTax(double tax) {
        this.tax = tax;
    }

    public List<Shop> getNextShops() {
        return nextShops;
    }

    public boolean isTechnicalIssue() {
        return technicalIssue;
    }

    public void setTechnicalIssue(boolean technicalIssue) {
        this.technicalIssue = technicalIssue;
    }

    public List<Order> getPurchasedOrders() {
        return purchasedOrders;
    }

    public Map<Long, DaySaleSchedule> getSchedule() {
        return schedule;
    }

    public void addPurchasedOrder(Order purchasedOrder) {
        this.purchasedOrders.add(purchasedOrder);
        this.orderSupervisor.addOrderToPrepare(purchasedOrder);
    }

    public OrderSupervisor getOrderSupervisor() {
        return orderSupervisor;
    }

    public CookieRecipe getCookieBestOfOfCookieFactory() {
        return cookieBestOfOfCookieFactory;
    }

    public void setCookieBestOfOfCookieFactory(CookieRecipe cookieBestOfOfCookieFactory) {
        this.cookieBestOfOfCookieFactory = cookieBestOfOfCookieFactory;
    }

    public boolean isPickUpTimeAvailable(long dayMilliseconds, LocalTime pickUpTime){
        return schedule.get(dayMilliseconds) !=null &&
                schedule.get(dayMilliseconds).isTimeSlotAvailable(pickUpTime);
    }

    public boolean validatePickUpDayTime(Date pickUpDate, LocalTime pickUpTime){
        if(this.isTechnicalIssue()) return false;
        pickUpDate= Calculator.getTimeCalendarMidnightOfTheDay(pickUpDate).getTime();
        return this.getOpeningTime() != null && pickUpTime.isAfter(this.getOpeningTime())
                && pickUpTime.isBefore(this.getClosingTime())
                && this.isPickUpTimeAvailable(pickUpDate.getTime() , pickUpTime);
    }

    public List<Order> ordersFromTheLast30Days(){
        List<Order> newList = new ArrayList<>();
        Calendar calendar30DaysBefore = Calculator.getTimeCalendarMidnightOfTheDay(new Date());
        calendar30DaysBefore.add(Calendar.DAY_OF_MONTH,-30);
        for (Order o : purchasedOrders){
            if (o.getPickUpDay().getTime() >= calendar30DaysBefore.getTime().getTime() && o.isPaymentDone()){
                newList.add(o);
            }
        }
        return newList;
    }

    public CookieRecipe setGetMostPopularCookie(){
        List<Order> orders = ordersFromTheLast30Days();
        if (orders.isEmpty()) return null;
        return Calculator.getMostpopularCookie(Calculator.fromCookieOrderListToCookieOrderMap(purchasedOrders));
    }

    public void scheduleTimeSlot(Date pickUpDay, LocalTime pickUpTime) {
        long dateInMilliseconds = pickUpDay.getTime();
        this.schedule.get(dateInMilliseconds).setTimeSlot(pickUpTime);
    }

    /**
     *
     * The param @Long below is used to determine the day when the command will be pick up
     *
     */

    public Map<Long, DaySaleSchedule> setUpSchedule(LocalTime openingTime, LocalTime closingTime){
        Map<Long, DaySaleSchedule> schedule = new HashMap<>();
        Calendar cal = Calculator.getTimeCalendarMidnightOfTheDay(new Date());
        long dateCursor = cal.getTimeInMillis();

        Calendar calEndDate = Calculator.getTimeCalendarMidnightOfTheDay(new Date());
        calEndDate.add(Calendar.DAY_OF_MONTH,31);
        long endTime = calEndDate.getTimeInMillis();

        while(dateCursor <= endTime){
            schedule.put(dateCursor, new DaySaleSchedule(openingTime, closingTime));
            cal.add(Calendar.DAY_OF_MONTH,1);
            dateCursor = cal.getTimeInMillis();
        }

        return schedule;
    }

    public Shop closeShopAvailable(Order o, Date date, LocalTime pickUpTime){
        date = Calculator.getTimeCalendarMidnightOfTheDay(date).getTime();
        for (Shop s: nextShops) {
            if(s.getOpeningTime() != null && pickUpTime.isAfter(s.getOpeningTime())
                    && schedule.get(date.getTime()) !=null
                    && schedule.get(date.getTime()).getAvailabilities().get(pickUpTime).equals("AVAILABLE")
                    && !s.isTechnicalIssue()
                    && s.canDoTheOrder(o))
                return s;
        }
        return null;
    }

    public boolean canDoTheOrder(Order order){
        Map<Dough, Integer> doughsDose = new EnumMap<>(Dough.class);
        Map<Flavor, Integer> flavorsDose = new EnumMap<>(Flavor.class);
        Map<Topping, Integer> toppingsDose = new EnumMap<>(Topping.class);

        for(OrderLine ol : order.getOrderLines()){
            CookieRecipe cr = ol.getCookieRecipe();
            int numberOfCookies = ol.getQuantity();

            for(Dough dough : cr.getDoughs().keySet())
                doughsDose.merge(dough, cr.getDoughs().get(dough)*numberOfCookies, Integer::sum);
            for(Flavor flavor : cr.getFlavors().keySet())
                flavorsDose.merge(flavor, cr.getFlavors().get(flavor)*numberOfCookies, Integer::sum);
            for(Topping topping : cr.getToppings().keySet())
                toppingsDose.merge(topping, cr.getToppings().get(topping)*numberOfCookies, Integer::sum);
        }

        return this.storage.hasEnoughIngredients(doughsDose, flavorsDose, toppingsDose);
    }

    public void consumeIngredientsOfTheOrder(Order order){
        for(OrderLine ol : order.getOrderLines()){
            CookieRecipe cr = ol.getCookieRecipe();
            int numberOfCookies = ol.getQuantity();
            for(Dough dough : cr.getDoughs().keySet())
                this.storage.consumeDoughIngredient(dough, cr.getDoughs().get(dough)*numberOfCookies);
            for(Flavor flavor : cr.getFlavors().keySet())
                this.storage.consumeFlavorIngredient(flavor, cr.getFlavors().get(flavor)*numberOfCookies);
            for(Topping topping : cr.getToppings().keySet())
                this.storage.consumeToppingIngredient(topping, cr.getToppings().get(topping)*numberOfCookies);
        }
    }

}
