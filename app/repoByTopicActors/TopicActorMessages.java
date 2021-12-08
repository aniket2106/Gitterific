package repoByTopicActors;

import models.searchResult.SearchResults;

/**
 * Immutable message class for RequestActor
 */
public final class TopicActorMessages {
    
    /**
     * Message class for creating RequestActor with topic argument
     */
    public static final class TopicRequestActorCreate {
        public final String topic;

        /**
         * Constructor of TopicRequestActorCreate
         * @param topic Topic
         */
        public TopicRequestActorCreate(String topic) {
            this.topic = topic;
        }
        
        /**
         * Override toString method to return object's string representation
         */
        @Override
        public String toString() {
            return "TopicRequestActorCreate("+topic+")";
        }
    }

    /**
     * Message class for responding back to requesting actor
     */
    public static final class TopicRepoItems {

        public final SearchResults searchResults;
        public final String topic;

        /**
         * Constructor of TopicRepoItems
         * @param searchResults Response of github api
         * @param topic Topic
         */
        public TopicRepoItems(SearchResults searchResults, String topic) {
            this.searchResults = searchResults;
            this.topic = topic;
        }

        /**
         * Getter method for topic
         */
        public String getTopic() {
            return this.topic;
        }

        /**
         * Override toString method to return object's string representation
         */
        @Override
        public String toString() {
            return "SearchResults(" + topic + ")";
        }
        

    }

}
