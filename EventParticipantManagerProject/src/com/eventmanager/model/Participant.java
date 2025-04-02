package com.eventmanager.model;

/**
 * Participant class represents an event participant.
 * It encapsulates participant details such as name, email, and event name.
 */
public class Participant {
    private String name;
    private String email;
    private String eventName;

    // Constructor to initialize participant details
    public Participant(String name, String email, String eventName) {
        this.name = name;
        this.email = email;
        this.eventName = eventName;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for event name
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // Overridden toString method to display participant details
    @Override
    public String toString() {
        return "Name: " + name + ", Email: " + email + ", Event: " + eventName;
    }
}
