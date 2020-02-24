package HumanResources;

import Infrastructure.Security.SecurityCentre;

public class SecurityOfficer extends Employee {
    private boolean hasWeapon;
    private SecurityCentre secCenter;

    public SecurityOfficer(String name) {
        super(name);
    }

    public void processEmployee(Employee employee) {
        secCenter.createEmployeeCard(employee);
    }

    public SecurityCentre getSecCenter() {
        return secCenter;
    }

    public void setSecCenter(SecurityCentre secCenter) {
        this.secCenter = secCenter;
    }
}
