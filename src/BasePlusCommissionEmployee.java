import java.security.InvalidParameterException;
import java.util.Objects;

public class BasePlusCommissionEmployee extends CommissionEmployee {
    float baseSalary;

    public BasePlusCommissionEmployee(String firstName, String lastName, int ID, float grossSales, int commision, float baseSalary) {
        super(firstName, lastName, ID, grossSales, commision);
        if (baseSalary < 0)
            throw new InvalidParameterException();
        this.baseSalary = baseSalary;
    }

    public BasePlusCommissionEmployee() {
        super();
        baseSalary = 0;
    }

    @Override
    public double earnings() {
        return baseSalary + super.earnings();
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        if (baseSalary < 0)
            throw new InvalidParameterException();
        this.baseSalary = baseSalary;
    }

    @Override
    public String toString() {
        return "BasePlusCommissionEmployee{" +
                "baseSalary=" + baseSalary +
                ", grossSales=" + grossSales +
                ", commision=" + commision +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID=" + ID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BasePlusCommissionEmployee that = (BasePlusCommissionEmployee) o;
        return Float.compare(that.baseSalary, baseSalary) == 0;
    }
}