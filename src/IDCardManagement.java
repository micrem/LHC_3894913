import HumanResources.Employee;
import Infrastructure.Security.IDCard.CardWriter;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IROIDCardMultichip;

public enum IDCardManagement {
    INSTANCE;
    private ICardWriter cardWriter;

    IDCardManagement() {
        cardWriter= new CardWriter(true);
    }

    public void assignIDCard(IROIDCardMultichip idCard, Employee employee){}

    public void lockIDCard(IROIDCardMultichip idCard){}

    public void clearIDCard(IROIDCardMultichip idCard){}

}
