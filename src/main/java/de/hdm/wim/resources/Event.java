package de.hdm.wim.resources;

import de.hdm.wim.resources.Enums;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ben on 16/04/2017.
 */
public class Event {
    private int                 _eventId;
    private Enums.EventType     _eventType;
    private Enums.Origin        _eventSource;
    private String              _payload;
    //private Participant         _sender;
    private LocalDateTime       _timestamp;

    /**
     * Instantiates a new Message event.
     *
     * @param eventId     the eventId
     * @param eventType   the eventType
     * @param eventSource the eventSource / component which created the event
     *
     * @param timestamp   the timestamp of creation
     * @param payload     the actual payload TODO:
     */
    public Event(int eventId, Enums.EventType eventType, Enums.Origin eventSource, LocalDateTime timestamp, String payload) {
        this._eventId       = eventId;
        this._eventSource   = eventSource;
        this._eventType     = eventType;
        this._payload       = payload;
        //this._sender        = sender;
        this._timestamp     = timestamp;
    }

    /**
     * Generate event event.
     *
     * @param payload the payload
     * @return the event
     */
    public static Event GenerateEvent( String payload){

        final Random random = new Random();

        int eventId                 = random.nextInt();
        Enums.EventType eventType   = Enums.EventType.GetRandom();
        Enums.Origin eventSource    = Enums.Origin.GetRandom();
        LocalDateTime timestamp     = LocalDateTime.now();

        return new Event(eventId, eventType, eventSource, timestamp, payload);
    }


    /**
     * get the event id
     *
     * @return int event id
     */
    public int get_eventId() {
        return _eventId;
    }

    /**
     * Gets event type.
     *
     * @return the event type
     */
    public Enums.EventType get_eventType() {
        return _eventType;
    }

    /**
     * Gets event source.
     *
     * @return the event source
     */
    public Enums.Origin get_eventSource() {
        return _eventSource;
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String get_payload() {
        return _payload;
    }



    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime get_timestamp() {
        return _timestamp;
    }

    /**
     * Sets event id.
     *
     * @param _eventId the event id
     */
    public void set_eventId(int _eventId) {
        this._eventId = _eventId;
    }

    /**
     * Sets event type.
     *
     * @param _eventType the event type
     */
    public void set_eventType(Enums.EventType _eventType) {
        this._eventType = _eventType;
    }

    /**
     * Sets event source.
     *
     * @param _eventSource the event source
     */
    public void set_eventSource(Enums.Origin _eventSource) {
        this._eventSource = _eventSource;
    }

    /**
     * Sets payload.
     *
     * @param _payload the payload
     */
    public void set_payload(String _payload) {
        this._payload = _payload;
    }


    /**
     * Sets timestamp.
     *
     * @param _timestamp the timestamp
     */
    public void set_timestamp(LocalDateTime _timestamp) {
        this._timestamp = _timestamp;
    }
}
