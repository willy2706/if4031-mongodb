package if4031.client.model;

import java.util.UUID;

/**
 * Created by nim_13512065 on 11/9/15.
 */
public class Tweet {
    private final String username; //ini adalah orang yang tweet
    private final UUID time;
    private final UUID tweet_id;
    private final String body;

    public Tweet(String username, UUID time, UUID tweet_id, String body) {
        this.username = username;
        this.time = time;
        this.tweet_id = tweet_id;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public UUID getTweet_id() {
        return tweet_id;
    }

    public UUID getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }
}
