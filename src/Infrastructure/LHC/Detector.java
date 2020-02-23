package Infrastructure.LHC;

import HumanResources.Person;
import HumanResources.Researcher;
import Infrastructure.Security.IDCard.CardReader;
import Infrastructure.Security.IDCard.ICardReader;
import Infrastructure.Security.Permission;
import PersistanceLayer.IPersistanceLayer;
import PersistanceLayer.PersistanceLayerDB;
import com.google.common.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Detector extends Subscriber implements IDetector {
    static private String higgsBosonStructure = "higgs";
    private boolean isActivated;
    private List<Experiment> experimentList = new ArrayList<>();
    private ICardReader cardReader = new CardReader(true);

    private IPersistanceLayer persistanceLayer = PersistanceLayerDB.instance;

    private Method searchMethod;
    private Object port;

    public Detector() {
        searchMethod = Configuration.instance.searchMethod;
        port = Configuration.instance.port;
        if (Configuration.instance.useDatabase) {
            persistanceLayer.setupConnection();
            experimentList = persistanceLayer.getExperiments();
            persistanceLayer.shutdown();
        }
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
    public void analyse(Experiment experiment) {
        int higgsPos;
        int blocksToCheck;
        switch (experiment.getScope()) {
            case ESFull:
                blocksToCheck = 200000;
                break;
            case ES5:
                blocksToCheck = 5000;
                break;
            case ES10:
                blocksToCheck = 10000;
                break;
            case ES20:
                blocksToCheck = 20000;
                break;
            default:
                blocksToCheck = 200000;
        }
        System.out.println("analysing experiment with protons " + experiment.getProton01ID() + " " + experiment.getProton02ID());
        for (int i = 0; i < blocksToCheck; i++) {
            higgsPos = matchString(experiment.getBlocks()[i].getStructure(), higgsBosonStructure);
            if (higgsPos >= 0) {
                experiment.setHiggsBosonFound(true);
                experiment.setHiggsBlockID(experiment.getBlocks()[i].getUuid().toString());
                return;
            }
        }
    }

    @Override
    @Subscribe
    public void receive(EventAnalyse event) {
        setActivated(true);
        Instant before = Instant.now();
        for (Experiment experiment : experimentList) {
            analyse(experiment);
            if (experiment.isHiggsBosonFound()) {
                Instant after = Instant.now();
                long delta = Duration.between(before, after).toMillis();
                System.out.println("Higgs particle found:");
                System.out.println(experiment.toString());
                System.out.println("Analysis runtime: " + delta + "ms");
            }
        }
        setActivated(false);
    }

    private int matchString(String text, String pattern) {
        try {
            return (int) searchMethod.invoke(port, text, pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void addExperiment(Experiment experiment) {
        if (experiment == null) {
            return;
        }
        experimentList.add(experiment);
    }

    @Override
    public List<Experiment> getExperiments(Person scientist) {
        cardReader.insertCard(scientist.getCard(this.cardReader));
        if (cardReader.verifyCardUser(scientist) && cardReader.verifyPermission(Permission.Researcher)) ;
        return experimentList;
    }

    public void writeToDB() {
        persistanceLayer.setupConnection();
        persistanceLayer.createTables();
        for (Experiment experiment : experimentList) {
            if (experiment.getProton02ID() / 2 < 15 || experiment.getProton02ID() / 2 > 20) continue;
            System.out.println("writing to DB experiment " + experiment.getProton01ID() + "+" + experiment.getProton02ID());
            persistanceLayer.insert(experiment);
            int i = 0;
            for (Block block : experiment.getBlocks()
            ) {
                if (++i % 10000 == 0) System.out.print(".");
                persistanceLayer.insert(block);
            }
            System.out.println("done");
        }
        persistanceLayer.shutdown();
    }
}
