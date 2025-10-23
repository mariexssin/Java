package ua.estate.model;

import ua.estate.util.Utils;
import java.util.Objects;

public class Agent extends Person {
    private String contactInfo;

    public Agent(String firstName, String lastName, String contactInfo) {
        super(firstName, lastName); 
        setContactInfo(contactInfo);
    }
    public static Agent createAgent(String firstName, String lastName, String phone) {
        return new Agent(firstName, lastName, phone);
    }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) {
        Utils.validateString(contactInfo, "Контактна інформація");
        this.contactInfo = contactInfo;
    }

    public String toString() {
        return "Agent{" +
               super.toString() + 
               ", contactInfo='" + contactInfo + '\'' +
               '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; 
        Agent agent = (Agent) o;
        return Objects.equals(contactInfo, agent.contactInfo);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), contactInfo); // Використання HASH батька
    }
}
