import java.util.ArrayList;

public class SortSettings {
//    ToDo: исправить Regex
    public static final String FILENAME_REGEX = "^((((\\w+([\\-_]?\\w+)*)/)*)?\\w*([\\-_]?\\w*)*\\.txt)$";
    private boolean stringType = false;
    private boolean sortTypeAsc = true;
    private ArrayList<String> inFilePathes = new  ArrayList<>();
    private ArrayList<String> outFilePathes = new  ArrayList<>();
    private String subFolderPath;

    public SortSettings() {
    }

    public String getSubFolderPath() {
        return subFolderPath;
    }

    public void setSubFolderPath(String subFolderPath) {
        this.subFolderPath = subFolderPath;
    }

    public boolean isStringType() {
        return stringType;
    }

    public void setStringType(boolean stringType) {
        this.stringType = stringType;
    }

    public boolean isSortTypeAsc() {
        return sortTypeAsc;
    }

    public void setSortTypeAsc(boolean sortTypeAsc) {
        this.sortTypeAsc = sortTypeAsc;
    }

    public ArrayList<String> getInFilePathes() {
        return inFilePathes;
    }

    public void setInFilePathes(ArrayList<String> inFilePathes) {
        this.inFilePathes = inFilePathes;
    }

    public ArrayList<String> getOutFilePathes() {
        return outFilePathes;
    }

    public void setOutFilePathes(ArrayList<String> outFilePathes) {
        this.outFilePathes = outFilePathes;
    }
}
