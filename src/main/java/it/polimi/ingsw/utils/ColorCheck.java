package it.polimi.ingsw.utils;

/**
 * Class used for setup color
 */
public class ColorCheck {

    /**
     * Boolean attributes used for check if color is available or not
     */
    private boolean Grey, White, Blue;

    public ColorCheck(boolean grey, boolean white, boolean blue) {
        this.Grey = grey;
        this.White = white;
        this.Blue = blue;
    }

    /**
     * @return true if BLUE is been already chosen
     */
    public synchronized boolean isBlue() {
        return Blue;
    }
    /**
     * @return true if GREY is been already chosen
     */
    public synchronized boolean isGrey() {
        return Grey;
    }
    /**
     * @return true if WHITE is been already chosen
     */
    public synchronized boolean isWhite() {
        return White;
    }

    /**
     * @param grey
     * set Grey = true
     */
    public void setGrey(boolean grey) {
        Grey = grey;
    }
    /**
     * @param white
     * set White = true
     */
    public void setWhite(boolean white) {
        White = white;
    }
    /**
     * @param blue
     * set Blue = true
     */
    public void setBlue(boolean blue) {
        Blue = blue;
    }

}
