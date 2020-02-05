package Infrastructure.LHC;

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


    public static void main(String[] args) {
        System.out.println(Configuration.instance.fullPathToJavaArchive);
    }

    private enum SearchAlgorithm {
        Native,
        BoyerMoore,
        KnuthMorrisPratt
    }
}