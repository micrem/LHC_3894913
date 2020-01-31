public enum Configuration {
    instance;
    public SearchAlgorithm typeOfSearchAlgorithm = SearchAlgorithm.Native;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String nameOfSubFolder = "SearchAlgos";
    public String nameOfJavaArchive = typeOfSearchAlgorithm + ".jar";
    public String fullPathToJavaArchive = userDirectory + fileSeparator + nameOfSubFolder +fileSeparator + nameOfJavaArchive;


    public static void main(String[] args) {

        System.out.println(Configuration.instance.fullPathToJavaArchive);
    }

    private enum SearchAlgorithm {
        Native,
        BoyerMoore,
        KnuthMorrisPratt
    }
}