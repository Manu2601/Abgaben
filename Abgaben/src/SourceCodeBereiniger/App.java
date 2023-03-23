import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws Exception {
        File quellOrdner = new File(
                "C:/Users/Raffl Manuel/OneDrive - HTL Anichstrasse/HTL/3AHWII/SWP/OP/Projekte/Abgaben/");
        File zielOrdner = new File("C:/tmp/BereinigerTest/");
        kopiereOrdner(quellOrdner, zielOrdner);
        getJava(zielOrdner);

    }

    static File[] files;
    static Path path;

    public static void getJava(File file) throws IOException {
        path = Paths.get(file.getAbsolutePath());
        if (file.isDirectory()) {
            files = file.listFiles((dir, name) -> {
                File[] filestest = dir.listFiles();
                for (File file2 : filestest) {
                    if (file2.getName().equals(name)) {
                        return name.endsWith(".java") || file2.isDirectory();
                    }
                }
                return false;
            });
            if (files != null) {
                for (File f : files) {
                    getJava(f);
                }
            }
        } else {
            readJava(file);

        }
    }

    static void readJava(File file) throws IOException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                    new FileInputStream(file.getAbsolutePath()), "UTF-8"));

    BereinigerWriter writer = new BereinigerWriter(new FileWriter(file.getParent()+"/bereinigt_"+file.getName()));
    String str = reader.readLine();
    while (str != null) {
        writer.append(str);
        writer.append("\n");
        str = reader.readLine();
    }
    reader.close();
    writer.close();

    }

    private static void kopiereOrdner(File quellOrdner, File zielOrdner) throws IOException {
        for (File quelle : quellOrdner.listFiles()) {
            File ziel = new File(zielOrdner, quelle.getName());
            if (quelle.isDirectory()) {
                if (!ziel.exists()) {
                    ziel.mkdirs();
                }
                kopiereOrdner(quelle, ziel);
            } else {
                try (InputStream in = new FileInputStream(quelle);
                        OutputStream out = new FileOutputStream(ziel)) {
                    byte[] buffer = new byte[4096];
                    int bytesGelesen;
                    while ((bytesGelesen = in.read(buffer)) > 0) {
                        out.write(buffer, 0, bytesGelesen);
                    }
                }
            }
        }
    }
}

// öäüÖÄÜß
