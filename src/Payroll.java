public class Payroll {
    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        employees[0] = new HourlyEmployee("ani", "aaa", 123456789, 40, 30);
        employees[1] = new CommissionEmployee("hgfdfgh", "dfghgrtyuy", 987654321, 10000.5f, 20);
        employees[2] = new BasePlusCommissionEmployee("iutyr", "cnb", 123459876, 60, 8, 1000);

        for (Employee employee : employees) {
            System.out.println(employee.toString());
            if (employee instanceof BasePlusCommissionEmployee)
                System.out.println(employee.earnings() * 1.1);
            else
                System.out.println(employee.earnings());
        }
    }
}