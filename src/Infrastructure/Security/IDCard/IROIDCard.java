package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Infrastructure.Security.Permission;

import java.time.LocalDate;

public interface IROIDCard {
    Person getPerson();

    LocalDate getValidFrom();

    LocalDate getValidTo();

    int[][] getIrisStructure();

    boolean isLocked();

    IIDCard grantWriteAccess(ICardWriter cardWriter);

    IIDCardPWedit grantPasswordChangeAccess(ICardReader cardReader);

    String getPassword();

    boolean hasPermission(Permission permission);

    IDCardVersion getVersion();

    IROIDCardMultichip getMultichipReadAccess(ICardReader cardReader);

    IIDCardMultichip getMultichipWriteAccess(ICardWriter cardWriter);

    Integer getID();
}
