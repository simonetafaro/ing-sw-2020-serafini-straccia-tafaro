package it.polimi.ingsw.observ;

public interface Observer<T> {

    public void update(T message);
}
