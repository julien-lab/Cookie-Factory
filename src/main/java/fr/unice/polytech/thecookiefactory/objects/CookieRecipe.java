package fr.unice.polytech.thecookiefactory.objects;

import fr.unice.polytech.thecookiefactory.Calculator;
import fr.unice.polytech.thecookiefactory.ingredients.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CookieRecipe {

   private String name;
   private Map<Dough, Integer> doughs = new EnumMap<>(Dough.class);
   private Map<Flavor, Integer> flavors = new EnumMap<>(Flavor.class);
   private Map<Topping, Integer> toppings = new EnumMap<>(Topping.class);
   private Mix mix;
   private Cooking cooking;
   private double price;
   private final boolean customized;

  public CookieRecipe(String name, Dough dough, Flavor flavor, List<Topping> toppings, Mix mix, Cooking cooking) {
     this.name = name;
     this.customized = false;
     if(dough != null){
         this.doughs.put(dough,1);
     }
     if(flavor != null){
         this.flavors.put(flavor,1);
     }
     for(Topping t : toppings){
         if(t != null) {
             this.toppings.put(t, 1);
         }
     }
     this.mix = mix;
     this.cooking = cooking;
     this.calculatePrice();
  }

  public CookieRecipe(String name, Mix mix, Cooking cooking){
     this.customized = true;
     this.name = name;
     this.mix = mix;
     this.cooking = cooking;
  }

   public void calculatePrice(){
      double doughsPrice = 0;
      double flavorsPrice = 0;
      double toppingsPrice = 0;
      for(Dough dough : this.doughs.keySet())
         doughsPrice += dough.getPrice()*doughs.get(dough);
      for(Flavor flavor : this.flavors.keySet())
         flavorsPrice += flavor.getPrice()*flavors.get(flavor);
      for(Topping topping : this.toppings.keySet())
         toppingsPrice += topping.getPrice()*toppings.get(topping);
      this.price = Calculator.round(doughsPrice+ flavorsPrice + toppingsPrice, 2);
      if(customized){
          this.price = Calculator.round(this.price*1.25, 2);
      }
   }

    public boolean isCustomized() {
        return customized;
    }

    public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Map<Dough, Integer> getDoughs() {
      return this.doughs;
   }

   public void setDoughs(Map<Dough,Integer> doughs) {
      this.doughs = doughs;
   }

   public Map<Flavor, Integer> getFlavors() {
      return this.flavors;
   }

   public void setFlavors(Map<Flavor,Integer> flavors) {
      this.flavors = flavors;
   }

   public Map<Topping, Integer> getToppings() {
      return this.toppings;
   }

   public void setToppings(Map<Topping, Integer> toppings) {
      this.toppings = toppings;
   }

   public Mix getMix() {
      return mix;
   }

   public void setMix(Mix mix) {
      this.mix = mix;
   }

   public Cooking getCooking() {
      return cooking;
   }

   public void setCooking(Cooking cooking) {
      this.cooking = cooking;
   }

   public double getPrice() {
      calculatePrice();
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

    public boolean hasSameDoughs(CookieRecipe aRecipe) {
       if(this.getDoughs().size() != aRecipe.getDoughs().size())
           return false;
       int commonNumberOfDoughs = 0;
       for(Dough dough : this.getDoughs().keySet()) {
           for (Dough d : aRecipe.getDoughs().keySet()) {
               if(dough.equals(d) && this.getDoughs().get(dough).equals(aRecipe.getDoughs().get(d))) {
                   commonNumberOfDoughs += 1;
                   break;
               }
            }
        }
        return (commonNumberOfDoughs == this.getDoughs().size()) ;
    }

    public boolean hasSameFlavors(CookieRecipe aRecipe) {
        if(this.getFlavors().size() != aRecipe.getFlavors().size())
            return false;
        int commonNumberOfFlavors = 0;
        for(Flavor flavor : this.getFlavors().keySet()) {
            for (Flavor f : aRecipe.getFlavors().keySet()) {
                if(flavor.equals(f) && this.getFlavors().get(flavor).equals(aRecipe.getFlavors().get(f))) {
                    commonNumberOfFlavors += 1;
                    break;
                }
            }
        }
        return (commonNumberOfFlavors == this.getFlavors().size()) ;
    }

    public boolean hasSameToppings(CookieRecipe aRecipe) {
        if(this.getToppings().size() != aRecipe.getToppings().size())
            return false;
        int commonNumberOfToppings = 0;
        for(Topping topping : this.getToppings().keySet()) {
            for(Topping t: aRecipe.getToppings().keySet()) {
                if(topping.equals(t) && this.getToppings().get(topping).equals(aRecipe.getToppings().get(t))) {
                    commonNumberOfToppings += 1;
                    break;
                }
            }
        }
        return (commonNumberOfToppings == this.getToppings().size()) ;
    }

    public boolean isValid() {
      if(customized){
          boolean verifyDoseOfDough = this.verifyDoseOfDough();
          return !this.doughs.isEmpty() && verifyDoseOfDough && this.mix != null && this.cooking != null;
      }else{
          return !this.doughs.isEmpty() &&  this.toppings.size() <= 3 && this.toppings.size() > 0;
      }
    }

    private boolean verifyDoseOfDough() {
      int nbDose =0;
      for(Dough d : this.doughs.keySet()){
          nbDose += this.doughs.get(d);
      }
      return nbDose>0;
    }

}