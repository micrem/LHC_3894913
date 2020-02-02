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
    private ICardWriter cardWriter = new CardWriter(true);

    public Reception(Building building) {
        visitorIDCards = new Stack<>();
        this.building = building;
    }

    public void createVisitorCard(Visitor visitor) {
        //todo throw exception
        if (visitorIDCards.empty()) {
            System.out.println("No empty HumanResources.Visitor IDCards left.");
            return;
        }
        IROIDCard card = visitorIDCards.pop();
    }

    private void generateEmptyCards(int ammountCards) {
        for (int i = 1; i < ammountCards; i++) {
            visitorIDCards.push(new IDCard());
        }
    }

    public IROIDCard getBlankIDCard() {
        if (visitorIDCards.empty()) {
            generateEmptyCards(20);
        }
        return visitorIDCards.pop();
    }

    public ICardWriter getCardWriter(Receptionist receptionist) {
        //log writer access, check credentials etc.
        return cardWriter;
    }
}
