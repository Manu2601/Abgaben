import java.util.Scanner;

public class Main {
    static Playground pg = new Playground();
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        
        pg = SchiffFactory.getPlayground();
        while (!pg.fertig()) {
            System.out.printf("Anzahl übriger Schiffe: %d\n", pg.getAnzahlSchiffe());
            System.out.println(pg);
            System.out.println("x y: ");
            String str = s.nextLine();
            String[] s = str.split(" ");
            Position p = new Position(Integer.parseInt(s[1]), Integer.parseInt(s[0]));
            pg.schuss(p);
        }

        System.out.printf("Benötigte Schüsse: %d \ndavon Treffer: %d",
                pg.getWasserBeschossen() + pg.getSchiffBeschossen(), pg.getSchiffBeschossen());
    }
}
