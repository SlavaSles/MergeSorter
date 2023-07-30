import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SorterLogic {

    private String[] args;

    public SorterLogic(String[] args) {
        this.args = args;
    }

    public void startSorting() {
        ImportSettings importSettings = new ImportSettings(args);
        SortSettings sortSettings = importSettings.argsParser();
        ArrayList<InputFile> inputFiles = new ArrayList<>();
        ArrayList<OutputFile> outputFiles = new ArrayList<>();
        Iterator<String> iteratorInFilesPath = sortSettings.getInFilePathes().iterator();
        while (iteratorInFilesPath.hasNext()) {
            InputFile inputFile = new InputFile(iteratorInFilesPath.next(), sortSettings.isStringType());
            if (inputFile.isExist()) {
                inputFiles.add(inputFile);
            } else {
                iteratorInFilesPath.remove();
            }
        }
        for (int i = 0; i < sortSettings.getOutFilePathes().size(); i++) {
            outputFiles.add(new OutputFile(sortSettings.getOutFilePathes().get(i),
                    sortSettings.isStringType(), sortSettings.isSortTypeAsc(), sortSettings.getSubFolderPath()));
        }
        int inputFilesCount = sortSettings.getInFilePathes().size();
        try {
            int step = 1;
            System.out.println("Количество входных файлов равно " + inputFilesCount + ".");
            System.out.println(step +". Сортировка слиянием входных файлов и сохранение результата в выходной файл.");
            if (inputFilesCount == 1) {
                oneInputMerge(inputFiles, outputFiles);
            } else if (inputFilesCount >= 2) {
                twoOrMoreInputsMerge(inputFiles, outputFiles);
            }
            if (UnsortedPart.getErrorFilesIndex() > -1) {
                step++;
                System.out.println(step + ". Сортировка фрагментов файлов с нарушенной последовательностью и добавление их в выходной файл.");
                mergeErrorFiles(sortSettings, outputFiles);
            }
            step++;
            System.out.println(step + ". Удаление временных файлов.");
            deleteTemporaryFiles(outputFiles);
            System.out.println("Сортировка слиянием завершена.");
        } catch (
        FileNotFoundException e) {
            e.printStackTrace();
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeErrorFiles(SortSettings sortSettings, ArrayList<OutputFile> outputFiles) throws IOException {
        int inputFileNumber = 0;
        int outputFileNumber = 1;
        for (int index = 0; index <= UnsortedPart.getErrorFilesIndex(); index++) {
            Sorter errorFileSorter = new Sorter();
            if (index == UnsortedPart.getErrorFilesIndex() && UnsortedPart.getErrorFilesIndex() != 0) {
                outputFileNumber = 0;
            } else {
                outputFileNumber = ((index % 2) == 0) ? 1 : 2;
            }
            InputFile inputFile = new InputFile(outputFiles.get(inputFileNumber).getPath(),
                    outputFiles.get(inputFileNumber).isStringType(), outputFiles.get(inputFileNumber).isSortTypeAsc());
            if (sortSettings.isStringType()) {
                errorFileSorter.sortErrorFileStr(
                        UnsortedPart.getErrorFilePaths().get(index), inputFile, outputFiles.get(outputFileNumber));
            } else {
                errorFileSorter.sortErrorFileInt(UnsortedPart.getErrorFilePaths().get(index), inputFile, outputFiles.get(outputFileNumber));
            }
            inputFileNumber = outputFileNumber;
        }
        if (outputFileNumber != 0) {
            File tempOutput = new File(outputFiles.get(outputFileNumber).getPath());
            File out = new File(outputFiles.get(0).getPath());
            File outOld = new File(sortSettings.getSubFolderPath() + "out1Temp1.txt");
            boolean firstRename = out.renameTo(outOld);
            boolean secondRename = tempOutput.renameTo(out);
//            Для перестраховки на случай, если переименование файлов даст ошибку.
            if (!secondRename) {
                InputFile inputFile = new InputFile(outputFiles.get(outputFileNumber).getPath(),
                        outputFiles.get(outputFileNumber).isStringType(), outputFiles.get(outputFileNumber).isSortTypeAsc());
                if (sortSettings.isStringType()) {
                    Sorter.oneInputToOutputStr(inputFile, outputFiles.get(0));
                } else {
                    Sorter.oneInputToOutputInt(inputFile, outputFiles.get(0));
                }
            }
        }
    }

    public void oneInputMerge(ArrayList<InputFile> inputFiles, ArrayList<OutputFile> outputFiles) throws IOException {
        if (inputFiles.get(0).isStringType()) {
            Sorter.oneInputToOutputStr(inputFiles.get(0), outputFiles.get(0));
        } else {
            Sorter.oneInputToOutputInt(inputFiles.get(0), outputFiles.get(0));
        }
    }

    public void twoOrMoreInputsMerge(ArrayList<InputFile> inputFiles, ArrayList<OutputFile> outputFiles) throws IOException {
        int outputFileNumber = 0;
        if (inputFiles.size() > 2) {
            outputFileNumber = 1;
        }
        for (int mergeCounter = 1; mergeCounter <= (inputFiles.size() - 1); mergeCounter++) {
            if (inputFiles.get(mergeCounter).isStringType()) {
                Sorter.mergeFilesStr(inputFiles.get(mergeCounter - 1), inputFiles.get(mergeCounter),
                        outputFiles.get(outputFileNumber));
            } else {
                Sorter.mergeFilesInt(inputFiles.get(mergeCounter - 1), inputFiles.get(mergeCounter),
                        outputFiles.get(outputFileNumber));
            }
            inputFiles.get(mergeCounter).setPath(((mergeCounter % 2) == 0) ? outputFiles.get(2).getPath() :
                    outputFiles.get(1).getPath());
            inputFiles.get(mergeCounter).setSortTypeAsc(((mergeCounter % 2) == 0) ? outputFiles.get(2).isSortTypeAsc() :
                    outputFiles.get(1).isSortTypeAsc());
            outputFileNumber = ((mergeCounter % 2) == 0) ? 1 : 2;
            if (mergeCounter == (inputFiles.size() - 2)) {
                outputFileNumber = 0;
            }
        }
    }

    private void deleteTemporaryFiles(ArrayList<OutputFile> outputFiles) {

        for (int i = 1; i < outputFiles.size(); i++) {
            File outTemp = new File(outputFiles.get(i).getPath());
            outTemp.delete();
        }
        for (int i = 0; i <= UnsortedPart.getErrorFilesIndex(); i++) {
            File errorFile = new File(UnsortedPart.getErrorFilePaths().get(i));
            errorFile.delete();
        }
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            String name = file.getName();
//            Pattern pattern1 = Pattern.compile("^errorFile\\d+\\.txt$");
//            Matcher matcher1 = pattern1.matcher(name);
//            if (matcher1.find()) {
//                file.delete();
//            }
//            Pattern pattern2 = Pattern.compile("^out[1]?Temp\\d+\\.txt$");
//            Matcher matcher2 = pattern2.matcher(name);
//            if (matcher2.find()) {
//                file.delete();
//            }
//        }
    }
}
