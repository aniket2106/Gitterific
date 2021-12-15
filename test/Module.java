import com.google.inject.AbstractModule;

import actors.UserActor;
import actors.UserParentActor;
import play.libs.akka.AkkaGuiceSupport;
import repoByTopicActors.RequestActor;
import repoDetailActor.RepoDetailActor;
import repoDetailActor.RepoIssueActor;
import service.GithubApi;
import service.GithubImplementation;
import service.GithubMockServiceImpl;

public class Module extends AbstractModule implements AkkaGuiceSupport {
    
    @Override
    protected final void configure() {
        // bind(GithubApi.class).to(GithubImplementation.class);
        bind(GithubApi.class).to(GithubMockServiceImpl.class);
        bindActor(UserParentActor.class, "userParentActor");
        bindActorFactory(UserActor.class, UserActor.Factory.class);
        bindActor(RequestActor.class, "requestActor");
        bindActor(RepoDetailActor.class, "repoDetailActor");
        bindActor(RepoIssueActor.class, "repoIssueActor");
    }

}
