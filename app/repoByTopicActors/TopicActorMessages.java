package repoByTopicActors;

import models.searchResult.SearchResults;

public final class TopicActorMessages {
    
    public static final class TopicRequestActorCreate {
        public final String topic;

        public TopicRequestActorCreate(String topic) {
            this.topic = topic;
        }

        @Override
        public String toString() {
            return "TopicRequestActorCreate("+topic+")";
        }
    }

    public static final class TopicRepoItems {

        public final SearchResults searchResults;
        public final String topic;

        public TopicRepoItems(SearchResults searchResults, String topic) {
            this.searchResults = searchResults;
            this.topic = topic;
        }

        public String getTopic() {
            return this.topic;
        }

        @Override
        public String toString() {
            return "SearchResults(" + topic + ")";
        }
        

    }

}
