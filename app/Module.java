import com.google.inject.AbstractModule;

import actors.UserActor;
import actors.UserParentActor;
import play.libs.akka.AkkaGuiceSupport;
import service.GithubApi;
import service.GithubImplementation;

public class Module extends AbstractModule implements AkkaGuiceSupport {
    
    @Override
    protected final void configure() {
        bind(GithubApi.class).to(GithubImplementation.class);
        bindActor(UserParentActor.class, "userParentActor");
        bindActorFactory(UserActor.class, UserActor.Factory.class);
    }

}
