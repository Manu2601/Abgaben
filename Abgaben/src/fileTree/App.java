import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    private static int anzOrdner = 0;
    private static int anzDateien = 0;

    public static void fileTree(File file, String einrueckung, int filter) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        if (file.isDirectory()) {
            anzOrdner++;
            System.out.println(einrueckung + "├─ " + file.getName());
            File[] files = file.listFiles((dir, name) -> {
                File[] filestest = dir.listFiles();
                for (File file2 : filestest) {
                    if (file2.getName().equals(name)) {
                        switch(filter){
                            case 0: return true; 
                            case 1: return name.endsWith(".java") || file2.isDirectory(); 
                            case 2: return name.endsWith(".class") || file2.isDirectory(); 
                            case 3: return file2.getName().length() >= 10 || file2.isDirectory(); 
                            case 4: return file2.length() >= 1000000 || file2.isDirectory(); 
                        }
                    }
                }
                return false;
    
            });
            if (files != null) {
                for (File f : files) {
                    fileTree(f, einrueckung + "   ", filter);
                }
            }
        } else {
            System.out.println(einrueckung + "- " + file.getName() + " (Größe: " + Files.size(path)
                    + "B, Letzte Änderung: " + Files.getLastModifiedTime(path) + ", Author: " + Files.getOwner(path)
                    + ", Festplatte: " + Files.getFileStore(path) + ")");
            anzDateien++;
        }
    }

    public static void main(String[] args) throws IOException {
        App.fileTree(new File("D:/Raffl Manuel/Schule/HTL/3AHWII/SWP/OP/Projekte/fileTree"), "", 0); 
        // 0...kein Filter, 1...java-Datei, 2...class-Datei, 3...Name größer 10 Zeichen, 4...Datei größer als 1MB
        System.out.printf("Anzahl Ordner: %d\nAnzahl Dateien: %d", anzOrdner, anzDateien);
    }
}