package Infrastructure.Security.IDCard;

import HumanResources.Person;

import java.util.Date;

public interface IIDCard {

    void setPerson(Person person);

    void setValidFrom(Date validFrom);

    void setValidTo(Date validTo);

    void setIrisStructure(int[][] irisStructureInput);

    void setLocked(boolean locked);

}
