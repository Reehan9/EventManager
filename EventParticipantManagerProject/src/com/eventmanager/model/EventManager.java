package com.eventmanager.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * EventManager class manages the participants of an event.
 * It includes all CRUD operations (create, read, update, delete) in a single place.
 */
public class EventManager {
    
    // List to store Participant objects
    private List<Participant> participants;

    // Constructor initializes the participant list
    public EventManager() {
        participants = new ArrayList<>();
    }

    // CREATE: Adds a participant to the event
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    // READ: Retrieves all participants
    public List<Participant> getAllParticipants() {
        return participants;
    }
    
    // UPDATE: Updates participant by matching email
    public boolean updateParticipant(String email, String newName, String newEventName) {
        for (Participant p : participants) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                p.setName(newName);
                p.setEventName(newEventName);
                return true;
            }
        }
        return false;
    }

    // DELETE: Removes participant by matching email
    public boolean deleteParticipant(String email) {
        Iterator<Participant> iterator = participants.iterator();
        while (iterator.hasNext()) {
            Participant p = iterator.next();
            if (p.getEmail().equalsIgnoreCase(email)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // SEARCH: Finds participants whose name contains the query (case-insensitive)
    public List<Participant> searchParticipant(String name) {
        List<Participant> result = new ArrayList<>();
        for (Participant p : participants) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }
}
