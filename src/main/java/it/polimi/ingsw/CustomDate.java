package it.polimi.ingsw;

public class CustomDate {
    private int day;
    private int month;
    private int year;

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    //return 1 se la data su cui chiamo il metodo è antecedente a quella passata come metodo
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
