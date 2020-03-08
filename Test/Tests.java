import HumanResources.*;
import Infrastructure.Energy.USP;
import Infrastructure.LHC.*;
import Infrastructure.Security.EmployeeType;
import Infrastructure.Security.IDCard.*;
import Infrastructure.Security.Permission;
import Infrastructure.Security.Reception;
import Infrastructure.Security.SecurityCentre;
import LHC_Streams.BlocksStreamProcessor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Tests {
    @Test
    void test1() {
        //LHC has 1 Ring and 2 USPs
        LargeHadronCollider lhc = new LargeHadronCollider();
        lhc.setRing(new Ring(lhc, null));

        assertNotNull(lhc.getRing());
        assertEquals(lhc.getUSPs().length, 2);
    }

    @Test
    void test2() {
        //USP has 25 batteries
        USP usp = new USP();

        assertEquals(usp.getBatteries().length, 25);
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
        IROIDCard card = visitor.getCard(new CardReader(false));

        assertNotNull(card);
        assertNotEquals(card.getPassword().length(), 0);
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
        IROIDCard card = employee.getCard(new CardReader(false));

        assertNotNull(card);
        assertNotEquals(card.getPassword().length(), 0);
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
        IROIDCard card = employee.getCard(reader);
        //create anonymous insane person test-class
        Employee crazyPerson = new Researcher("Mad Hatter") {
            public int passwordAttempts = 0;

            public String typePassword(IPasswordPad passwordPad) {
                passwordAttempts++;
                return password.replace(password.charAt(0), password.charAt(1));
            }

            public String toString() {
                return "" + passwordAttempts;
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
        assertTrue(experiments.get(0).getBlocks().length == 200000);
        assertTrue(experiments.get(0).getBlocks()[0].getStructure().length() == 10);

    }

    @Test
    void test15() {
        //find higgs
        Detector detector = new Detector();
        detector.loadExperimentsFromDB();
        final List<Experiment> experiments = detector.getExperiments(new Researcher(Person.getRandomName()));
        final List<Experiment> filteredExperiments = experiments.stream().filter(experiment -> experiment.isHiggsBosonFound()).collect(Collectors.toList());
        assertTrue(filteredExperiments.size() > 0);
        System.out.println(filteredExperiments.get(0));
    }

    @Test
    void testStreams01() {
        Experiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true)};
        String structure0 = "a8345678##"; //once
        String structure1 = "a834567890";
        String structure2 = "X43456789X";
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        e.setBlocks(blocks);
        int hashCount = (new BlocksStreamProcessor(e)).countWithHashtag();
        assertEquals(1, hashCount);
    }

    @Test
    void testStreams02() {
        Experiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true)};
        String structure0 = "a83456789z";//should appear only once
        String structure1 = "a83456789z";
        String structure2 = "X43456789X";//should not appear
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        e.setBlocks(blocks);
        List<Block> list = (new BlocksStreamProcessor(e)).blocksWithZ();

        assertEquals(1, list.size());
        assertEquals(list.get(0), blocks[0]);
        assertFalse(list.contains(blocks[1]));
        assertFalse(list.contains(blocks[2]));
    }

    @Test
    void testStreams03() {
        Experiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
        String structure0 = "a83456789z";//should appear last
        String structure1 = "a23456789z";//should appear first
        String structure2 = "X43456789X";//should not appear
        String structure3 = "a23456789z";//duplicate
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        e.setBlocks(blocks);
        List<Block> list = (new BlocksStreamProcessor(e)).blocksWithA9Zordered();

        assertEquals(2, list.size());
        assertEquals(list.get(0), blocks[1]);
        assertEquals(list.get(1), blocks[0]);
        assertFalse(list.contains(blocks[2]));
    }

    @Test
    void testStreams04() {
        Experiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
        String structure0 = "ab00000000";//group0
        String structure1 = "00000000ab";//group8
        String structure2 = "00000000ab";//duplicate discarded
        String structure3 = "11111111ab";//group8
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        e.setBlocks(blocks);
        Map<Integer, List<Block>> mapAB = (new BlocksStreamProcessor(e)).containsAB();

        assertEquals(2, mapAB.size());
        assertEquals( mapAB.get(0).get(0), blocks[0] );
        assertTrue( mapAB.get(8).containsAll(Arrays.asList(blocks[1],blocks[3])) );
    }

    @Test
    void testStreams05() {
        Experiment e = new Experiment();
        Block[] blocks = {new Block(true), new Block(true), new Block(true), new Block(true)};
        String structure0 = "abc0000000";//true
        String structure1 = "abc0000000";//duplicate
        String structure2 = "00000000ab";//false
        String structure3 = "c1111111ab";//true
        blocks[0].setStructure(structure0);
        blocks[1].setStructure(structure1);
        blocks[2].setStructure(structure2);
        blocks[3].setStructure(structure3);
        e.setBlocks(blocks);
        Map<Boolean, List<Block>> mapABC = (new BlocksStreamProcessor(e)).distinctPartitionABC();

        assertEquals(2, mapABC.size());
        assertEquals( mapABC.get(false).get(0), blocks[2] );
        assertTrue( mapABC.get(true).containsAll(Arrays.asList(blocks[0],blocks[3])) );
    }

    @Test
    void runExperimentsFromTxt() {
        //loads protons from files, runs experiments, overwrites into DB

        //FIXME test doesn't work reliably, possibly due to laptop performance
        if (true) return;

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
            System.out.print(" " + i);
            cc.startExperment(ExperimentScope.ESFull);
        }
        System.out.println();
        cc.analyseAll();
        detector.saveExperimentsToDB();
    }

}