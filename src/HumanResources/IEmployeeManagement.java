package HumanResources;

import Infrastructure.Security.Permission;

public interface IEmployeeManagement extends IROEmployeeManagement{
    void createRegisteredEmployee(String name, String type);
}
