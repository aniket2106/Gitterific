package models;

public class publicUserRepo {
    String repoNames;

    public publicUserRepo(String r){
        repoNames = r;
    }

    public String getRepoNames(){
        return repoNames;
    }
}