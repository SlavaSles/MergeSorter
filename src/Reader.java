import java.io.IOException;
import java.io.RandomAccessFile;

public class Reader extends RandomAccessFile {
    private String path;
    private long position;

    public Reader(String path) throws IOException {
        super(path, "r");
        this.path = path;
        this.position = this.length() - 1;
    }
    public Integer fileInReaderInt() throws IOException {
        String line = "";
        Integer number = null;
        do {
            line = this.readLine();
            if (line != null && line != "") {
                try {
                    number = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка преобразования строки в целое число!");;
                }
            }
            if (number != null) {
                break;
            }
        } while (line != null);
        return number;
    }

    public String fileInReaderStr() throws IOException {
        String line = "";
        do {
            line = this.readLine();
            if (line == null) {
                break;
            }
            line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
        } while (line.length() != line.replaceAll("[\s\t]", "").length() || line.length() == 0);
        return line;
    }

    public Integer reverseFileInReaderInt() throws IOException {
        String line = "";
        Integer number = null;
        do {
            startLinePosition();
            if (position < 0) {
                return null;
            }
            this.seek(position);
            line = this.readLine();
            position -= 2;
            if (line != null || line != "") {
                try {
                    number = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка преобразования строки в целое число!");
                }
                if (number != null) {
                    break;
                }
            }
        } while (line != null);
        return number;
    }
    public String reverseFileInReaderStr() throws IOException {
        String line = "";
        String string = null;
        do {
            startLinePosition();
            if (position < 0) {
                return null;
            }
            this.seek(position);
            line = this.readLine();
            position -= 2;
            if (line == null) {
                break;
            }
            line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
            if (line.length() == line.replaceAll("[\s\t]", "").length() && line.length() != 0) {
                string = line;
            }
        } while (string == null);
        return string;
    }

    private void startLinePosition() throws IOException {
        int symbol;
        while (position > 0) {
            this.seek(position);
            symbol = this.read();
            if (symbol == 10 && position != (this.length() - 1)) {
                position++;
                break;
            } else if (symbol == 10 && position == (this.length() - 1)) {
                position--;
            }
            position--;
        }
    }
}
