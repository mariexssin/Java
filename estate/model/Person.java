package ua.estate.model;

import ua.estate.util.Utils;
import java.util.Objects;
public abstract class Person {

    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setFirstName(String firstName) {
        Utils.validateString(firstName, "Ім'я");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        Utils.validateString(lastName, "Прізвище");
        this.lastName = lastName;
    }

    public String toString() {
        return "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
               Objects.equals(lastName, person.lastName);
    }

    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
