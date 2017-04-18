package de.hdm.wim.resources;

/**
 * Created by ben on 17/04/2017.
 */
public class Participant {

    private String _firstName;
    private String _lastName;
    private String _role;

    /**
     * Instantiates a new Participant.
     *
     * @param firstName first name
     * @param lastName  last name
     * @param role      role of the user
     */
    public Participant(String firstName, String lastName, String role) {
        this._firstName = firstName;
        this._lastName  = lastName;
        this._role      = role;
    }

    /**
     * Instantiates a new Participant.
     */
    public Participant() {
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return _firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return _lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this._lastName = lastName;
    }
}