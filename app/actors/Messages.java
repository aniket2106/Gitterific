package actors;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.searchResult.SearchResults;


public final class Messages {

    private final static Logger logger = LoggerFactory.getLogger("play");

    public static final class UserParentActorCreate {
        public final String id;

        public UserParentActorCreate(String id) {
            logger.info("Created actor with id" + id);
            this.id = requireNonNull(id);
        }

        @Override
        public String toString() {
            return "UserParentActorCreate(" + id + ")";
        }
    }

    public static final class WatchSearchResults {
        public final String query;

        public WatchSearchResults(String query) {
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "WatchSearchResults(" + query + ")";
        }
    }

    public static final class UnwatchSearchResults {
        public final String query;

        public UnwatchSearchResults(String query) {
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "UnwatchSearchResults(" + query + ")";
        }
    }

    public static final class RepoItem {
        public final Set<SearchResults> searchResults;
        public final String query;

        public RepoItem(Set<SearchResults> searchResults, String query) {
            this.searchResults= searchResults;
            this.query = query;
        }

        @Override
        public String toString() {
            return "RepoItem(" + query + ")";
        }
    }

    public static final class RegisterActor {
        @Override
        public String toString() {
            return "RegisterActor";
        }
    }

    @Override
    public String toString() {
        return "Messages";
    }
}
