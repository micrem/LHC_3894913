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

public class Detector implements IDetector {
    static private String higgsBosonStructure = "higgs";
    private boolean isActivated;
    private LinkedList<Experiment> experimentList;
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
        int higgsPos=-1;
        for (int i = 0; i < 200000; i++) {
            higgsPos=matchString(higgsBosonStructure, experiment.getBlocks()[i].getStructure());
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
        for (Experiment experiment : experimentList
                ) {
            search(experiment);
            if (experiment.isHiggsBosonFound()){
                Instant after = Instant.now();
                long delta = Duration.between(before, after).toMillis();
                System.out.println(experiment.toString());
                System.out.println("Analysis runtime: "+delta+"ms");
                System.out.println();
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
