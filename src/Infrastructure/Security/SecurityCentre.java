package Infrastructure.Security;

import HumanResources.Employee;
import HumanResources.EmployeeManagement;
import HumanResources.IROEmployeeManagement;
import HumanResources.SecurityOfficer;
import Infrastructure.LHC.Building;
import Infrastructure.Security.IDCard.CardWriter;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IDCardv2;
import Infrastructure.Security.IDCard.IROIDCard;

import java.util.Stack;

public class SecurityCentre {
    private Building building;
    private Stack<IROIDCard> employeeIDCards;
    private ICardWriter cardWriter;
    private SecurityOfficer officer;

    private IROEmployeeManagement employeeManagement;

    public SecurityCentre(Building building) {
        employeeIDCards = new Stack<>();
        this.building = building;
        generateEmptyCards(20);
        cardWriter = new CardWriter(false);
        employeeManagement = EmployeeManagement.instance;
    }

    public void createEmployeeCard(Employee employee) {
        Permission[] permissions;
        if (employeeIDCards.empty()) {
            System.out.println("No empty HumanResources.Visitor IDCards left.");
            return;
        }

        IROIDCard idCard = this.getBlankIDCard();
        cardWriter = this.getCardWriter(officer);
        cardWriter.insertCard(idCard);
        permissions = employeeManagement.getEmployeePermissions(employee.getId());
        for (Permission permission : permissions) {
            cardWriter.setPermission(permission);
        }
        cardWriter.getPasswordInput(employee);
        cardWriter.writePassword();
        cardWriter.scanIrisToCard(employee);
        cardWriter.finalizeCard(employee);
        IROIDCard authorizedCard = cardWriter.ejectCard();
        employee.receiveCard(authorizedCard);
    }

    private void generateEmptyCards(int ammountCards) {
        for (int i = 1; i < ammountCards; i++) {
            employeeIDCards.push(new IDCardv2());
        }
    }

    private IROIDCard getBlankIDCard() {
        if (employeeIDCards.empty()) {
            generateEmptyCards(20);
        }
        return employeeIDCards.pop();
    }


    public ICardWriter getCardWriter(SecurityOfficer officer) {
        //log writer access, check credentials etc.
        return cardWriter;
    }

    public SecurityOfficer getOfficer() {
        return officer;
    }

    public void setOfficer(SecurityOfficer officer) {
        this.officer = officer;
    }

    public boolean verifyEmployee(Employee employee) {
        cardWriter.insertCard(employee.getCard(cardWriter));
        boolean verified = cardWriter.verifyCardUser(employee);
        employee.receiveCard(cardWriter.ejectCard());
        return verified;
    }
}
