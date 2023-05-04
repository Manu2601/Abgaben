package Sockets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandling {
    private String str;
    private long length;

    public FileHandling(String str) {
        this.str = str;
    }

    public void safeFile() throws IOException {
        String[] string = str.split("#");
        File datei = new File("C:/tmp/Server/" + string[1]);
        FileWriter fw = new FileWriter(datei);
        fw.write(string[2]);
        fw.flush();
        fw.close();
        length = datei.length();
    }

    public long getLength() {
        return length;
    }

    public String getList() {
        File dir = new File("C:/tmp/Server/");
        File[] files = dir.listFiles();
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            if (file.isFile()) {
                sb.append(file.getName()).append("\n");
            }
        }
        return sb.toString();
    }
}
