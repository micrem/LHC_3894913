package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Infrastructure.Security.Permission;

import java.util.ArrayList;
import java.util.Date;

public interface IROIDCard {
    Person getPerson();

    Date getValidFrom();

    Date getValidTo();

    int[][] getIrisStructure();

    boolean isLocked();

    IIDCard grantWriteAccess(ICardWriter cardWriter);

    String getPassword();

    boolean hasPermission(Permission permission);

}
