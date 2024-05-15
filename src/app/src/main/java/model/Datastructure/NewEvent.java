package model.Datastructure;

import java.util.Date;

public class NewEvent {
    private int postId;
    private User eventUser;
    private EventType eventType;
    private Date eventTime;

    public enum EventType{
        LIKE, COMMENT, POST
    }

    public NewEvent(int postId, User eventUser, EventType eventType, Date eventTime) {
        this.postId = postId;
        this.eventUser = eventUser;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public int getPostId() {
        return postId;
    }

    public User getEventUser() {
        return eventUser;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }
}
