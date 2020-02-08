package Infrastructure.LHC;

import HumanResources.Person;
import Infrastructure.Security.IDCard.CardReader;
import Infrastructure.Security.IDCard.ICardReader;
import Infrastructure.Security.Permission;
import com.google.common.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class Detector extends Subscriber implements IDetector {
    static private String higgsBosonStructure = "higgs";
    private boolean isActivated;
    private List<Experiment> experimentList = new LinkedList<>();
    private ICardReader cardReader = new CardReader(true);

    private Method searchMethod;
    private Object port;


    public Detector() {
        searchMethod = Configuration.instance.searchMethod;
        port = Configuration.instance.port;
    }

    @Override
    public boolean isActivated() {
        return isActivated;
    }

    @Override
    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    @Override
    public void search(Experiment experiment){
        int higgsPos;
        int blocksToCheck;
        switch (experiment.getScope()) {
            case ESFull:blocksToCheck=200000; break;
            case ES5:   blocksToCheck=5000; break;
            case ES10:  blocksToCheck=10000; break;
            case ES20:  blocksToCheck=20000; break;
            default:    blocksToCheck=200000;
        }
        for (int i = 0; i < blocksToCheck; i++) {
            higgsPos=matchString(experiment.getBlocks()[i].getStructure(), higgsBosonStructure);
            if(higgsPos>=0){
                experiment.setHiggsBosonFound(true);
                experiment.setHiggsBlockID(i);
                return;
            }
        }
    }

    @Override
    @Subscribe
    public void receive(EventAnalyse event){
        setActivated(true);
        Instant before = Instant.now();
        for (Experiment experiment : experimentList ) {
            search(experiment);
            if (experiment.isHiggsBosonFound()){
                Instant after = Instant.now();
                long delta = Duration.between(before, after).toMillis();
                System.out.println("Higgs particle found:");
                System.out.println(experiment.toString());
                System.out.println("Analysis runtime: "+delta+"ms");
            }
        }
        setActivated(false);
    }

    private int matchString(String text, String pattern){
        try {
            return (int) searchMethod.invoke(port,text,pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  0;
    }

    @Override
    public void addExperiment(Experiment experiment){
        experimentList.add(experiment);
    }

    @Override
    public List<Experiment> getExperiments(Person scientist) {
        cardReader.insertCard(scientist.getCard(this.cardReader));
        if(cardReader.verifyCardUser(scientist) && cardReader.verifyPermission(Permission.Researcher));
        return experimentList;
    }
}
