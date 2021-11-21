package Businesslogic;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import models.publicUserProfile;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 *<p> This userProfile class fetches user's public github information</p>
 */
public class userProfile{

    String API = "https://api.github.com/users/";
    HttpResponse response = null;
    List<String> l = new ArrayList<>();

    /**
     * <p>Trying to get the data using github's API</p>
     * @param username It is the Github's username of the user.
     * @return JSON object of the public information of the user.
     */
    public JSONObject getUserData(String username){
        JSONObject temp = new JSONObject();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + URLEncoder.encode(username,"UTF-8"))).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Object object = new JSONParser().parse(String.valueOf(response.body()));

            temp = (JSONObject) object;
        } catch (Exception e) {
        }
        return temp;
    }

    /**
     * <p>This function calls API and process the JSON data.</p>
     * @param username It is the Github's username of the user.
     * @return the list of <class>publicUserProfile</class>.
     */
    public List<publicUserProfile> getData(String username){
        List<publicUserProfile> array = new ArrayList<>();
        JSONObject array1 = this.getUserData(username);
        var temp = array1;
        try{
            array.add(new publicUserProfile((String) temp.get("login"),(String) temp.get("name"),(String) temp.get("company"),(String) temp.get("location"),(String) temp.get("email"),(String) temp.get("hire"),(String) temp.get("bio"),(String) temp.get("twitter_username"),(Long) temp.get("public_repos"),(Long) temp.get("followers"),(Long) temp.get("following")));
        }catch(Exception e){}
        return array;
    }
}