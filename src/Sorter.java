import java.io.*;

public class Sorter {

    public static void oneInputToOutput(InputFile input1, OutputFile output) throws IOException {
        Reader readerIn1 = new Reader(input1.getPath());
        PrintWriter writer = new PrintWriter(output.getPath());
        // ToDo: Нужна проверка на отсортированность!
        String line = "";
//        Integer number;
        while (true) {
            if (input1.isSortTypeAsc() == output.isSortTypeAsc()) {
                line = readerIn1.fileInReaderStr();
            } else {
                line = readerIn1.reverseFileInReaderStr();
            }
////            if (input1.isStringType()) {
//            number = readerIn1.reverseFileInReaderInt();
//            if (number != null) {
//                line = String.valueOf(number);
//            } else {
//                line = null;
//            }
            if (line == null) {
                break;
            }
            writer.write(line.concat("\r\n"));
        }
        readerIn1.closeFile();
        writer.flush();
        writer.close();
    }

    public static void mergeFilesStr(InputFile input1, InputFile input2, OutputFile output) throws IOException {
        Reader readerIn1 = new Reader(input1.getPath());
        Reader readerIn2 = new Reader(input2.getPath());
        PrintWriter writer = new PrintWriter(output.getPath());
        boolean reverseIn1 = false;
        boolean reverseIn2 = false;
        if (input1.isSortTypeAsc() != output.isSortTypeAsc()) {
            reverseIn1 = true;
        }
        if (input2.isSortTypeAsc() != output.isSortTypeAsc()) {
            reverseIn2 = true;
        }
        input1.setCurrentStrValue((reverseIn1) ? readerIn1.reverseFileInReaderStr() : readerIn1.fileInReaderStr());
        input2.setCurrentStrValue((reverseIn2) ? readerIn2.reverseFileInReaderStr() : readerIn2.fileInReaderStr());
        String outputStr = null;
        while (input1.getCurrentStrValue() != null || input2.getCurrentStrValue()  != null) {
            if (input1.getPreviousStrValue() != null && input1.getCurrentStrValue() != null && input1.getPreviousStrValue().toLowerCase().
                    compareTo(input1.getCurrentStrValue().toLowerCase()) > 0 && output.isSortTypeAsc()) {
                input1.setCurrentStrValue(UnsortedPart.findAnotherCurrentStrValue(input1, readerIn1, output.isSortTypeAsc()));
            } else if (input1.getPreviousStrValue() != null && input1.getCurrentStrValue() != null && input1.getPreviousStrValue().toLowerCase().
                    compareTo(input1.getCurrentStrValue().toLowerCase()) < 0 && !output.isSortTypeAsc()) {
                input1.setCurrentStrValue(UnsortedPart.findAnotherCurrentStrValue(input1, readerIn1, output.isSortTypeAsc()));
            }
            if (input2.getPreviousStrValue() != null && input2.getCurrentStrValue() != null && input2.getPreviousStrValue().toLowerCase().
                    compareTo(input2.getCurrentStrValue().toLowerCase()) > 0 && output.isSortTypeAsc()) {
                input2.setCurrentStrValue(UnsortedPart.findAnotherCurrentStrValue(input2, readerIn2, output.isSortTypeAsc()));
            } else if (input2.getPreviousStrValue() != null && input2.getCurrentStrValue() != null && input2.getPreviousStrValue().toLowerCase().
                    compareTo(input2.getCurrentStrValue().toLowerCase()) < 0 && !output.isSortTypeAsc()) {
                input2.setCurrentStrValue(UnsortedPart.findAnotherCurrentStrValue(input2, readerIn2, output.isSortTypeAsc()));
            }
            if (input1.getCurrentStrValue()  == null) {
                outputStr = input2.getCurrentStrValue();
                input2.setPreviousStrValue(input2.getCurrentStrValue());
                input2.setCurrentStrValue((reverseIn2) ? readerIn2.reverseFileInReaderStr() : readerIn2.fileInReaderStr());
            } else if (input2.getCurrentStrValue() == null) {
                outputStr = input1.getCurrentStrValue();
                input1.setPreviousStrValue(input1.getCurrentStrValue());
                input1.setCurrentStrValue((reverseIn1) ? readerIn1.reverseFileInReaderStr() : readerIn1.fileInReaderStr());
//                Сравнение текста выполняется в нижнем регистре, чтобы не было нарушения алфавитного порядка в
//                отсортированном списке
            } else if (input1.getCurrentStrValue().toLowerCase().compareTo(input2.getCurrentStrValue().toLowerCase()) < 0 && output.isSortTypeAsc()) {
                outputStr = input1.getCurrentStrValue();
                input1.setPreviousStrValue(input1.getCurrentStrValue());
                input1.setCurrentStrValue((reverseIn1) ? readerIn1.reverseFileInReaderStr() : readerIn1.fileInReaderStr());
            } else if (input1.getCurrentStrValue().toLowerCase().compareTo(input2.getCurrentStrValue().toLowerCase()) >= 0 && output.isSortTypeAsc()) {
                outputStr = input2.getCurrentStrValue();
                input2.setPreviousStrValue(input2.getCurrentStrValue());
                input2.setCurrentStrValue((reverseIn2) ? readerIn2.reverseFileInReaderStr() : readerIn2.fileInReaderStr());
            } else if (input1.getCurrentStrValue().toLowerCase().compareTo(input2.getCurrentStrValue().toLowerCase()) > 0 && !output.isSortTypeAsc()) {
                outputStr = input1.getCurrentStrValue();
                input1.setPreviousStrValue(input1.getCurrentStrValue());
                input1.setCurrentStrValue((reverseIn1) ? readerIn1.reverseFileInReaderStr() : readerIn1.fileInReaderStr());
            } else if ((input1.getCurrentStrValue().toLowerCase().compareTo(input2.getCurrentStrValue().toLowerCase()) <= 0 && !output.isSortTypeAsc())) {
                outputStr = input2.getCurrentStrValue();
                input2.setPreviousStrValue(input2.getCurrentStrValue());
                input2.setCurrentStrValue((reverseIn2) ? readerIn2.reverseFileInReaderStr() : readerIn2.fileInReaderStr());
            }
            writer.write(outputStr.toString() + "\n");
        }
        input1.setPreviousStrValue(null);
        input2.setPreviousStrValue(null);
        readerIn1.closeFile();
        readerIn2.closeFile();
        writer.flush();
        writer.close();
    }

    public static void mergeFilesInt(InputFile input1, InputFile input2, OutputFile output) throws IOException {
        Reader readerIn1 = new Reader(input1.getPath());
        Reader readerIn2 = new Reader(input2.getPath());
        PrintWriter writer = new PrintWriter(output.getPath());
        boolean reverseIn1 = false;
        boolean reverseIn2 = false;
        if (input1.isSortTypeAsc() != output.isSortTypeAsc()) {
            reverseIn1 = true;
        }
        if (input2.isSortTypeAsc() != output.isSortTypeAsc()) {
            reverseIn2 = true;
        }
        input1.setCurrentIntValue((reverseIn1) ? readerIn1.reverseFileInReaderInt() : readerIn1.fileInReaderInt());
        input2.setCurrentIntValue((reverseIn2) ? readerIn2.reverseFileInReaderInt() : readerIn2.fileInReaderInt());
        Integer outputInt = null;
        while (input1.getCurrentIntValue() != null || input2.getCurrentIntValue()  != null) {
            if (input1.getPreviousIntValue() != null && input1.getCurrentIntValue() != null &&
                    input1.getPreviousIntValue() > input1.getCurrentIntValue() && output.isSortTypeAsc()) {
                input1.setCurrentIntValue(UnsortedPart.findAnotherCurrentIntValue(input1, readerIn1, output.isSortTypeAsc()));
            } else if (input1.getPreviousIntValue() != null && input1.getCurrentIntValue() != null &&
                    input1.getPreviousIntValue() < input1.getCurrentIntValue() && !output.isSortTypeAsc()) {
                input1.setCurrentIntValue(UnsortedPart.findAnotherCurrentIntValue(input1, readerIn1, output.isSortTypeAsc()));
            }
            if (input2.getPreviousIntValue() != null && input2.getCurrentIntValue() != null &&
                    input2.getPreviousIntValue() > input2.getCurrentIntValue() && output.isSortTypeAsc()) {
                input2.setCurrentIntValue(UnsortedPart.findAnotherCurrentIntValue(input2, readerIn2, output.isSortTypeAsc()));
            } else if (input2.getPreviousIntValue() != null && input2.getCurrentIntValue() != null &&
                    input2.getPreviousIntValue() < input2.getCurrentIntValue() && !output.isSortTypeAsc()) {
                input2.setCurrentIntValue(UnsortedPart.findAnotherCurrentIntValue(input2, readerIn2, output.isSortTypeAsc()));
            }
            if (input1.getCurrentIntValue()  == null) {
                outputInt = input2.getCurrentIntValue();
                input2.setPreviousIntValue(input2.getCurrentIntValue());
                input2.setCurrentIntValue((reverseIn2) ? readerIn2.reverseFileInReaderInt() : readerIn2.fileInReaderInt());
            } else if (input2.getCurrentIntValue() == null) {
                outputInt = input1.getCurrentIntValue();
                input1.setPreviousIntValue(input1.getCurrentIntValue());
                input1.setCurrentIntValue((reverseIn1) ? readerIn1.reverseFileInReaderInt() : readerIn1.fileInReaderInt());
            } else if (input1.getCurrentIntValue() < input2.getCurrentIntValue() && output.isSortTypeAsc()) {
                outputInt = input1.getCurrentIntValue();
                input1.setPreviousIntValue(input1.getCurrentIntValue());
                input1.setCurrentIntValue((reverseIn1) ? readerIn1.reverseFileInReaderInt() : readerIn1.fileInReaderInt());
            } else if (input1.getCurrentIntValue() >= input2.getCurrentIntValue() && output.isSortTypeAsc()) {
                outputInt = input2.getCurrentIntValue();
                input2.setPreviousIntValue(input2.getCurrentIntValue());
                input2.setCurrentIntValue((reverseIn2) ? readerIn2.reverseFileInReaderInt() : readerIn2.fileInReaderInt());
            } else if (input1.getCurrentIntValue() > input2.getCurrentIntValue() && !output.isSortTypeAsc()) {
                outputInt = input1.getCurrentIntValue();
                input1.setPreviousIntValue(input1.getCurrentIntValue());
                input1.setCurrentIntValue((reverseIn1) ? readerIn1.reverseFileInReaderInt() : readerIn1.fileInReaderInt());
            } else if (input1.getCurrentIntValue() <= input2.getCurrentIntValue() && !output.isSortTypeAsc()) {
                outputInt = input2.getCurrentIntValue();
                input2.setPreviousIntValue(input2.getCurrentIntValue());
                input2.setCurrentIntValue((reverseIn2) ? readerIn2.reverseFileInReaderInt() : readerIn2.fileInReaderInt());
            }
            writer.write(outputInt.toString() + "\n");
        }
        input1.setPreviousIntValue(null);
        input2.setPreviousIntValue(null);
        readerIn1.closeFile();
        readerIn2.closeFile();
        writer.flush();
        writer.close();
    }
}
