package HumanResources;

import Infrastructure.Security.EmployeeType;

class EmployeeEntry {
    public Employee employee;
    public EmployeeType type;

    public EmployeeEntry(Employee employee, EmployeeType type) {
        this.employee = employee;
        this.type = type;
    }
}