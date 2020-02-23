package Infrastructure.LHC;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public enum Configuration {
    instance;
    public SearchAlgorithm typeOfSearchAlgorithm = SearchAlgorithm.BoyerMoore;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String nameOfClass = "StringMatcher";
    public String nameOfSubFolder = "src" + fileSeparator + "SearchAlgorithms" + fileSeparator + typeOfSearchAlgorithm;
    public String nameOfJavaArchive = "StringMatcher.jar";
    public String subFolderPathToArchive = nameOfSubFolder + fileSeparator + nameOfJavaArchive;

    public String fullPathToJavaArchive = userDirectory + fileSeparator + nameOfSubFolder + fileSeparator + nameOfJavaArchive;
    public Object port;
    public Method searchMethod;
    public boolean useDatabase = true;
    private Class stringMatcherClass;

    /**
     * @noinspection unchecked
     */
    Configuration() {
        init();
    }

    public static void main(String[] args) {
        System.out.println(Configuration.instance.fullPathToJavaArchive);
    }

    void init() {
        try {
            URL[] urls = {new File(subFolderPathToArchive).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Detector.class.getClassLoader());
            stringMatcherClass = Class.forName(nameOfClass, true, urlClassLoader);
            port = stringMatcherClass.getMethod("getInstance").invoke(null);
            port = stringMatcherClass.getDeclaredField("port").get(port);
            searchMethod = port.getClass().getMethod("search", String.class, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum SearchAlgorithm {
        Native,
        BoyerMoore,
        KnuthMorrisPratt
    }
}