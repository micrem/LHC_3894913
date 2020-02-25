import HumanResources.*;
import Infrastructure.Energy.USP;
import Infrastructure.LHC.*;
import Infrastructure.Security.EmployeeType;
import Infrastructure.Security.IDCard.*;
import Infrastructure.Security.Permission;
import Infrastructure.Security.Reception;
import Infrastructure.Security.SecurityCentre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Tests {
    @Test
    void test1() {
        //LHC has 1 Ring and 2 USPs
        LargeHadronCollider lhc = new LargeHadronCollider();
        lhc.setRing(new Ring(lhc, null));

        assertNotNull(lhc.getRing());
        assertEquals(lhc.getUSPs().length,2);
    }

    @Test
    void test2() {
        //USP has 25 batteries
        USP usp = new USP();

        assertEquals(usp.getBatteries().length,25);
    }

    @Test
    void test3() {
        //reception creates valid visitor card
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
        //Security Centre creates valid employee card
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
        //3x wrong password locks card
        IEmployeeManagement employeeMngt = EmployeeManagement.instance;
        SecurityCentre secCenter = new SecurityCentre(new Building());
        SecurityOfficer secOfficer = new SecurityOfficer("John McLane");
        Employee employee = employeeMngt.createRegisteredEmployee(Person.getRandomName(), EmployeeType.Researcher.toString());
        secOfficer.setSecCenter(secCenter);
        secCenter.setOfficer(secOfficer);
        employee.registerWithSecCenter(secOfficer);
        ICardReader reader = new CardReader(false);
        IROIDCard card =  employee.getCard(reader);
        //create anonymous insane person test-class
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
        //expired card refused
        IEmployeeManagement employeeMngt = EmployeeManagement.instance;
        SecurityCentre secCenter = new SecurityCentre(new Building());
        SecurityOfficer secOfficer = new SecurityOfficer("John McLane");
        ICardReader reader = new CardReader(false);
        Employee employee = employeeMngt.createRegisteredEmployee(Person.getRandomName(), EmployeeType.Researcher.toString());
        IROIDCard card;

        secOfficer.setSecCenter(secCenter);
        secCenter.setOfficer(secOfficer);
        employee.registerWithSecCenter(secOfficer);
        //valid card works = control group
        assertTrue(secCenter.verifyEmployee(employee));

        card = employee.getCard(reader);
        //Set card date to expired
        card.grantWriteAccess(new CardWriter(false)).setValidFrom(LocalDate.now().plusMonths(1));
        employee.receiveCard(card);

        assertFalse(secCenter.verifyEmployee(employee));
    }

    @Test
    void test14() {
        //experiment has 200k blocks, block has 10 symbols
        Detector detector = new Detector();
        detector.loadExperimentsFromDB();
        //detector.getExperiments(new Researcher(Person.getRandomName())).forEach(System.out::println);
        final List<Experiment> experiments = detector.getExperiments(new Researcher(Person.getRandomName()));
        assertTrue(experiments.get(0).getBlocks().length==200000);
        assertTrue(experiments.get(0).getBlocks()[0].getStructure().length()==10);

    }

    @Test
    void test15() {
        //find higgs
        Detector detector = new Detector();
        detector.loadExperimentsFromDB();
        final List<Experiment> experiments = detector.getExperiments(new Researcher(Person.getRandomName()));
        final List<Experiment> filteredExperiments = experiments.stream().filter(experiment -> experiment.isHiggsBosonFound()).collect(Collectors.toList());
        assertTrue(filteredExperiments.size()>0);
        System.out.println(filteredExperiments.get(0));
    }

    @Test
    void runExperimentsFromTxt() {
        //loads protons from files, runs experiments, overwrites into DB

        //FIXME test doesn't work reliably, possibly due to laptop performance
        if(true)return;

        ControlCenter cc = new ControlCenter();
        Detector detector = new Detector();
        LargeHadronCollider lhc = new LargeHadronCollider();

        Ring ring = new Ring(lhc, detector);
        lhc.setRing(ring);
        lhc.setControlCenter(cc);

        cc.addSubscriber(ring);
        cc.addSubscriber(detector);
        ring.loadProtonTxts();

        System.out.println("running experiment ");
        for (int i = 0; i < 25; i++) {
            System.out.print( " "+ i);
            cc.startExperment(ExperimentScope.ESFull);
        }
        System.out.println();
        cc.analyseAll();
        detector.saveExperimentsToDB();
    }

}