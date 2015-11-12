package if4031.client.model;

import java.util.List;

/**
 * Created by nim_13512065 on 11/9/15.
 */
public class Timeline {
    private final List<Tweet> tweets;

    public Timeline(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
