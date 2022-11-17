package fr.unice.polytech.thecookiefactory;

import fr.unice.polytech.thecookiefactory.objects.CookieRecipe;
import fr.unice.polytech.thecookiefactory.objects.Order;
import fr.unice.polytech.thecookiefactory.objects.OrderLine;
import fr.unice.polytech.thecookiefactory.objects.Shop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.*;

public interface Calculator {

    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    static Shop getMostPopularShop(List<Order> orders){
        HashMap<Shop, Integer> hashMap = new HashMap<>();
        for (Order o : orders){
            hashMap.merge(o.getShop(),1,Integer::sum);
        }
        return getKey(hashMap, mapMaxValue(hashMap));
    }

    /**
     *
     * The function below is used to know the hour of the most popular slot throughout the days
     *
     */

    static Map<LocalTime, Integer> getStatisticsOnOrdersPickUpTime(List<Order> orders){
        HashMap<LocalTime, Integer> hashMap = new HashMap<>();
        for (Order o : orders){
            hashMap.merge(o.getPickUpTime(),1,Integer::sum);
        }
        return hashMap;
    }

    static Map<CookieRecipe, Integer> fromCookieOrderListToCookieOrderMap(List<Order> cookieOrderList){
        HashMap<CookieRecipe, Integer> cookieRecipeHashMap = new HashMap<>();
        for (Order o : cookieOrderList){
            for (OrderLine l : o.getOrderLines()){
                cookieRecipeHashMap.merge(l.getCookieRecipe(), l.getQuantity(), Integer::sum);
            }
        }
        return cookieRecipeHashMap;
    }

    static CookieRecipe getMostpopularCookie(Map<CookieRecipe, Integer> cookieRecipeHashMap){
        return getKey(cookieRecipeHashMap, mapMaxValue(cookieRecipeHashMap));
    }

    static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    static <K, V extends Comparable<V>> V mapMaxValue(Map<K, V> map) {
        Map.Entry<K, V> maxEntry = Collections.max(map.entrySet(), (Map.Entry<K, V> e1, Map.Entry<K, V> e2) -> e1.getValue()
                .compareTo(e2.getValue()));
        return maxEntry.getValue();
    }

    static Calendar getTimeCalendarMidnightOfTheDay(Date pickUpDay){
        Calendar cal = Calendar.getInstance();
        cal.setTime(pickUpDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

}
