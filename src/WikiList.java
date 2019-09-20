import java.util.ArrayList;

public class WikiList {

    private String article;
    private ArrayList<WikiList> wikiLists;
    private ArrayList<String> route;
    private ArrayList<String> covered;

    public WikiList(String article, ArrayList<String> route, ArrayList<String> covered){
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

    public ArrayList<WikiList> addLinks(ArrayList<String> links){
        for (String link: links){
            if (!covered.contains(link)){
                ArrayList<String> newRoute = (ArrayList<String>) route.clone();
                covered.add(link);
                newRoute.add(article);
                wikiLists.add(new WikiList(link, newRoute, covered));
            }
        }
        for (WikiList wikiList : wikiLists){
            wikiList.setCovered(covered);
        }
        return wikiLists;
    }

    public void setCovered(ArrayList<String> covered){
        this.covered = covered;
    }

    public WikiList getWikiList(String article){
        for (WikiList wikiList : wikiLists){
            if (wikiList.getArticle().equals(article)) {
                return wikiList;
            }
        }
        return null;
    }

    public ArrayList<String> getRoute(){
        return (ArrayList<String>) route.clone();
    }
}
