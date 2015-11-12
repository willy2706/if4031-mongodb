package if4031.client.model.response;

import if4031.client.model.Timeline;
import if4031.client.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nim_13512065 on 11/9/15.
 */
public class DisplayTimelineResponse implements CassandraModelResponse {
    private Map<User, Timeline> tweets;

    public DisplayTimelineResponse() {
        tweets = new HashMap<>();
    }

    public void addUserAndTweets(User user, Timeline timeline) {
        tweets.put(user, timeline);
    }

    public Map<User, Timeline> getTimelineResponse() {
        return tweets;
    }

    public void setTweets(Map<User, Timeline> tweets) {
        this.tweets = tweets;
    }
}
