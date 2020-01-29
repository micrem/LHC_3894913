package Infrastructure.Security.IDCard;

import HumanResources.Person;

import java.util.Date;

public interface IROIDCard {
    Person getPerson();

    Date getValidFrom();

    Date getValidTo();

    int[][] getIrisStructure();

    boolean isLocked();

    IIDCard grantWriteAccess(ICardWriter cardWriter);
}
