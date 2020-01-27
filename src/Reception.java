import java.util.Stack;

public class Reception {
    // enum den Aufwand nicht wert, zB public Konstruktor nicht möglich
    private Building building;
    private Stack<IDCard> visitorIDCards;
    private ICardWriter cardWriter = new CardWriterVersion1();

    public Reception(Building building) {
        visitorIDCards = new Stack<>();
        this.building = building;
        for (int i=1;i<20;i++){
            visitorIDCards.push(new IDCard());
        }
    }

    public void createVisitorCard(Visitor visitor){
        //todo throw exception
        if (visitorIDCards.empty()) {
            System.out.println("No empty Visitor IDCards left.");
            return;
        }
        IDCard card = visitorIDCards.pop();

    }
}
