import HumanResources.Employee;
import Infrastructure.Security.IDCard.IDCard;
import Infrastructure.Security.IDCard.Reader;

import java.util.HashMap;

public enum  Management {
    INSTANCE;
    private Reader reader;
    private HashMap<Integer, Employee> employeeMap;
    private HashMap<Integer, IDCard> idCardHashMap;
    public void createEmployee(String name){}

    public void assignIDCard(IDCard idCard, Employee employee){}

    public void lockIDCard(IDCard idCard){}

    public void clearIDCard(IDCard idCard){}

}
