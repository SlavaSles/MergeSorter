import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InputFile {
    private String path;
    private boolean stringType;
    private boolean sortTypeAsc;
    private Integer currentIntValue;
    private Integer previousIntValue;
    private String currentStrValue;
    private String previousStrValue;

    public InputFile(String path, boolean stringType) {
        this.path = path;
        this.stringType = stringType;
        if (stringType) {
            this.sortTypeAsc = checkSortTypeStr();
        } else {
            this.sortTypeAsc = checkSortTypeInt();
        }
    }

    private boolean checkSortTypeInt() {
        ArrayList<Integer> ints = new ArrayList<>();
        boolean sortType = true;
        try {
            Reader reader = new Reader(path);
            for (int i = 0; ; i++) {
                ints.add(reader.fileInReaderInt());
                if (ints.get(i) == null) {
                    break;
                }
                if (i >= 1 && ints.get(0) < ints.get(i)) {
                    sortType = true;
                    break;
                } else if (i >= 1 && ints.get(0) > ints.get(i)) {
                    sortType = false;
                    break;
                }
            }
            reader.closeFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortType;
    }

    private boolean checkSortTypeStr() {
        ArrayList<String> strings = new ArrayList<>();
        boolean sortType = true;
        try {
            Reader reader = new Reader(path);
            for (int i = 0; ; i++) {
                strings.add(reader.fileInReaderStr());
                if (strings.get(i) == null) {
                    break;
                }
                if (i >= 1 && strings.get(0).toLowerCase().compareTo(strings.get(i).toLowerCase()) < 0) {
                    sortType = true;
                    break;
                } else if (i >= 1 && strings.get(0).toLowerCase().compareTo(strings.get(i).toLowerCase()) > 0) {
                    sortType = false;
                    break;
                }
            }
            reader.closeFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortType;
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

    public Integer getCurrentIntValue() {
        return currentIntValue;
    }

    public void setCurrentIntValue(Integer currentIntValue) {
        this.currentIntValue = currentIntValue;
    }

    public Integer getPreviousIntValue() {
        return previousIntValue;
    }

    public void setPreviousIntValue(Integer previousIntValue) {
        this.previousIntValue = previousIntValue;
    }

    public String getCurrentStrValue() {
        return currentStrValue;
    }

    public void setCurrentStrValue(String currentStrValue) {
        this.currentStrValue = currentStrValue;
    }

    public String getPreviousStrValue() {
        return previousStrValue;
    }

    public void setPreviousStrValue(String previousStrValue) {
        this.previousStrValue = previousStrValue;
    }
}
