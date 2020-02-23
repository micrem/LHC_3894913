import HumanResources.Person;
import HumanResources.Receptionist;
import HumanResources.Visitor;
import Infrastructure.Energy.USP;
import Infrastructure.LHC.Building;
import Infrastructure.LHC.LargeHadronCollider;
import Infrastructure.LHC.Ring;
import Infrastructure.Security.IDCard.CardReader;
import Infrastructure.Security.IDCard.IROIDCard;
import Infrastructure.Security.Permission;
import Infrastructure.Security.Reception;
import org.junit.jupiter.api.Test;

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

    }
}