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

public class userProfile{

    String API = "https://api.github.com/users/";
    HttpResponse response = null;
    List<String> l = new ArrayList<>();

    public JSONObject getUserData(String username){
        JSONObject temp = new JSONObject();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + URLEncoder.encode(username,"UTF-8"))).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Object object = new JSONParser().parse(String.valueOf(response.body()));

            temp = (JSONObject) object;
            //System.out.println("temp"+temp);
        } catch (Exception e) {
        }
        return temp;
    }

    public List<publicUserProfile> getData(String username){
        List<publicUserProfile> array = new ArrayList<>();
        JSONObject array1 = this.getUserData(username);
//            for(int i = 0; i < array1.size();i++){
        var temp = array1;
        try{
            array.add(new publicUserProfile((String) temp.get("login"),(String) temp.get("name"),(String) temp.get("company"),(String) temp.get("location"),(String) temp.get("email"),(String) temp.get("hire"),(String) temp.get("bio"),(String) temp.get("twitter_username"),(Long) temp.get("public_repos"),(Long) temp.get("followers"),(Long) temp.get("following")));
        }catch(Exception e){}
//            }
        return array;
    }
}