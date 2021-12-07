package repoDetailActor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;
import scala.concurrent.duration.Duration;
import service.GithubService;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class RepoDetailActor extends AbstractActor {
    
    @Inject
    private GithubService githubService;

    public GithubService getGithubService() {
        return githubService;
    }

    private ActorRef replyTo;

    private String userName;
    private String repoName;

    private final Logger logger = LoggerFactory.getLogger("play");

    public static Props props() {
        return Props.create(RepoDetailActor.class, () -> new RepoDetailActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(RepoDetailMessages.CreateActorRepoDetail.class, message -> {
                logger.info("Registering actor for repo detail {}", message.userName);
                replyTo = getSender();
                watchGithubResponse(message);
            })
            .build();
    }

    public CompletionStage<Void> watchGithubResponse(RepoDetailMessages.CreateActorRepoDetail message) {
        userName = message.userName;
        repoName = message.repoName;

        return githubService.getRepoDetails(userName, repoName).thenAcceptAsync(repoDetails -> {
            RepoDetailMessages.RepoDetailResponse response = new RepoDetailMessages.RepoDetailResponse(repoDetails);
            replyTo.tell(response, self());
        });

    }

    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }

    public ActorRef getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(ActorRef replyTo) {
        this.replyTo = replyTo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

}
