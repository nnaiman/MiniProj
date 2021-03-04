import java.security.InvalidParameterException;
import java.util.Objects;

public class CommissionEmployee extends Employee {
    float grossSales;
    int commision;

    public CommissionEmployee(String firstName, String lastName, int ID, float grossSales, int commision) {
        super(firstName, lastName, ID);
        this.grossSales = grossSales;
        this.commision = commision;
    }

    public CommissionEmployee() {
        super();
        grossSales = 0;
        commision = 0;
    }

    @Override
    public double earnings() {
        return grossSales * commision / 100;
    }

    public float getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(float grossSales) {
        if (grossSales < 0)
            throw new InvalidParameterException();
        this.grossSales = grossSales;
    }

    public int getCommision() {
        return commision;
    }

    public void setCommision(int commision) {
        if (commision < 0)
            throw new InvalidParameterException();
        this.commision = commision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommissionEmployee that = (CommissionEmployee) o;
        return Float.compare(that.grossSales, grossSales) == 0 && commision == that.commision;
    }

    @Override
    public String toString() {
        return "CommissionEmployee{" +
                "grossSales=" + grossSales +
                ", commision=" + commision +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID=" + ID +
                '}';
    }
}
