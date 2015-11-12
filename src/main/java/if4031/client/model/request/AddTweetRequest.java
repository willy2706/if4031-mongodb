package if4031.client.model.request;

import if4031.client.command.cassandra.AddTweetCassandraCommand;

import java.util.UUID;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class AddTweetRequest implements CassandraModelRequest {
    private final String username;
    private final String body;
    private final UUID tweet_id;
    private final UUID time;
    public AddTweetRequest(AddTweetCassandraCommand addTweetCassandraCommand) {
        username = addTweetCassandraCommand.getUsername();
        body = addTweetCassandraCommand.getBody();
        tweet_id = UUID.randomUUID();
        time = UUID.randomUUID(); //TODO change
    }

    public String getUsername() {
        return username;
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
}
