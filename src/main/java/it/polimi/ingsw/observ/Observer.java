package it.polimi.ingsw.observ;

public interface Observer<T> {

    void update(T message);
}
