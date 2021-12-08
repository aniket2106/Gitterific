package repoDetailActor;


import models.repoDetails.IssueItem;
import models.repoDetails.RepoDetail;

/**
 * A message protocol of the actor class. It contains all information relevant for making
 * a request to get the repository details and issues of the repositories.
 * @author dhruvimodi
 *
 */

public final class RepoDetailMessages {
    
    public static final class CreateActorRepoDetail {

        public final String userName;
        public final String repoName;

        public CreateActorRepoDetail(String userName, String repoName) {
            this.userName = userName;
            this.repoName = repoName;
        }

    }

    public static final class CreateActorRepoIssues {

        public final String userName;
        public final String repoName;

        public CreateActorRepoIssues(String userName, String repoName) {
            this.userName = userName;
            this.repoName = repoName;
        }

    }

    public static final class RepoDetailResponse {

        public final RepoDetail repoDetail;

        public RepoDetailResponse(RepoDetail repoDetail) {
            this.repoDetail = repoDetail;
        }

    }

    public static final class RepoIssuesResponse {

        public final IssueItem[] issueItems;

        public RepoIssuesResponse(IssueItem[] issueItems) {
            this.issueItems = issueItems;
        }

    }

}
