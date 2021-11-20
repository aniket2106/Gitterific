package models;

public class publicUserProfile{
    String userid,
            name,
            company,
            location,
            email ,
            hire,
            bio,
            twitter_username;
    long public_repos,
            followers,
            following;

    public publicUserProfile(String uid,String n,String c,String loc,String e,String h,String b,String t, long p,long f, long f1){
        userid = uid;
        name = n;
        company = c;
        location = loc;
        email = e;
        hire =h;
        bio = b;
        twitter_username = t;
        public_repos = p;
        followers = f;
        following = f1;
    }

    public String getUserid(){
        return userid;
    }

    public String getName(){
        return name;
    }
    public String getCompany(){
        return company;
    }
    public String getLocation(){
        return location;
    }
    public String getEmail(){
        return email;
    }
    public String getHire(){
        return hire;
    }
    public String getBio(){
        return bio;
    }
    public String getTwitterUsername(){
        return  twitter_username;
    }

    public long getPublic_repos(){
        return public_repos;
    }

    public  long getFollowers(){
        return followers;
    }

    public long getFollowing(){
        return following;
    }
}