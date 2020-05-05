package it.polimi.ingsw.utils;

public enum PlayerColor {
    GREY, WHITE, BLUE;

    private boolean Grey, White, Blue;

    public synchronized boolean isBlue() {
        return Blue;
    }
    public synchronized boolean isGrey() {
        return Grey;
    }
    public synchronized boolean isWhite() {
        return White;
    }

}
