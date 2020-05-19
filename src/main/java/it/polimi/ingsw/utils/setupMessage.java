package it.polimi.ingsw.utils;

import java.io.Serializable;

public class setupMessage implements Serializable {
    private String nickname;
    private CustomDate birthday;

    public setupMessage(String nickname, int day, int month, int year) {
        this.nickname = nickname;
        this.birthday = new CustomDate(day,month,year);
    }

    public String getNickname() {
        return nickname;
    }
}
