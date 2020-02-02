package Infrastructure.LHC;

import java.io.File;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;

public class Detector {
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
            URL[] urls = {new File(Configuration.instance.fullPathToJavaArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Detector.class.getClassLoader());
            stringMatcherClass = Class.forName(Configuration.instance.nameOfClass, true, urlClassLoader);
            port = stringMatcherClass.getMethod("getInstance").invoke(null);
            port = stringMatcherClass.getDeclaredField("port").get(port);
            searchMethod = port.getClass().getMethod("search",String.class,String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void search(Experiment experiment){

    }

    private int matchString(String text, String pattern){

        try {
            return (int) searchMethod.invoke(port,text,pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  0;
    }

}
