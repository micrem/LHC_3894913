import HumanResources.*;
import Infrastructure.Energy.USP;
import Infrastructure.LHC.Building;
import Infrastructure.LHC.LargeHadronCollider;
import Infrastructure.LHC.Ring;
import Infrastructure.Security.EmployeeType;
import Infrastructure.Security.IDCard.*;
import Infrastructure.Security.Permission;
import Infrastructure.Security.Reception;
import Infrastructure.Security.SecurityCentre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void test1() {
        LargeHadronCollider lhc = new LargeHadronCollider();
        lhc.setRing(new Ring(lhc, null));
        assertNotNull(lhc.getRing());
        assertEquals(lhc.getUSPs().length,2);
    }

    @Test
    void test2() {
        USP usp = new USP();
        assertEquals(usp.getBatteries().length,25);
    }

    @Test
    void test3() {
        Reception reception = new Reception(new Building());
        Receptionist receptionist = new Receptionist("Alfred Hitchblock");
        Visitor visitor = new Visitor("Dumbledore Blunderbuss");
        receptionist.setReception(reception);
        reception.setReceptionist(receptionist);
        visitor.registerWithReceptionist(receptionist);
        IROIDCard card =  visitor.getCard(new CardReader(false));
        assertNotNull(card);
        assertNotEquals(card.getPassword().length(),0);
        assertTrue(card.hasPermission(Permission.Visitor));
        assertTrue(card.getPerson().equals(visitor));
        assertTrue(reception.verifyVisitor(visitor));
    }

    @Test
    void test4() {
        IEmployeeManagement employeeMngt = EmployeeManagement.instance;
        SecurityCentre secCenter = new SecurityCentre(new Building());
        SecurityOfficer secOfficer = new SecurityOfficer("John McLane");
        Employee employee = employeeMngt.createRegisteredEmployee(Person.getRandomName(), EmployeeType.Researcher.toString());
        secOfficer.setSecCenter(secCenter);
        secCenter.setOfficer(secOfficer);
        employee.registerWithSecCenter(secOfficer);
        IROIDCard card =  employee.getCard(new CardReader(false));
        assertNotNull(card);
        assertNotEquals(card.getPassword().length(),0);
        assertTrue(card.hasPermission(Permission.Researcher));
        assertTrue(card.getPerson().equals(employee));
        assertTrue(secCenter.verifyEmployee(employee));
    }

    @Test
    void test5_test6() {
        //Test5
        IEmployeeManagement employeeMngt = EmployeeManagement.instance;
        SecurityCentre secCenter = new SecurityCentre(new Building());
        SecurityOfficer secOfficer = new SecurityOfficer("John McLane");
        Employee employee = employeeMngt.createRegisteredEmployee(Person.getRandomName(), EmployeeType.Researcher.toString());
        secOfficer.setSecCenter(secCenter);
        secCenter.setOfficer(secOfficer);
        employee.registerWithSecCenter(secOfficer);
        ICardReader reader = new CardReader(false);
        IROIDCard card =  employee.getCard(reader);
        Employee crazyPerson = new Researcher("Mad Hatter"){
            public int passwordAttempts=0;
                public String typePassword(IPasswordPad passwordPad) {
                    passwordAttempts++;
                return password.replace(password.charAt(0),password.charAt(1));
            }
            public String toString(){
                    return ""+passwordAttempts;
            }
        };
        crazyPerson.receiveCard(card);
        assertFalse(secCenter.verifyEmployee(crazyPerson));
        assertTrue(crazyPerson.toString().equals("3"));
        assertTrue(card.isLocked());

        //Test6
        //attempt to verify with correct employee but locked card
        assertFalse(reader.verifyCardUser(employee));
    }

    @Test
    void test7() {
        IEmployeeManagement employeeMngt = EmployeeManagement.instance;
        SecurityCentre secCenter = new SecurityCentre(new Building());
        SecurityOfficer secOfficer = new SecurityOfficer("John McLane");
        ICardReader reader = new CardReader(false);
        Employee employee = employeeMngt.createRegisteredEmployee(Person.getRandomName(), EmployeeType.Researcher.toString());
        IROIDCard card;

        secOfficer.setSecCenter(secCenter);
        secCenter.setOfficer(secOfficer);
        employee.registerWithSecCenter(secOfficer);

        assertTrue(secCenter.verifyEmployee(employee));

        card = employee.getCard(reader);
        card.grantWriteAccess(new CardWriter(false)).setValidFrom(LocalDate.now().plusMonths(1));
        employee.receiveCard(card);

        assertFalse(secCenter.verifyEmployee(employee));
    }
}