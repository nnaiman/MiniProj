import java.security.InvalidParameterException;
import java.util.Objects;

public class HourlyEmployee extends Employee {
    int hoursInWeek;
    float wage;

    public HourlyEmployee(String firstName, String lastName, int ID, int hoursInWeek, float wage) {
        super(firstName, lastName, ID);
        this.hoursInWeek = hoursInWeek;
        this.wage = wage;
    }

    public HourlyEmployee() {
        super();
        hoursInWeek = 0;
        wage = 0;
    }

    @Override
    public double earnings() {
        return hoursInWeek * wage;
    }

    public int getHoursInWeek() {
        return hoursInWeek;
    }

    public void setHoursInWeek(int hoursInWeek) {
        if (hoursInWeek < 0)
            throw new InvalidParameterException();
        this.hoursInWeek = hoursInWeek;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        if (wage < 0)
            throw new InvalidParameterException();
        this.wage = wage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HourlyEmployee that = (HourlyEmployee) o;
        return hoursInWeek == that.hoursInWeek && Float.compare(that.wage, wage) == 0;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID=" + ID +
                ", hoursInWeek=" + hoursInWeek +
                ", wage=" + wage +
                '}';
    }
}