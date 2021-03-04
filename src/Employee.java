import java.security.InvalidParameterException;
import java.util.Objects;

public abstract class Employee {
    String firstName;
    String lastName;
    int ID;

    public Employee(String firstName, String lastName, int ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
    }

    public Employee() {
        firstName = "plony";
        lastName = "almony";
        ID = 0;
    }

    //region setters and gettres
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setID(int ID) {
        if (ID < 100000000 || ID > 999999999)
            throw new InvalidParameterException();
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
    //endregion

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID=" + ID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return ID == employee.ID && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    public abstract double earnings();
}