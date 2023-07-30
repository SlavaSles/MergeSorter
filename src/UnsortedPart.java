import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UnsortedPart {
    private static ArrayList<String> errorFilePaths = new ArrayList<>();
    private static int errorFilesIndex = -1;

    public static ArrayList<String> getErrorFilePaths() {
        return new ArrayList<>(errorFilePaths);
    }

    public static int getErrorFilesIndex() {
        return Integer.valueOf(errorFilesIndex);
    }

    public static String findAnotherCurrentStrValue(InputFile input, Reader reader, OutputFile output) throws IOException{
        errorFilesIndex++;
        errorFilePaths.add(output.getSubFolderPath() + "errorFile" + (errorFilesIndex + 1) + ".txt");
        String firstStrValue = input.getPreviousStrValue();
        String wrongStrValue = input.getCurrentStrValue();
        String newCurrentStrValue = null;
        String nextStrValue;
        boolean reverse = false;
        if (input.isSortTypeAsc() != output.isSortTypeAsc()) {
            reverse = true;
        }
        try (PrintWriter errorFileWriter = new PrintWriter(errorFilePaths.get(errorFilesIndex))) {
            errorFileWriter.write(wrongStrValue + "\n");
            do {
                if (reverse) {
                    nextStrValue = reader.reverseFileInReaderStr();
                } else {
                    nextStrValue = reader.fileInReaderStr();
                }
                if (nextStrValue == null) {
                    break;
                }
                if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) > 0 && output.isSortTypeAsc()) {
                    wrongStrValue = nextStrValue;
                } else if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) <= 0 && output.isSortTypeAsc()) {
                    newCurrentStrValue = nextStrValue;
                    break;
                } else if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) < 0 && !output.isSortTypeAsc()) {
                    wrongStrValue = nextStrValue;
                } else if (firstStrValue.toLowerCase().compareTo(nextStrValue.toLowerCase()) >= 0 && !output.isSortTypeAsc()) {
                    newCurrentStrValue = nextStrValue;
                    break;
                }
                errorFileWriter.write(wrongStrValue + "\n");
            } while (newCurrentStrValue == null);

            if (newCurrentStrValue == null) {
                reader.close();
            }
            errorFileWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newCurrentStrValue;
    }
    
    public static Integer findAnotherCurrentIntValue(InputFile input, Reader reader, OutputFile output) throws IOException {
        errorFilesIndex++;
        errorFilePaths.add(output.getSubFolderPath() + "errorFile" + (errorFilesIndex + 1) + ".txt");
        int wrongIntValue = input.getCurrentIntValue();
        int firstIntValue = input.getPreviousIntValue();
        Integer newCurrentIntValue = null;
        Integer nextIntValue;
        boolean reverse = false;
        if (input.isSortTypeAsc() != output.isSortTypeAsc()) {
            reverse = true;
        }
        try (PrintWriter errorFileWriter = new PrintWriter(errorFilePaths.get(errorFilesIndex))) {
            errorFileWriter.write(wrongIntValue + "\n");
            do {
                if (reverse) {
                    nextIntValue = reader.reverseFileInReaderInt();
                } else {
                    nextIntValue = reader.fileInReaderInt();
                }
                if (nextIntValue == null) {
                    break;
                }
                if (firstIntValue > nextIntValue && output.isSortTypeAsc()) {
                    wrongIntValue = nextIntValue;
                } else if (firstIntValue <= nextIntValue && output.isSortTypeAsc()) {
                    newCurrentIntValue = nextIntValue;
                    break;
                } else if (firstIntValue < nextIntValue && !output.isSortTypeAsc()) {
                    wrongIntValue = nextIntValue;
                } else if (firstIntValue >= nextIntValue && !output.isSortTypeAsc()) {
                    newCurrentIntValue = nextIntValue;
                    break;
                }
                errorFileWriter.write(wrongIntValue + "\n");
            } while (newCurrentIntValue == null);

            if (newCurrentIntValue == null) {
                reader.close();
            }
            errorFileWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newCurrentIntValue;
    }
}
