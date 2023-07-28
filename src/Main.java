import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        SortSettings sortSettings = new SortSettings();
        try {
            argsParser(args, sortSettings);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ArrayList<InputFile> inputFiles = new ArrayList<>();
        ArrayList<OutputFile> outputFiles = new ArrayList<>();
        for (int i = 0; i < sortSettings.getInFilePathes().size(); i++) {
            InputFile inputFile = new InputFile(sortSettings.getInFilePathes().get(i),
                    sortSettings.isStringType());
            inputFiles.add(inputFile);
        }
        for (int i = 0; i < sortSettings.getOutFilePathes().size(); i++) {
            outputFiles.add(new OutputFile(sortSettings.getOutFilePathes().get(i), sortSettings.isSortTypeAsc()));
        }
        int inputFilesCount = sortSettings.getInFilePathes().size();
        try {
            if (inputFilesCount == 1) {
                System.out.println("Количество входных файлов = 1");
                oneInputMerge(inputFiles, outputFiles);
            } else if (inputFilesCount == 2) {
                System.out.println("Количество входных файлов = 2");
                twoInputsMerge(inputFiles, outputFiles);
            } else if (inputFilesCount > 2) {
                System.out.println("Количество входных файлов > 2");
                threeOrMoreInputsMerge(inputFiles, outputFiles);
            }
            System.out.println("Сортировка слиянием завершена!");
//            ToDo: Удалить все временные файлы по завершении программы
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void argsParser(String[] args, SortSettings sortSettings) throws IllegalArgumentException {
//        ToDO: исправить Regex
        String filenameRegex = "^(((\\w+([\\-_]?\\w+)*)/)?\\w*([\\-_]?\\w*)*\\.txt)$";
        boolean nextParamsFilenames = false;
        boolean outputFilenameIsExist = false;
        boolean wrongName = false;
        int parametersCount = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-") && args[i].length() == 2 && !nextParamsFilenames) {
                parametersCount++;
                switch (args[i]) {
                    case "-a" -> sortSettings.setSortTypeAsc(true);
                    case "-d" -> sortSettings.setSortTypeAsc(false);
                    case "-s" -> {
                        sortSettings.setStringType(true);
                        nextParamsFilenames = true;
                    }
                    case "-i" -> {
                        sortSettings.setStringType(false);
                        nextParamsFilenames = true;
                    }
                }
            } else if (args[i].contains("-") && args[i].length() == 2 && nextParamsFilenames) {
                parametersCount++;
            } else if (nextParamsFilenames) {
                Pattern pattern = Pattern.compile(filenameRegex);
                Matcher matcher = pattern.matcher(args[i]);
                if (matcher.find()) {
                    if (!outputFilenameIsExist) {
                        sortSettings.getOutFilePathes().add(matcher.group(1).trim());
//                        ToDo: Убрать папки из пути!
                        sortSettings.getOutFilePathes().add("data/outTemp1.txt");
                        sortSettings.getOutFilePathes().add("data/outTemp2.txt");
                        outputFilenameIsExist = true;
                    } else {
                        sortSettings.getInFilePathes().add(matcher.group(1).trim());
                    }
                } else {
                    wrongName = true;
                }
            }
            if (wrongName || parametersCount > 2) {
                break;
            }
        }
        String errorMessage = "";
        if (parametersCount == 0 || parametersCount > 2) {
            errorMessage = "В параметрах запуска указано неверное количество аргументов " +
                    "с режимом сортировки и типом данных!";
        } else if (!nextParamsFilenames) {
            errorMessage = "В параметрах запуска не указан тип данных!";
        } else if (wrongName) {
            errorMessage = "В параметрах запуска неверно указано имя файла!";
        } else if (sortSettings.getOutFilePathes().size() == 0) {
            errorMessage = "В параметрах запуска не указано имя выходного файла!";
        } else if (sortSettings.getInFilePathes().size() == 0) {
            errorMessage = "В параметрах запуска не указано имя входного файла!";
        }
        if (errorMessage.length() > 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void oneInputMerge(ArrayList<InputFile> inputFiles, ArrayList<OutputFile> outputFiles) throws IOException {
        Sorter.oneInputToOutput(inputFiles.get(0), outputFiles.get(0));
    }

    public static void twoInputsMerge(ArrayList<InputFile> inputFiles, ArrayList<OutputFile> outputFiles) throws IOException {
        if (inputFiles.get(0).isStringType()) {
            Sorter.mergeFilesStr(inputFiles.get(0), inputFiles.get(1), outputFiles.get(0));
        } else {
            Sorter.mergeFilesInt(inputFiles.get(0), inputFiles.get(1), outputFiles.get(0));
        }
    }

    public static void threeOrMoreInputsMerge(ArrayList<InputFile> inputFiles, ArrayList<OutputFile> outputFiles) throws IOException {
        int outputFileNumber = 1;
        for (int mergeCounter = 1; mergeCounter <= (inputFiles.size() - 1); mergeCounter++) {
            if (inputFiles.get(mergeCounter).isStringType()) {
                Sorter.mergeFilesStr(inputFiles.get(mergeCounter - 1), inputFiles.get(mergeCounter),
                        outputFiles.get(outputFileNumber));
            } else {
                Sorter.mergeFilesInt(inputFiles.get(mergeCounter - 1), inputFiles.get(mergeCounter),
                        outputFiles.get(outputFileNumber));
            }
//            System.out.println("MergeCounter = " + mergeCounter);
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
}
