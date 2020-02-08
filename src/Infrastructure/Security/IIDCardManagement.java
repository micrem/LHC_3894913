package Infrastructure.Security;

import HumanResources.Employee;
import Infrastructure.Security.IDCard.IROIDCard;

public interface IIDCardManagement {
    void assignIDCard(IROIDCard idCard, Employee employee);

    void lockIDCard(IROIDCard idCard);

    void clearIDCard(IROIDCard idCard);
}
