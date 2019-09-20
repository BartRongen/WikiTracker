import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WikiAPI {

    public static ArrayList<String> getLinks(String article) throws IOException {
        String url = "https://en.wikipedia.org/w/api.php?action=query&prop=links&titles=" + article + "&pllimit=500&format=json";
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
            JSONObject json = new JSONObject(response.toString());
            JSONObject json2 = json.getJSONObject("query").getJSONObject("pages");
            String key = (String) json2.keySet().toArray()[0];
            if (!json2.getJSONObject(key).has("links")){
                return new ArrayList<String>();
            }
            JSONArray json3 = json2.getJSONObject(key).getJSONArray("links");
            ArrayList<String> list = new ArrayList<>();
            for (int i=0; i<json3.length(); i++){
                JSONObject entry = (JSONObject) json3.get(i);
                String target = (String) entry.get("title");
                target = target.replace(" ", "_");
                list.add(target);
            }
            return list;
        } else {
            System.out.println("GET NOT WORKED");
        }
        return null;
    }

    public static ArrayList<String> getLinksHere(String article) throws IOException {
        String url = "https://en.wikipedia.org/w/api.php?action=query&prop=linkshere&titles=" + article + "&lhlimit=500&format=json";
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
            JSONObject json = new JSONObject(response.toString());
            JSONObject json2 = json.getJSONObject("query").getJSONObject("pages");
            String key = (String) json2.keySet().toArray()[0];
            if (!json2.getJSONObject(key).has("linkshere")){
                return new ArrayList<String>();
            }
            JSONArray json3 = json2.getJSONObject(key).getJSONArray("linkshere");
            ArrayList<String> list = new ArrayList<>();
            for (int i=0; i<json3.length(); i++){
                JSONObject entry = (JSONObject) json3.get(i);
                String target = (String) entry.get("title");
                target = target.replace(" ", "_");
                list.add(target);
            }
            return list;
        } else {
            System.out.println("GET NOT WORKED");
        }
        return null;
    }
}
