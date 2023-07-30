import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportSettings {
    SortSettings sortSettings;
    String[] args;

    public ImportSettings(String[] args) {
        this.args = args;
        this.sortSettings = new SortSettings();
    }

    public SortSettings argsParser() throws IllegalArgumentException  {
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
                Pattern pattern = Pattern.compile(SortSettings.FILENAME_REGEX);
                Matcher matcher = pattern.matcher(args[i]);
                if (matcher.find()) {
                    if (!outputFilenameIsExist) {
                        sortSettings.getOutFilePathes().add(matcher.group(1).trim());
//                        ToDo: Убрать папки из пути!
                        sortSettings.setSubFolderPath((matcher.group(2) != null) ? matcher.group(2) : "");
                        sortSettings.getOutFilePathes().add(sortSettings.getSubFolderPath() + "outTemp1.txt");
                        sortSettings.getOutFilePathes().add(sortSettings.getSubFolderPath() + "outTemp2.txt");
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
        return sortSettings;
    }
}
