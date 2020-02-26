package HumanResources;

public interface IEmployeeManagement extends IROEmployeeManagement {
    Employee createRegisteredEmployee(String name, String type);

    Employee getEmployee(int employeeID);
}
