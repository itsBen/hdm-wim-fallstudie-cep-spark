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
        /**
         * Speech tokenization origin.
         */
        SPEECH_TOKENIZATION,
        /**
         * Machine learning origin.
         */
        MACHINE_LEARNING,
        /**
         * User interface origin.
         */
        USER_INTERFACE,
        /**
         * Semantische represenation origin.
         */
        SEMANTISCHE_REPRESENATION,
        /**
         * Event origin.
         */
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
        /**
         * Topic 1 topic.
         */
        TOPIC_1,
        /**
         * Topic 2 topic.
         */
        TOPIC_2,
        /**
         * Topic 3 topic.
         */
        TOPIC_3,
        /**
         * Topic 4 topic.
         */
        TOPIC_4,
        /**
         * Topic 5 topic.
         */
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
        /**
         * Action event type.
         */
        ACTION,
        /**
         * Request event type.
         */
        REQUEST,
        /**
         * User event type.
         */
        USER,
        /**
         * Feedback event type.
         */
        FEEDBACK,
        /**
         * Time event type.
         */
        TIME,
        /**
         * Date event type.
         */
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