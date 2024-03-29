package it.polimi.ingsw.observ;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    protected void notifyObserver(T message) {
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }


}
