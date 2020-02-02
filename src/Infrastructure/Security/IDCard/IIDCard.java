package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Infrastructure.Security.Permission;

import java.time.LocalDate;

public interface IIDCard {

    void setPerson(Person person);

    void setValidFrom(LocalDate validFrom);

    void setValidTo(LocalDate validTo);

    void setIrisStructure(int[][] irisStructureInput);

    void setLocked(boolean locked);

    void setPassword(String password);

    void setPermission(Permission permission, boolean value);

}
