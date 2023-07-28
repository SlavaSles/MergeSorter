import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

// Может надо отнаследовать класс Reader от RandomAccessFile?
public class Reader {
    private String path;
    private long position;
    private RandomAccessFile inputFile;

    public Reader(String path) throws IOException {
//        ToDo: Проверить работу программы в случае, если файлы отсутствуют!
        this.path = path;
        inputFile = new RandomAccessFile(path, "r");
        this.position = inputFile.length() - 1;
    }

    public Integer fileInReaderInt() throws IOException {
        String line = "";
        Integer number = null;
        line = inputFile.readLine();
        if (line != null) {
            number = Integer.parseInt(line);
        }
        return number;
    }

    public String fileInReaderStr() throws IOException {
        String line = "";
        do {
            line = inputFile.readLine();
            if (line == null) {
                break;
            }
            line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
        } while (line.length() != line.replaceAll("[\s\t]", "").length());
        return line;
    }

    public Integer reverseFileInReaderInt() throws IOException {
        startLinePosition();
        if (position < 0) {
            return null;
        }
        String line = "";
        Integer number = null;
        inputFile.seek(position);
        line = inputFile.readLine();
        if (line != null) {
            number = Integer.parseInt(line);
        }
        position -= 2;
        return number;
    }
//    Todo: Файл не должен заканчивааться пустой строкой, иначе он будет игнорироваться
    public String reverseFileInReaderStr() throws IOException {
        startLinePosition();
        if (position < 0) {
            return null;
        }
        boolean repeat;
        String line = "";
        do {
            inputFile.seek(position);
            line = inputFile.readLine();
            if (line == null) {
                break;
            }
            line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
            repeat = line.length() != line.replaceAll("[\s\t]", "").length();
            if (repeat) {
                position -= 2;
                startLinePosition();
            }
        } while (repeat);
        position -= 2;
        return line;
    }

    private void startLinePosition() throws IOException {
        int symbol;
        while (position > 0) {
            inputFile.seek(position);
            symbol = inputFile.read();
            if (symbol == 10) {
                position++;
                break;
            }
            position--;
        }
    }

    public void closeFile() throws IOException {
        inputFile.close();
    }

    public RandomAccessFile getInputFile() {
        return inputFile;
    }
}
