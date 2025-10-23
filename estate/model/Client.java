package ua.estate.model;

import ua.estate.util.Utils;
import java.util.Objects;

public class Client extends Person {
    private String email;

    public Client(String firstName, String lastName, String email) {
        super(firstName, lastName);
        setEmail(email);
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        Utils.validateEmail(email); 
        this.email = email;
    }

    public String toString() {
        return "Client{" +
               super.toString() +
               ", email='" + email + '\'' +
               '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return Objects.equals(email, client.email);
    }
  
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
}
