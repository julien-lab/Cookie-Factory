package fr.unice.polytech.thecookiefactory.objects.account;

import fr.unice.polytech.thecookiefactory.Calculator;
import fr.unice.polytech.thecookiefactory.TheCookieFactory;
import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Shop;

import java.time.LocalTime;
import java.util.Map;

public class NationalManager extends EmployeeAccount {

    public final TheCookieFactory theCookieFactory;

    public NationalManager(TheCookieFactory theCookieFactory) {
        super("National Manager");
        this.theCookieFactory = theCookieFactory;
    }

    public TheCookieFactory getTheCookieFactory() {
        return theCookieFactory;
    }

    public void addNewCookieRecipe(CookieRecipe cr){
        if(!this.theCookieFactory.recipeAlreadyExist(cr) && cr.isValid()){
            this.theCookieFactory.getRecipes().add(cr);
        }
    }

    public void removeCookieRecipe(CookieRecipe cookieRecipe){
        CookieRecipe cookieRecipeToRemove = null;
        for(CookieRecipe cr : this.theCookieFactory.getRecipes()){
            if(cr.getName().equals(cookieRecipe.getName())){
                cookieRecipeToRemove = cr;
            }
        }
        if(cookieRecipeToRemove!=null){
            this.theCookieFactory.getRecipes().remove(cookieRecipeToRemove);
        }
    }

    public Map<CookieRecipe, Integer> getStatisticsOnNumberOfCookieOrdered(){
        return Calculator.fromCookieOrderListToCookieOrderMap(theCookieFactory.getOrdersOfAllShops());
    }

    public Map<LocalTime, Integer> getStatisticsOnOrdersPickUpTime(){
        return Calculator.getStatisticsOnOrdersPickUpTime(theCookieFactory.getOrdersOfAllShops());
    }

    public Shop getMostPopularShop(){
        return Calculator.getMostPopularShop(theCookieFactory.getOrdersOfAllShops());
    }


}
