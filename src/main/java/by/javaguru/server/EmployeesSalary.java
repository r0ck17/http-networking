package by.javaguru.server;

import java.util.List;

public class EmployeesSalary {
    private String info;
    private List<Employee> employees;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getTotalIncome() {
        return employees.stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getTotalTax() {
        return employees.stream()
                .mapToInt(Employee::getTax)
                .sum();
    }

    public int getTotalProfit() {
        return employees.stream()
                .mapToInt(e -> e.getSalary() - e.getTax())
                .sum();
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "info='" + info + '\'' +
                ", employees=" + employees +
                '}';
    }
}
