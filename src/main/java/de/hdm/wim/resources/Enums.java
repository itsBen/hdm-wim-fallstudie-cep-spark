package de.hdm.wim.resources;

import java.util.Random;

/**
 * Created by ben on 17/04/2017.
 */
public class Enums {

    /**
     * The enum Origin.
     */
    public enum Origin {
        SPEECH_TOKENIZATION,
        MACHINE_LEARNING,
        USER_INTERFACE,
        SEMANTISCHE_REPRESENATION,
        EVENT;

        /**
         * Pick a random value of the Origin enum.
         *
         * @return a random Origin.
         */
        public static Origin GetRandom() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    /**
     * The enum Topic.
     */
    public enum Topic {
        TOPIC_1,
        TOPIC_2,
        TOPIC_3,
        TOPIC_4,
        TOPIC_5;

        /**
         * Pick a random value of the Topic enum.
         *
         * @return a random Topic.
         */
        public static Topic GetRandom() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    /**
     * The enum Event type.
     */
    public enum EventType {
        ACTION,
        REQUEST,
        USER,
        FEEDBACK,
        TIME,
        DATE;

        //TODO: unify GetRandom Methods

        /**
         * Pick a random value of the EventType enum.
         *
         * @return a random EventType.
         */
        public static EventType GetRandom() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

}