package by.javaguru.Server.model;

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

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "info='" + info + '\'' +
                ", employees=" + employees +
                '}';
    }
}
