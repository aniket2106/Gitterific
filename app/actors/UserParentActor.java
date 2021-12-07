package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.util.Timeout;
import play.libs.akka.InjectedActorSupport;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

import scala.compat.java8.FutureConverters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserParentActor extends AbstractActor implements InjectedActorSupport {

    private final Timeout timeout = new Timeout(10, TimeUnit.SECONDS);
    private final String query;

    private final Logger logger = LoggerFactory.getLogger("play");

    private final UserActor.Factory childFactory;

    @Inject
    public UserParentActor(UserActor.Factory childFactory) {
        this.childFactory = childFactory;
        this.query = "";
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Messages.UserParentActorCreate.class, create -> {
                    logger.info("Created child user actor with id" + create.id);
                    ActorRef child = injectedChild(() -> childFactory.create(create.id), "userActor-" + create.id);
                    CompletionStage<Object> future = FutureConverters.toJava(ask(child, new Messages.WatchSearchResults(query), timeout));
                    pipe(future, context().dispatcher()).to(sender());
                }).build();
    }

}