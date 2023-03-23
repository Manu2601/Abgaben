public class App {
    public static void main(String[] args) throws Exception {
        DVD dvd1 = new DVD(1, 2.22, "Test", 3, COUNTRYCODE.AUT);
        Book b1 = new Book(1, 5, "Arthur", "Arthurs Story", 2022);
        DigitalBook db1 = new DigitalBook(4, 12, "Manu", "Guter Titel", 2020, 1875);

        ShoppingCard sc1 = new ShoppingCard();
        sc1.addArticle(dvd1);
        sc1.addArticle(b1);
        sc1.addArticle(db1);
        System.out.println(sc1);
        }
}
