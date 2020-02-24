package HumanResources;

import Infrastructure.Security.EmployeeType;
import Infrastructure.Security.Permission;

import java.util.HashMap;

public enum EmployeeManagement implements IEmployeeManagement {
    instance;

    private HashMap<Integer, EmployeeEntry> employeeMap;

    @Override
    public void createRegisteredEmployee(String name, String type) {
        EmployeeType employeeType = null;
        Employee employee = null;

        for (EmployeeType emplType : EmployeeType.values()) {
            if (type.equals(emplType.toString())) {
                employeeType = emplType;
            }
        }

        switch (employeeType) {
            case HRAssistant:
                employee = new HRAssistant(name);
                break;
            case HRConsultant:
                employee = new HRConsultant(name);
                break;
            case HRHoD:
                employee = new HRHoD(name);
                break;
            case Receptionist:
                employee = new Receptionist(name);
                break;
            case Researcher:
                employee = new Researcher(name);
                break;
            case ScientificAssistant:
                employee = new ScientificAssistant(name);
                break;
            case SecurityOfficer:
                employee = new SecurityOfficer(name);
                break;
        }
        if (employee != null) employeeMap.put(employee.getId(), new EmployeeEntry(employee,employeeType));
    }

    @Override
    public String getEmployeeData(int employeeID) {
        return employeeMap.get(employeeID).toString();
    }

    @Override
    public Permission[] getEmployeePermissions(int employeeID) {
        EmployeeType type = employeeMap.get(employeeID).type;
        return getPermissions(type);
    }

    private Permission[] getPermissions(EmployeeType type) {
        Permission[] perm=null;
        switch (type) {
            case HRAssistant:
                perm=new Permission[]{Permission.readEmployeeData};
                break;
            case HRConsultant:
                perm=new Permission[]{Permission.readEmployeeData,Permission.writeEmployeeData};
                break;
            case HRHoD:
                perm=new Permission[]{Permission.readEmployeeData,Permission.writeEmployeeData};
                break;
            case Receptionist:
                break;
            case Researcher:
                perm=new Permission[]{Permission.Researcher, Permission.ControlCenter};
                break;
            case ScientificAssistant:
                perm=new Permission[]{Permission.ControlCenter};
                break;
            case SecurityOfficer:
                perm=new Permission[]{Permission.Security};
                break;
        }
        return perm;
    }

    private class EmployeeEntry{
        Employee employee;
        EmployeeType type;

        public EmployeeEntry(Employee employee, EmployeeType type) {
            this.employee = employee;
            this.type = type;
        }
    }


}
