public class OutputFile {
    private String path;
//    Можно убрать поле stringType
//    private boolean stringType;
    private boolean sortTypeAsc;

    public OutputFile(String path, boolean sortTypeAsc) {
        this.path = path;
//        this.stringType = stringType;
        this.sortTypeAsc = sortTypeAsc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public boolean isStringType() {
//        return stringType;
//    }

//    public void setStringType(boolean stringType) {
//        this.stringType = stringType;
//    }

    public boolean isSortTypeAsc() {
        return sortTypeAsc;
    }

    public void setSortTypeAsc(boolean sortTypeAsc) {
        this.sortTypeAsc = sortTypeAsc;
    }
}
