package it.polimi.ingsw.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomDateTest {


    @Test
    void compareDate() {

        CustomDate date1 = new CustomDate();
        CustomDate date2 = new CustomDate();

        //date1: previous year than date2
        date1.setDay(23);
        date1.setMonth(30);
        date1.setYear(3);

        date2.setDay(23);
        date2.setMonth(30);
        date2.setYear(4);

        assertEquals(date1.compareDate(date1),0);
        assertEquals(date2.compareDate(date1),1);
        assertEquals(date1.compareDate(date2),-1);

        //date1: previous day than date2
        date1.setDay(1);
        date1.setMonth(2);
        date1.setYear(3);

        date2.setDay(2);
        date2.setMonth(2);
        date2.setYear(3);

        assertEquals(date1.compareDate(date1),0);
        assertEquals(date2.compareDate(date1),1);
        assertEquals(date1.compareDate(date2),-1);

        //date2: previous month than date2
        date1.setDay(1);
        date1.setMonth(3);
        date1.setYear(3);

        date2.setDay(1);
        date2.setMonth(2);
        date2.setYear(3);

        assertEquals(date1.compareDate(date1),0);
        assertEquals(date2.compareDate(date1),-1);
        assertEquals(date1.compareDate(date2),1);

    }
}