import HumanResources.Employee;

import Infrastructure.Security.IDCard.IROIDCardMultichip;

import java.util.HashMap;

public enum EmployeeManagement {
    INSTANCE;
    private HashMap<Integer, Employee> employeeMap;
    private HashMap<Integer, IROIDCardMultichip> idCardHashMap;

    public void createEmployee(String name){

    }

}
