package fr.unice.polytech.thecookiefactory.objects;

import java.time.LocalTime;
import java.util.*;

public class DaySaleSchedule {

    static final String SLOT_AVAILABLE = "AVAILABLE";
    static final String SLOT_UNAVAILABLE = "UNAVAILABLE";

    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private final Map<LocalTime, String> availabilities;


    public DaySaleSchedule(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.availabilities = fillAvailabilities();
    }

    public LocalTime calculateFirstSlot(){
        LocalTime firstSlot = openingTime.minusMinutes(openingTime.getMinute());
        while(firstSlot.isBefore(openingTime)){
            firstSlot = firstSlot.plusMinutes(30);
        }
        return firstSlot;
    }

    public LocalTime calculateLastSlot(){
        LocalTime lastSlot = closingTime.plusMinutes((long)60 - closingTime.getMinute());
        while(lastSlot.isAfter(closingTime)){
            lastSlot = lastSlot.minusMinutes(30);
        }
        return lastSlot;
    }

    public Map<LocalTime, String> fillAvailabilities(){
        HashMap<LocalTime, String> localAvailabilities = new HashMap<>();
        LocalTime timeCursor = LocalTime.of(calculateFirstSlot().getHour(), calculateFirstSlot().getMinute());
        while(timeCursor.isBefore(calculateLastSlot())){
            localAvailabilities.put(timeCursor, SLOT_AVAILABLE);
            timeCursor = timeCursor.plusMinutes(30);
        }
        return localAvailabilities;
    }

    public Map<LocalTime, String> getAvailabilities() {
        return availabilities;
    }

    public void setTimeSlot(LocalTime slotTime){
        availabilities.put(slotTime, SLOT_UNAVAILABLE);
    }

    public boolean isTimeSlotAvailable(LocalTime slotTime){
        return this.availabilities.get(slotTime).equals(SLOT_AVAILABLE);
    }
}
