public enum Configuration {
    instance;
    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");
    public SearchAlgorithm typeOfSearchAlgorithm = SearchAlgorithm.Native;
    public String nameOfSubFolder = "exchange_component_" + typeOfSearchAlgorithm + fileSeparator + "jar";
    public String nameOfJavaArchive = "MemoryStick.jar" //TODO THINGS! ;
    public String subFolderPathOfJavaArchive = nameOfSubFolder + fileSeparator + nameOfJavaArchive;
    public String fullPathToJavaArchive = userDirectory + subFolderPathOfJavaArchive;
    public String nameOfClass = "MemoryStick" slödkfdskfj;

    public void print() {
        System.out.println("--- Configuration");
        System.out.println("fileSeparator              : " + fileSeparator);
        System.out.println("userDirectory              : " + userDirectory);
        System.out.println("typeOfMemoryStick          : " + typeOfSearchAlgorithm);
        System.out.println("nameOfSubFolder            : " + nameOfSubFolder);
        System.out.println("nameOfJavaArchive          : " + nameOfJavaArchive);
        System.out.println("subFolderPathOfJavaArchive : " + subFolderPathOfJavaArchive);
        System.out.println("fullPathToJavaArchive      : " + fullPathToJavaArchive);
        System.out.println("nameOfClass                : " + nameOfClass);
        System.out.println();
    }

    public static void main(String[] args) {
        Configuration c = Configuration.instance;
        c.print();
    }

    private enum SearchAlgorithm {
        Native,
        BoyerMoore,
        KnuthMorrisPratt
    }
}