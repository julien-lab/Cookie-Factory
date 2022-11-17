package fr.unice.polytech.thecookiefactorytest.objectstest;

import fr.unice.polytech.thecookiefactory.objects.DaySaleSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DaySaleScheduleTest {

    public DaySaleSchedule schedule;

    @BeforeEach
    public void init() {
        schedule = new DaySaleSchedule(LocalTime.of(8,0),LocalTime.of(16,0));
    }

    @Test
    public void calculateFirstSlotTest(){
        assertEquals(schedule.calculateFirstSlot(), LocalTime.of(8,0));
        assertNotEquals(LocalTime.of(9,0),schedule.calculateFirstSlot());
        assertNotEquals(LocalTime.of(10,0),schedule.calculateFirstSlot());
        assertNotEquals(LocalTime.of(11,0),schedule.calculateFirstSlot());
        assertNotEquals(LocalTime.of(12,0),schedule.calculateFirstSlot());
        assertNotEquals(LocalTime.of(13,0),schedule.calculateFirstSlot());
        assertNotEquals(LocalTime.of(14,0),schedule.calculateFirstSlot());
    }

    @Test
    public void calculateLastSlotTest(){
        assertEquals(LocalTime.of(16,0),schedule.calculateLastSlot());
        assertNotEquals(LocalTime.of(9,0),schedule.calculateLastSlot());
        assertNotEquals(LocalTime.of(10,0),schedule.calculateLastSlot());
        assertNotEquals(LocalTime.of(11,0),schedule.calculateLastSlot());
        assertNotEquals(LocalTime.of(12,0),schedule.calculateLastSlot());
        assertNotEquals(LocalTime.of(13,0),schedule.calculateLastSlot());
        assertNotEquals(LocalTime.of(14,0),schedule.calculateLastSlot());
    }

    @Test
    public void fillAvailabilitiesTest(){
        schedule.fillAvailabilities();
        LocalTime timeCursor = LocalTime.of(8, 0);
        while(timeCursor.isBefore(LocalTime.of(16,0))){
            assertEquals("AVAILABLE",schedule.getAvailabilities().get(timeCursor));
            timeCursor = timeCursor.plusMinutes(30);
        }

    }

    @Test
    public void isTimeSlotAvailableTest(){
        schedule.fillAvailabilities();
        LocalTime timeCursor = LocalTime.of(8, 0);
        while(timeCursor.isBefore(LocalTime.of(16,0))){
            assertEquals("AVAILABLE",schedule.getAvailabilities().get(timeCursor));
            timeCursor = timeCursor.plusMinutes(30);
        }
        timeCursor = LocalTime.of(8, 0);
        while(timeCursor.isBefore(LocalTime.of(16,0))){
            schedule.setTimeSlot(timeCursor);
            timeCursor = timeCursor.plusMinutes(60);
        }
        timeCursor = LocalTime.of(8, 0);
        while(timeCursor.isBefore(LocalTime.of(16,0))){
            assertEquals("UNAVAILABLE",schedule.getAvailabilities().get(timeCursor));
            timeCursor = timeCursor.plusMinutes(60);
        }

    }
    
}
