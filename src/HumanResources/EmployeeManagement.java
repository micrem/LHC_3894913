package HumanResources;

import java.util.HashMap;

public enum EmployeeManagement {
    instance;

    private HashMap<Integer, Employee> employeeMap;

    public void createEmployee(String name, String type){
        EmployeeType employeeType=null;
        Employee employee=null;

        for(EmployeeType emplType : EmployeeType.values()){
            if (type.equals(emplType.toString())){
                employeeType= emplType;
            }
        }

        switch (employeeType) {
            case HRAssistant: employee=new HRAssistant(name);
                break;
            case HRConsultant: employee=new HRConsultant(name);
                break;
            case HRHoD: employee=new HRHoD(name);
                break;
            case Receptionist: employee=new Receptionist(name);
                break;
            case Researcher: employee=new Researcher(name);
                break;
            case ScientificAssistant:employee=new ScientificAssistant(name);
                break;
            case SecurityOfficer:employee=new SecurityOfficer(name);
                break;
        }
        if(employee!=null) employeeMap.put(employee.getId(),employee);
    }

    private enum EmployeeType {
        HRAssistant,
        HRConsultant,
        HRHoD,
        Receptionist,
        Researcher,
        ScientificAssistant,
        SecurityOfficer
    }

}
