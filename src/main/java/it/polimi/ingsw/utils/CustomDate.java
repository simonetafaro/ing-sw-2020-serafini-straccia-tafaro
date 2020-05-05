package it.polimi.ingsw.utils;

import java.io.Serializable;

public class CustomDate implements Serializable {

    private int day;
    private int month;
    private int year;


    public CustomDate (int day, int month, int year){
        this.day= day;
        this.month=month;
        this.year= year;
    }

    public CustomDate(){}
    //TODO: manage correct date
    public void setDay(int day) {
        this.day = day;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public void setYear(int year) {
        this.year = year;
    }

    //date1.compareDate(date2) -> return 1 if date1 is earlier than data2
    public int compareDate(CustomDate date){
        if(this.year>date.year)
            return 1;
        else {
            if (this.year<date.year)
                return -1;
        }

        if(this.month>date.month)
            return 1;
        else{
            if(this.month<date.month)
                return -1;
        }

        if(this.day>date.day)
            return 1;
        else {
            if(this.day<date.day)
                return -1;
        }

        return 0;
    }
}
