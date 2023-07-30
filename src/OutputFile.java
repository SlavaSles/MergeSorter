public class OutputFile {
    private String path;
    private boolean stringType;
    private boolean sortTypeAsc;
    private String subFolderPath;

    public OutputFile(String path, boolean stringType, boolean sortTypeAsc, String subFolderPath) {
        this.path = path;
        this.stringType = stringType;
        this.sortTypeAsc = sortTypeAsc;
        this.subFolderPath = subFolderPath;
    }

    public String getSubFolderPath() {
        return subFolderPath;
    }

    public void setSubFolderPath(String subFolderPath) {
        this.subFolderPath = subFolderPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}
