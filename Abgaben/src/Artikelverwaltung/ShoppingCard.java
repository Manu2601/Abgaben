import java.util.ArrayList;

public class ShoppingCard {
    ArrayList<Article> articles = new ArrayList<>();
    public void addArticle(Article article){
        articles.add(article);
    }

public double showBill(){
    double preis = 0; 
    for (Article article : articles) {
        preis = preis + article.getPrice();
    }
    return preis;
}

    @Override
    public String toString() {
        String string = "";
        for (Article article : articles) {
            string += article.toString();
            string = String.format("%s\n", string);
        }
        string = String.format("%sGesamtpreis: %.2f", string, showBill());
        return string;
    }

      

}
