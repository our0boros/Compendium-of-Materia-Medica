package model.Datastructure;

import java.util.ArrayList;
import java.util.List;
/**
 * @author: Xing Chen
 * @uid: u7725171
 * @description:
 * Handle events.
 * Singleton and observer pattern used here.
 */
public class NewEventHandler {
    private List<NewEvent> eventList;
    private List<EventObserver> observers = new ArrayList<>();
    private int unreadNotifications;
    private static NewEventHandler instance;
    private NewEventHandler(List<NewEvent> eventList){
        this.eventList = eventList;
        this.unreadNotifications = eventList.size();
    }
    public static synchronized NewEventHandler getInstance(List<NewEvent> eventList){
        if(instance == null){
            instance = new NewEventHandler(eventList);
        }
        return instance;
    }
    public static NewEventHandler getInstance(){
        if(instance == null){
            throw new IllegalStateException("Instance not created. Call getInstance(List<NewEvent>) first.");
        }
        return instance;
    }

    public void addEvent(NewEvent event){
        this.eventList.add(event);
        this.unreadNotifications++;
        notifyObservers();
    }
    public void removeEvent(NewEvent event) {
        this.eventList.remove(event);
        if (this.unreadNotifications > 0) {
            this.unreadNotifications--;
        }
        notifyObservers();
    }

    public List<NewEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<NewEvent> eventList) {
        this.eventList = eventList;
    }
    public interface EventObserver {
        void onEventChanged();
    }
    public void addObserver(EventObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (EventObserver observer : observers) {
            observer.onEventChanged();
        }
    }
    public void markAllEventsAsRead() {
        this.unreadNotifications = 0;
        this.eventList.clear();
        notifyObservers();
    }
    public int getUnreadNotifications() {
        return unreadNotifications;
    }
}
