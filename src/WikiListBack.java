import java.util.ArrayList;
import java.util.Collections;

public class WikiListBack {

    private String article;
    private ArrayList<WikiListBack> wikiLists;
    private ArrayList<String> route;
    private ArrayList<String> covered;

    public WikiListBack(String article, ArrayList<String> route, ArrayList<String> covered){
        this.article = article;
        this.route = route;
        this.wikiLists = new ArrayList<>();
        this.covered = covered;
    }

    public String getArticle() {
        return article;
    }

    public String linkedFrom(){
        if (route.isEmpty()){
            return "none";
        }
        return route.get(route.size()-1);
    }

    public ArrayList<WikiListBack> addLinks(ArrayList<String> links){
        for (String link: links){
            if (!covered.contains(link)){
                ArrayList<String> newRoute = (ArrayList<String>) route.clone();
                covered.add(link);
                newRoute.add(article);
                wikiLists.add(new WikiListBack(link, newRoute, covered));
            }
        }
        for (WikiListBack wikiList : wikiLists){
            wikiList.setCovered(covered);
        }
        return wikiLists;
    }

    public ArrayList<String> getRoute(){
        Collections.reverse(route);
        ArrayList<String> routeReverse = (ArrayList<String>) route.clone();
        routeReverse.add(0, article);
        Collections.reverse(route);
        return routeReverse;
    }

    private void setCovered(ArrayList<String> covered){
        this.covered = covered;
    }
}
