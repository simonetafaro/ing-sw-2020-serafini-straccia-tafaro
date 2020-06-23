package it.polimi.ingsw.utils;

public class ColorCheck {

    private boolean Grey, White, Blue;
    private int counter;

    public ColorCheck(boolean grey, boolean white, boolean blue) {
        this.Grey = grey;
        this.White = white;
        this.Blue = blue;
        this.counter = 0;
    }

    public synchronized boolean isBlue() {
        return Blue;
    }
    public synchronized boolean isGrey() {
        return Grey;
    }
    public synchronized boolean isWhite() {
        return White;
    }

    public void setGrey(boolean grey) {
        Grey = grey;
        counter++;
    }
    public void setWhite(boolean white) {
        White = white;
        counter++;
    }
    public void setBlue(boolean blue) {
        Blue = blue;
        counter++;
    }

}
