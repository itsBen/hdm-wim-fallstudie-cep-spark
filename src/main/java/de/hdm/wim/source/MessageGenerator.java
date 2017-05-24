package de.hdm.wim.source;

import de.hdm.wim.resources.Enums;
import de.hdm.wim.resources.Event;
//import de.hdm.wim.resources.Message;
//import de.hdm.wim.resources.Participant;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ben on 17/04/2017.
 */
public class MessageGenerator {

    //private ArrayList<Participant> _senders = new ArrayList<>();
    private final Random random             = new Random();

    /**
     * Instantiates a new Message generator.
     *
      */
    public MessageGenerator() {
        //_senders = senders;
    }

    /**
     * Generate message message.
     *
     * @return the message
     */
 /*   public Message GenerateMessage(){
        //int sendersCount = _senders.size();

        Event event = Event.GenerateEvent("payload");

        return new Message(random.nextInt(), Enums.Origin.GetRandom(),Enums.Topic.GetRandom(),event);
    }*/
}
