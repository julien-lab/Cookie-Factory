package fr.unice.polytech.thecookiefactory;

import fr.unice.polytech.thecookiefactory.ingredients.*;
import fr.unice.polytech.thecookiefactory.objects.*;
import fr.unice.polytech.thecookiefactory.objects.account.*;

import java.time.LocalTime;
import java.util.*;

public class TheCookieFactory {

    private final List<Shop> shops;
    private final List<ClientAccount> clientAccounts;
    private final List<CookieRecipe> recipes;
    private final NationalManager nationalManager;

    public TheCookieFactory(){
        shops = new ArrayList<>();
        clientAccounts = new ArrayList<>();
        recipes = new ArrayList<>();
        this.nationalManager = new NationalManager(this);
        initializeShops();
        initializeCookies();
    }

    public List<CookieRecipe> getRecipes() {
        return recipes;
    }

   public CookieRecipe getRecipeByName(String recipeName) {
        for (CookieRecipe recipe : this.recipes)
            if(recipe.getName().equals(recipeName))
                return recipe;
        return null;
    }

    public NationalManager getNationalManager() {
        return nationalManager;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public List<ClientAccount> getClientAccounts() {
        return clientAccounts;
    }

    public List<Order> getOrdersOfAllShops() {
        List<Order> ordersOfAllShops = new ArrayList<>();
        for(Shop s : shops){
            ordersOfAllShops.addAll(s.getPurchasedOrders());
        }
        return ordersOfAllShops;
    }

    private void initializeShops(){
        shops.add(new Shop("New York", LocalTime.of(8,30), LocalTime.of(19,30), 0.11));
        shops.add(new Shop("San Francisco", LocalTime.of(9,30), LocalTime.of(20,30), 0.11));
        shops.add(new Shop("Chicago", LocalTime.of(8,0), LocalTime.of(20,3), 0.11));
        shops.add(new Shop("Los Angeles", LocalTime.of(7,30), LocalTime.of(19,0), 0.11));
        shops.add(new Shop("Philadelphia", LocalTime.of(10,0), LocalTime.of(20,30), 0.11));
        shops.add(new Shop("Washington", LocalTime.of(9,0), LocalTime.of(18,30), 0.11));

        shops.get(0).getNextShops().add(shops.get(4));
        shops.get(0).getNextShops().add(shops.get(5));
        shops.get(4).getNextShops().add(shops.get(0));

        for (Shop s : shops){
            s.setUpSchedule(s.getOpeningTime(),s.getClosingTime());
        }
    }

    private void initializeCookies(){
        recipes.add(new CookieRecipe("Chocolate / Chili", Dough.CHOCOLATE, Flavor.CHILI, new ArrayList<>(Collections.singletonList(Topping.MILK_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY));
        recipes.add(new CookieRecipe("Oatmeal / Cinnamon", Dough.OATMEAL, Flavor.CINNAMON,new ArrayList<>(Collections.singletonList(Topping.MNMS)), Mix.TOPPED, Cooking.CRUNCHY));
        recipes.add(new CookieRecipe("Plain / Vanilla", Dough.PLAIN, Flavor.VANILLA, new ArrayList<>(Collections.singletonList(Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY));
        recipes.add(new CookieRecipe("Oatmeal / Vanilla", Dough.OATMEAL, Flavor.VANILLA, new ArrayList<>(Collections.singletonList(Topping.REESES_BUTTERCUP)), Mix.TOPPED, Cooking.CHEWY));
        recipes.add(new CookieRecipe("Peanut Butter / Cinnamon", Dough.PEANUT_BUTTER, Flavor.CINNAMON, new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.REESES_BUTTERCUP)), Mix.MIXED, Cooking.CRUNCHY));
        recipes.add(new CookieRecipe("Plain / Chili", Dough.PLAIN, Flavor.CHILI,new ArrayList<>(Arrays.asList(Topping.REESES_BUTTERCUP, Topping.MNMS)), Mix.TOPPED, Cooking.CHEWY));
        recipes.add(new CookieRecipe("Chocolate", Dough.CHOCOLATE, null , new ArrayList<>(Arrays.asList(Topping.MILK_CHOCOLATE, Topping.MNMS, Topping.WHITE_CHOCOLATE)), Mix.MIXED, Cooking.CHEWY));
    }

    public List<Order> retrieveAllOrdersFromTheLast30Days(){
        List<Order> orders = new ArrayList<>();
        Calendar calendar30DaysBefore = Calculator.getTimeCalendarMidnightOfTheDay(new Date());
        calendar30DaysBefore.add(Calendar.DAY_OF_MONTH,-30);
        for (Shop s : getShops()) {
            orders.addAll(s.ordersFromTheLast30Days());
        }
        return orders;
    }

    public CookieRecipe setMostPopularCookieOfTheCookieFactory(){
        List<Order> orders = retrieveAllOrdersFromTheLast30Days();
        if (orders.isEmpty()) return null;
        return Calculator.getMostpopularCookie(Calculator.fromCookieOrderListToCookieOrderMap(orders));
    }

    public void setDiscountsForCookiesBestOf(){
        CookieRecipe bestCookie = setMostPopularCookieOfTheCookieFactory();
        for (Shop s : getShops()){
            s.setGetMostPopularCookie();
            s.setCookieBestOfOfCookieFactory(bestCookie);
        }
    }

    public void createClientAccount(String accountName){
        ClientAccount ca = new ClientAccount(accountName);
        clientAccounts.add(ca);
    }

    public ClientAccount getClientAccount(String clientAccountName){
        for(ClientAccount ca : this.clientAccounts){
            if(ca.getAccountName().equals(clientAccountName)){
                return ca;
            }
        }
        return null;
    }

    public boolean recipeAlreadyExist(CookieRecipe aRecipe){
        for(CookieRecipe cookieRecipe : recipes ){
            if(cookieRecipe.hasSameDoughs(aRecipe) && cookieRecipe.hasSameFlavors(aRecipe)
                    && cookieRecipe.hasSameToppings(aRecipe))
                return true;
        }
        return false;
    }



}
