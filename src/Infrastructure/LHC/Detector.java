package Infrastructure.LHC;

import com.google.common.eventbus.Subscribe;

import java.io.File;
import java.io.Reader;
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
    private List<Experiment> experimentList=new LinkedList<>();
    private Reader reader;

    private Class stringMatcherClass;
    private Object port;
    private Method searchMethod;

    /** @noinspection unchecked*/
    public Detector() {
        try {
            URL[] urls = {new File(Configuration.instance.subFolderPathToArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Detector.class.getClassLoader());
            stringMatcherClass = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            port = stringMatcherClass.getMethod("getInstance").invoke(null);
            port = stringMatcherClass.getDeclaredField("port").get(port);
            searchMethod = port.getClass().getMethod("search",String.class,String.class);
        } catch (Exception e) {
            e.printStackTrace();
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

}
