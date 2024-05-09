package model.Datastructure;

import java.util.List;

public class NewEventHandler {
    private List<NewEvent> eventList;

    private static NewEventHandler instance;
    private NewEventHandler(List<NewEvent> eventList){
        this.eventList = eventList;
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
    }

    public List<NewEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<NewEvent> eventList) {
        this.eventList = eventList;
    }
}
