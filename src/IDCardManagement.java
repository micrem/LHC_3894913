import HumanResources.Employee;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IROIDCardMultichip;

public enum IDCardManagement {
    INSTANCE;
    private ICardWriter CardWriter;

    public void assignIDCard(IROIDCardMultichip idCard, Employee employee){}

    public void lockIDCard(IROIDCardMultichip idCard){}

    public void clearIDCard(IROIDCardMultichip idCard){}

}
