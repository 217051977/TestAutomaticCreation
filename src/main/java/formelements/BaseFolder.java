package formelements;

public class BaseFolder {

    private String baseFolderPath = "D:\\Users\\nb27853\\omni-qa-pluma-test";

    public BaseFolder() {

    }

    public BaseFolder(String baseFolderPath) {
        this.baseFolderPath = baseFolderPath;
    }

    public String getBaseFolderPath() {
        return baseFolderPath;
    }

}
