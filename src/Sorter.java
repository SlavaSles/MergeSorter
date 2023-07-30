import java.io.*;
import java.util.ArrayList;

public class Sorter {

    public void sortErrorFileStr(String path, InputFile input, OutputFile output) {
        ArrayList<String> strings = new ArrayList<>();
        try (Reader readerErrorFile = new Reader(path)) {
            String line = "";
            while (true) {
                line = readerErrorFile.fileInReaderStr();
                if (line == null) {
                    break;
                }
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        strings = sortStr(strings, output.isSortTypeAsc());
        mergeErrorToOutStr(strings, input, output);
    }

    private ArrayList<String> sortStr(ArrayList<String> strings, boolean sortTypeAsc) {
        if (strings == null) {
            return null;
        }
        if (strings.size() < 2) {
            return strings;
        }
        int middle = strings.size() / 2;
        ArrayList<String> leftStrings = new ArrayList<>();
        for (int i = 0; i < middle; i++) {
            leftStrings.add(strings.get(i));
        }
        ArrayList<String> rightStrings = new ArrayList<>();
        for (int i = middle; i < strings.size(); i++) {
            rightStrings.add(strings.get(i));
        }
        leftStrings = sortStr(leftStrings, sortTypeAsc);
        rightStrings = sortStr(rightStrings, sortTypeAsc);
        return mergeStr(leftStrings, rightStrings, sortTypeAsc);
    }

    private ArrayList<String> mergeStr(ArrayList<String> leftStrings, ArrayList<String> rightStrings, boolean sortTypeAsc) {
        ArrayList<String> strings = new ArrayList<>();
        int length = leftStrings.size() + rightStrings.size();
        for (int i = 0, positionLeft = 0, positionRight = 0; i < length; i++) {
            if (sortTypeAsc) {
                strings.add(positionLeft < leftStrings.size() && (positionRight == rightStrings.size() ||
                        leftStrings.get(positionLeft).compareTo(rightStrings.get(positionRight)) < 0) ?
                        leftStrings.get(positionLeft++) : rightStrings.get(positionRight++));
            } else {
                strings.add(positionLeft < leftStrings.size() && (positionRight == rightStrings.size() ||
                        leftStrings.get(positionLeft).compareTo(rightStrings.get(positionRight)) > 0) ?
                        leftStrings.get(positionLeft++) : rightStrings.get(positionRight++));
            }
        }
        return strings;
    }

    private void mergeErrorToOutStr(ArrayList<String> strings, InputFile input, OutputFile output) {
        try (Reader readerIn = new Reader(input.getPath());
             PrintWriter writer = new PrintWriter(output.getPath())) {
            input.setCurrentStrValue(readerIn.fileInReaderStr());
            String outputStr = null;
            int index = 0;
            while (input.getCurrentStrValue() != null || index < strings.size()) {
                if (input.getCurrentStrValue() == null) {
                    outputStr = strings.get(index++);
                } else if (index == strings.size()) {
                    outputStr = input.getCurrentStrValue();
                    input.setCurrentStrValue(readerIn.fileInReaderStr());
//                Сравнение текста выполняется в нижнем регистре, чтобы не было нарушения алфавитного порядка в
//                отсортированном списке
                } else if (input.getCurrentStrValue().toLowerCase().compareTo(strings.get(index).toLowerCase()) < 0 && output.isSortTypeAsc()) {
                    outputStr = input.getCurrentStrValue();
                    input.setCurrentStrValue(readerIn.fileInReaderStr());
                } else if (input.getCurrentStrValue().toLowerCase().compareTo(strings.get(index).toLowerCase()) >= 0 && output.isSortTypeAsc()) {
                    outputStr = strings.get(index++);
                } else if (input.getCurrentStrValue().toLowerCase().compareTo(strings.get(index).toLowerCase()) > 0 && !output.isSortTypeAsc()) {
                    outputStr = input.getCurrentStrValue();
                    input.setCurrentStrValue(readerIn.fileInReaderStr());
                } else if ((input.getCurrentStrValue().toLowerCase().compareTo(strings.get(index).toLowerCase()) <= 0 && !output.isSortTypeAsc())) {
                    outputStr = strings.get(index++);
                }
                writer.write(outputStr.toString() + "\n");

            }
            input.setPreviousStrValue(null);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortErrorFileInt(String path, InputFile input, OutputFile output) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try (Reader readerErrorFile = new Reader(path)) {
            Integer number = null;
            while (true) {
                number = readerErrorFile.fileInReaderInt();
                if (number == null) {
                    break;
                }
                numbers.add(number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        numbers = sortInt(numbers, output.isSortTypeAsc());
        mergeErrorToOutInt(numbers, input, output);
    }

    private ArrayList<Integer> sortInt(ArrayList<Integer> numbers, boolean sortTypeAsc) {
        if (numbers == null) {
            return null;
        }
        if (numbers.size() < 2) {
            return numbers;
        }
        int middle = numbers.size() / 2;
        ArrayList<Integer> leftNumbers = new ArrayList<>();
        for (int i = 0; i < middle; i++) {
            leftNumbers.add(numbers.get(i));
        }
        ArrayList<Integer> rightNumbers = new ArrayList<>();
        for (int i = middle; i < numbers.size(); i++) {
            rightNumbers.add(numbers.get(i));
        }
        leftNumbers = sortInt(leftNumbers, sortTypeAsc);
        rightNumbers = sortInt(rightNumbers, sortTypeAsc);
        return mergeInt(leftNumbers, rightNumbers, sortTypeAsc);
    }

    private ArrayList<Integer> mergeInt(ArrayList<Integer> leftNumbers, ArrayList<Integer> rightNumbers, boolean sortTypeAsc) {
        ArrayList<Integer> numbers = new ArrayList<>();
        int length = leftNumbers.size() + rightNumbers.size();
        for (int i = 0, positionLeft = 0, positionRight = 0; i < length; i++) {
            if (sortTypeAsc) {
                numbers.add(positionLeft < leftNumbers.size() && (positionRight == rightNumbers.size() ||
                        leftNumbers.get(positionLeft) < rightNumbers.get(positionRight)) ?
                        leftNumbers.get(positionLeft++) : rightNumbers.get(positionRight++));
            } else {
                numbers.add(positionLeft < leftNumbers.size() && (positionRight == rightNumbers.size() ||
                        leftNumbers.get(positionLeft) > rightNumbers.get(positionRight)) ?
                        leftNumbers.get(positionLeft++) : rightNumbers.get(positionRight++));
            }
        }
        return numbers;
    }

    private void mergeErrorToOutInt(ArrayList<Integer> numbers, InputFile input, OutputFile output) {
        try (Reader readerIn = new Reader(input.getPath());
             PrintWriter writer = new PrintWriter(output.getPath())) {
            input.setCurrentIntValue(readerIn.fileInReaderInt());
            Integer outputInt = null;
            int index = 0;
            while (input.getCurrentIntValue() != null || index < numbers.size()) {
                if (input.getCurrentIntValue() == null) {
                    outputInt = numbers.get(index++);
                } else if (index == numbers.size()) {
                    outputInt = input.getCurrentIntValue();
                    input.setCurrentIntValue(readerIn.fileInReaderInt());
//                Сравнение текста выполняется в нижнем регистре, чтобы не было нарушения алфавитного порядка в
//                отсортированном списке
                } else if (input.getCurrentIntValue() < numbers.get(index) && output.isSortTypeAsc()) {
                    outputInt = input.getCurrentIntValue();
                    input.setCurrentIntValue(readerIn.fileInReaderInt());
                } else if (input.getCurrentIntValue() >= numbers.get(index) && output.isSortTypeAsc()) {
                    outputInt = numbers.get(index++);
                } else if (input.getCurrentIntValue() > numbers.get(index) && !output.isSortTypeAsc()) {
                    outputInt = input.getCurrentIntValue();
                    input.setCurrentIntValue(readerIn.fileInReaderInt());
                } else if ((input.getCurrentIntValue() <= numbers.get(index) && !output.isSortTypeAsc())) {
                    outputInt = numbers.get(index++);
                }
                writer.write(outputInt.toString() + "\n");

            }
            input.setPreviousStrValue(null);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void oneInputToOutputStr(InputFile input1, OutputFile output) {
        try (Reader readerIn1 = new Reader(input1.getPath());
             PrintWriter writer = new PrintWriter(output.getPath())) {
            boolean reverse = false;
            if (input1.isSortTypeAsc() != output.isSortTypeAsc()) {
                reverse = true;
            }
            input1.setCurrentStrValue((reverse) ? readerIn1.reverseFileInReaderStr() : readerIn1.fileInReaderStr());
            String outputStr = null;
            while (input1.getCurrentStrValue() != null) {
                checkSortTypeStr(input1, readerIn1, output);
                outputStr = input1.getCurrentStrValue();
                input1.setPreviousStrValue(input1.getCurrentStrValue());
                input1.setCurrentStrValue((reverse) ? readerIn1.reverseFileInReaderStr() : readerIn1.fileInReaderStr());
                writer.write(outputStr.concat("\r\n"));
            }
            input1.setPreviousStrValue(null);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void oneInputToOutputInt(InputFile input1, OutputFile output) {
        try (Reader readerIn1 = new Reader(input1.getPath());
             PrintWriter writer = new PrintWriter(output.getPath())) {
            boolean reverse = false;
            if (input1.isSortTypeAsc() != output.isSortTypeAsc()) {
                reverse = true;
            }
            input1.setCurrentIntValue((reverse) ? readerIn1.reverseFileInReaderInt() : readerIn1.fileInReaderInt());
            Integer outputInt = null;
            while (input1.getCurrentIntValue() != null) {
                checkSortTypeInt(input1, readerIn1, output);
                outputInt = input1.getCurrentIntValue();
                input1.setPreviousIntValue(input1.getCurrentIntValue());
                input1.setCurrentIntValue((reverse) ? readerIn1.reverseFileInReaderInt() : readerIn1.fileInReaderInt());
                writer.write(outputInt.toString() + "\n");
            }
            input1.setPreviousIntValue(null);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void mergeFilesStr(InputFile input1, InputFile input2, OutputFile output) {
        try (Reader readerIn1 = new Reader(input1.getPath());
             Reader readerIn2 = new Reader(input2.getPath());
             PrintWriter writer = new PrintWriter(output.getPath())) {
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
            while (input1.getCurrentStrValue() != null || input2.getCurrentStrValue() != null) {
                checkSortTypeStr(input1, readerIn1, output);
                checkSortTypeStr(input2, readerIn2, output);
                if (input1.getCurrentStrValue() == null) {
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
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkSortTypeStr(InputFile input, Reader readerIn, OutputFile output) throws IOException {
        if (input.getPreviousStrValue() != null && input.getCurrentStrValue() != null && input.getPreviousStrValue().toLowerCase().
                compareTo(input.getCurrentStrValue().toLowerCase()) > 0 && output.isSortTypeAsc()) {
            input.setCurrentStrValue(UnsortedPart.findAnotherCurrentStrValue(input, readerIn, output));
        } else if (input.getPreviousStrValue() != null && input.getCurrentStrValue() != null && input.getPreviousStrValue().toLowerCase().
                compareTo(input.getCurrentStrValue().toLowerCase()) < 0 && !output.isSortTypeAsc()) {
            input.setCurrentStrValue(UnsortedPart.findAnotherCurrentStrValue(input, readerIn, output));
        }

    }

    public static void mergeFilesInt(InputFile input1, InputFile input2, OutputFile output) {
        try (Reader readerIn1 = new Reader(input1.getPath());
             Reader readerIn2 = new Reader(input2.getPath());
             PrintWriter writer = new PrintWriter(output.getPath())) {
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
            while (input1.getCurrentIntValue() != null || input2.getCurrentIntValue() != null) {
                checkSortTypeInt(input1, readerIn1, output);
                checkSortTypeInt(input2, readerIn2, output);
                if (input1.getCurrentIntValue() == null) {
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
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkSortTypeInt(InputFile input, Reader readerIn, OutputFile output) throws IOException {
        if (input.getPreviousIntValue() != null && input.getCurrentIntValue() != null &&
                input.getPreviousIntValue() > input.getCurrentIntValue() && output.isSortTypeAsc()) {
            input.setCurrentIntValue(UnsortedPart.findAnotherCurrentIntValue(input, readerIn, output));
        } else if (input.getPreviousIntValue() != null && input.getCurrentIntValue() != null &&
                input.getPreviousIntValue() < input.getCurrentIntValue() && !output.isSortTypeAsc()) {
            input.setCurrentIntValue(UnsortedPart.findAnotherCurrentIntValue(input, readerIn, output));
        }
    }
}
