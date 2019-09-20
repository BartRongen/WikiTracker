import java.io.IOException;
import java.util.ArrayList;

public class WikiTracker {

    private String stArticle;
    private String destination;
    private WikiList wikiList;
    private WikiListBack backList;
    private ArrayList<WikiList> currentLists;
    private ArrayList<WikiListBack> currentBackLists;

    public WikiTracker(String stArticle, String destination){
        this.stArticle = stArticle.replace(" ", "_");
        this.destination = destination.replace(" ", "_");
        ArrayList<String> covered = new ArrayList<>();
        covered.add(this.stArticle);
        wikiList = new WikiList(this.stArticle, new ArrayList<String>(), covered);
        ArrayList<String> coveredBack = new ArrayList<>();
        coveredBack.add(this.destination);
        backList = new WikiListBack(this.destination, new ArrayList<String>(), coveredBack);
        currentLists = new ArrayList<>();
        currentLists.add(wikiList);
        currentBackLists = new ArrayList<>();
        currentBackLists.add(backList);
    }

    public void getRoute() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList<String> route;
        ArrayList<ArrayList<String>> routes = new ArrayList<>();
        routes = findRoutes(currentLists, currentBackLists);
        int fronthops = 0;
        int backhops = 0;
        int progress = 1;
        boolean frontRead = true;

        ArrayList<WikiList> nextFrontList;
        ArrayList<WikiListBack> nextBackList;

        //while (route == null){
        while (routes.size() == 0){
            if (frontRead) {
                nextFrontList = new ArrayList<>();
                for (WikiList wikiList : currentLists) {
                    System.out.println("FrontList: hops: " + fronthops + ", progress: " + progress + "/" + currentLists.size() + ", linked from: " + wikiList.linkedFrom() + ", API calling for: " + wikiList.getArticle());
                    ArrayList<String> links = WikiAPI.getLinks(wikiList.getArticle());
                    progress++;
                    ArrayList<WikiList> newList = wikiList.addLinks(links);
                    nextFrontList.addAll(newList);

                    //check if we've already found a path
                    routes = findRoutes(newList, currentBackLists);
                    if (routes.size() > 0) {
                        break;
                    }
                }
                currentLists.clear();
                currentLists.addAll(nextFrontList);
                fronthops++;
                progress = 1;

                routes = findRoutes(currentLists, currentBackLists);
                frontRead = false;
            } else {
                nextBackList = new ArrayList<>();
                for (WikiListBack backList : currentBackLists){
                    System.out.println("BackList: hops: " + backhops + ", progress: " + progress + "/" + currentBackLists.size() + ", linked from: " + backList.linkedFrom() + ", API calling for: " + backList.getArticle());
                    ArrayList<String> links = WikiAPI.getLinksHere(backList.getArticle());
                    progress++;
                    ArrayList<WikiListBack> newList = backList.addLinks(links);
                    nextBackList.addAll(newList);

                    routes = findRoutes(currentLists, newList);
                    if (routes.size() > 0) {
                        break;
                    }
                }
                currentBackLists.clear();
                currentBackLists.addAll(nextBackList);
                backhops++;
                progress = 1;
                routes = findRoutes(currentLists, currentBackLists);
                frontRead = true;
            }
        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("Here comes the route from " + stArticle + " to " + destination);
        System.out.println("Found " + routes.size() + " route(s)");
        int routeNumber = 1;
        for (ArrayList<String> singleRoute : routes){
            System.out.println("------------------ Route " + routeNumber + " ------------------");
            for (String node : singleRoute){
                System.out.println(node);
            }
            System.out.println("----------------- End route -----------------");
            routeNumber++;
        }

        long stop = System.currentTimeMillis();
        long timeSpent = (stop-start)/1000;

        System.out.println("Time spent: " + timeSpent + " s");
    }


    public ArrayList<ArrayList<String>> findRoutes(ArrayList<WikiList> wikiLists, ArrayList<WikiListBack> backLists){
        ArrayList<ArrayList<String>> routes = new ArrayList<>();
        for (WikiList wikiList : wikiLists){
            for (WikiListBack backList: backLists){
                if (wikiList.getArticle().equals(backList.getArticle())){
                    ArrayList<String> singleRoute = new ArrayList<>();
                    singleRoute.addAll(wikiList.getRoute());
                    singleRoute.addAll(backList.getRoute());
                    routes.add(singleRoute);
                }
            }
        }
        return routes;
    }

}
