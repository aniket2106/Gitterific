package Businesslogic;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import models.publicUserRepo;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class userRepo{

    String API = "https://api.github.com/users/";
    HttpResponse response = null;
    List<String> l = new ArrayList<>();

    public Object getUserData(String username){
        Object temp = new Object();
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + URLEncoder.encode(username,"UTF-8")+"/repos")).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            temp = new JSONParser().parse(String.valueOf(response.body()));
        } catch (Exception e) {

        }
        return temp;
    }

    public List<publicUserRepo> getData(String username){
        List<publicUserRepo> array = new ArrayList<>();
        JSONArray array1 = (JSONArray) this.getUserData(username);
        for(int i = 0; i < array1.size();i++){
            var temp = (JSONObject) array1.get(i);
            try{
                array.add(new publicUserRepo((String) temp.get("name")));
            }catch(Exception e){}
        }
        return array;
    }
}