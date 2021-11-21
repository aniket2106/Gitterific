package models;
/**
 * <p>This model is used to store user's public repositories</p>
 * <p>It used getter methods to access data from outside.</p>
 * @author Aniket Tailor
 */
public class publicUserRepo {
    String repoNames;

    public publicUserRepo(String r){
        repoNames = r;
    }

    public String getRepoNames(){
        return repoNames;
    }
}