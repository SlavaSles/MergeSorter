import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UnsortedPart {
    private static ArrayList<String> errorFilePathes = new ArrayList<>();
    private static ArrayList<PrintWriter> errorFileWriters = new ArrayList<>();
    private static int errorFilesIndex = -1;
    public static String findAnotherCurrentStrValue(InputFile input, Reader reader, boolean outputSortTypeAsc) throws IOException {
        errorFilesIndex++;
//        ToDo: Убрать папки из пути!
        errorFilePathes.add("data/errorFile" + (errorFilesIndex + 1) + ".txt");
        errorFileWriters.add(new PrintWriter(errorFilePathes.get(errorFilesIndex)));
        String wrongStrValue = input.getCurrentStrValue();
        errorFileWriters.get(errorFilesIndex).write(wrongStrValue + "\n");
        String firstStrValue = input.getPreviousStrValue();
        String newCurrentStrValue = null;
        String nextStrValue;
        boolean reverse = false;
        if (input.isSortTypeAsc() != outputSortTypeAsc) {
            reverse = true;
        }
        do {
            if (reverse) {
                nextStrValue = reader.reverseFileInReaderStr();
            } else {
                nextStrValue = reader.fileInReaderStr();
            }
            if (nextStrValue == null) {
                break;
            }
            if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) > 0 && outputSortTypeAsc) {
                wrongStrValue = nextStrValue;
            } else if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) <= 0 && outputSortTypeAsc) {
                newCurrentStrValue = nextStrValue;
                break;
            } else if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) < 0 && !outputSortTypeAsc) {
                wrongStrValue = nextStrValue;
            } else if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) >= 0 && !outputSortTypeAsc) {
                newCurrentStrValue = nextStrValue;
                break;
            }
            errorFileWriters.get(errorFilesIndex).write(wrongStrValue + "\n");
        } while (newCurrentStrValue == null);

        if (newCurrentStrValue == null) {
            reader.closeFile();
        }
        errorFileWriters.get(errorFilesIndex).flush();
        errorFileWriters.get(errorFilesIndex).close();
        return newCurrentStrValue;
    }
    
    public static Integer findAnotherCurrentIntValue(InputFile input, Reader reader, boolean outputSortTypeAsc) throws IOException {
        errorFilesIndex++;
//        ToDo: Убрать папки из пути!
        errorFilePathes.add("data/errorFile" + (errorFilesIndex + 1) + ".txt");
        errorFileWriters.add(new PrintWriter(errorFilePathes.get(errorFilesIndex)));
        int wrongIntValue = input.getCurrentIntValue();
        errorFileWriters.get(errorFilesIndex).write(wrongIntValue + "\n");
        int firstIntValue = input.getPreviousIntValue();
        Integer newCurrentIntValue = null;
        Integer nextIntValue;
        boolean reverse = false;
        if (input.isSortTypeAsc() != outputSortTypeAsc) {
            reverse = true;
        }
        do {
            if (reverse) {
                nextIntValue = reader.reverseFileInReaderInt();
            } else {
                nextIntValue = reader.fileInReaderInt();
            }
            if (nextIntValue == null) {
                break;
            }
            if (firstIntValue > nextIntValue && outputSortTypeAsc) {
                wrongIntValue = nextIntValue;
            } else if (firstIntValue <= nextIntValue && outputSortTypeAsc) {
                newCurrentIntValue = nextIntValue;
                break;
            } else if (firstIntValue < nextIntValue && !outputSortTypeAsc) {
                wrongIntValue = nextIntValue;
            } else if (firstIntValue >= nextIntValue && !outputSortTypeAsc) {
                newCurrentIntValue = nextIntValue;
                break;
            }
            errorFileWriters.get(errorFilesIndex).write(wrongIntValue + "\n");
        } while (newCurrentIntValue == null);

        if (newCurrentIntValue == null) {
            reader.closeFile();
        }
        errorFileWriters.get(errorFilesIndex).flush();
        errorFileWriters.get(errorFilesIndex).close();
        return newCurrentIntValue;
    }
}
