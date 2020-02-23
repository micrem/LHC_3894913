package HumanResources;

import Infrastructure.Security.Permission;

public interface IEmployeeManagement {
    void createRegisteredEmployee(String name, String type);
    Permission[] getEmployeePermissions(int employeeID);
}
