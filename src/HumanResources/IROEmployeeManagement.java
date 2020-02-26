package HumanResources;

import Infrastructure.Security.Permission;

public interface IROEmployeeManagement {
    String getEmployeeData(int employeeID);

    Permission[] getEmployeePermissions(int employeeID);
}
