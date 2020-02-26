package Infrastructure.Security;

import HumanResources.Receptionist;
import HumanResources.Visitor;
import Infrastructure.LHC.Building;
import Infrastructure.Security.IDCard.CardWriter;
import Infrastructure.Security.IDCard.ICardWriter;
import Infrastructure.Security.IDCard.IDCard;
import Infrastructure.Security.IDCard.IROIDCard;

import java.util.Stack;

public class Reception {
    private Building building;
    private Stack<IROIDCard> visitorIDCards;
    private ICardWriter cardWriter;
    private Receptionist receptionist;

    public Reception(Building building) {
        visitorIDCards = new Stack<>();
        this.building = building;
        generateEmptyCards(20);
        cardWriter = new CardWriter(false);
    }

    public void createVisitorCard(Visitor visitor) {

        if (visitorIDCards.empty()) {
            System.out.println("No empty HumanResources.Visitor IDCards left.");
            return;
        }

        IROIDCard idCard = this.getBlankIDCard();
        cardWriter = this.getCardWriter(receptionist);
        cardWriter.insertCard(idCard);
        cardWriter.setPermission(Permission.Visitor);
        cardWriter.getPasswordInput(visitor);
        cardWriter.writePassword();
        cardWriter.scanIrisToCard(visitor);
        cardWriter.finalizeCard(visitor);
        IROIDCard authorizedCard = cardWriter.ejectCard();
        visitor.receiveCard(authorizedCard);
    }

    private void generateEmptyCards(int ammountCards) {
        for (int i = 1; i < ammountCards; i++) {
            visitorIDCards.push(new IDCard());
        }
    }

    private IROIDCard getBlankIDCard() {
        if (visitorIDCards.empty()) {
            generateEmptyCards(20);
        }
        return visitorIDCards.pop();
    }

    public ICardWriter getCardWriter(Receptionist receptionist) {
        //log writer access, check credentials etc.
        return cardWriter;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public boolean verifyVisitor(Visitor visitor) {
        cardWriter.insertCard(visitor.getCard(cardWriter));
        boolean verified = cardWriter.verifyCardUser(visitor);
        visitor.receiveCard(cardWriter.ejectCard());
        return verified;
    }
}
